package local.paxbase.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.PeriodSubClass;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineConfig;
import local.paxbase.entity.dto.TimelineDTO;

@Service(TimelineService.NAME)
public class TimelineServiceBean implements TimelineService {

	@Inject
	private Persistence persistence;

	@Override
	public TimelineDTO getDto(String context) {

		TimelineDTO dto = new TimelineDTO();

		TimelineConfig campaignTimelineConfig = getCampaignConfig();

		TimelineConfig dutyPeriodConfig = getDutyPeriodGroupedBySiteConfig();

		try (Transaction tx = persistence.createTransaction()) {
			// preferences des Users für den context laden
			List<UserPreference> userPreferenceList = getUserPreferences(context);

			// weil das im Context der Kampagnenübersicht geladen wird
			// gucken wir, welche Entitäten wir denn brauchen
			// also erstmal die Typen laden, die in den Preferences sind

			List<Site> preferredSites = loadPreferredSites(userPreferenceList);
			List<FunctionCategory> preferredFunctionCategories = loadPreferredFunctionCategories(userPreferenceList);
			Set<PeriodSubClass> preferredSubClassList = new HashSet<PeriodSubClass>();

			for (FunctionCategory functionCategory : preferredFunctionCategories) {
				if (functionCategory.getPeriodSubClass() != null) {
					preferredSubClassList.add(functionCategory.getPeriodSubClass());
				}
			}

			for (PeriodSubClass periodSubClass : preferredSubClassList) {

				if (periodSubClass.equals(PeriodSubClass.Campaign)) {
					dto.addItems(getCampaigns(preferredSites, preferredFunctionCategories), campaignTimelineConfig);

				} else if (periodSubClass.equals(PeriodSubClass.Administration)) {
					List<OffshoreUser> personsOnDuty = loadPreferredPersonsOnDuty(userPreferenceList);
					List<DutyPeriod> dutyPeriods = getDutyPeriods(personsOnDuty, preferredSites,
							preferredFunctionCategories);
					dto.addItems(dutyPeriods, dutyPeriodConfig);
				}
			}

			tx.commit();
		}
		return dto;

	}

	@Override
	public TimelineDTO getRotoplanDto() {
		TimelineDTO dto = new TimelineDTO();
		TimelineConfig rotaplanConfig = getCampaignConfig();
		
		try (Transaction tx = persistence.createTransaction()) {
			
			// der angemeldete User darf seine MA einplanen, ein Admin darf
			// alle?
			// --> jeder MA sucht sich selbst die Departments			
			List<UserPreference> preferredDepartments = getUserPreferences("RotaplanDepartments");
			List<OffshoreUser> personsOnDuty = loadPreferredPersonsByDepartment(preferredDepartments);
			List<DutyPeriod> dutyPeriods = getDutyPeriods(personsOnDuty, null, null);
			
			dto.addItems(dutyPeriods, rotaplanConfig);
			
			tx.commit();
		}
		return dto;

	}

	private TimelineConfig getDutyPeriodGroupedBySiteConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupFunction((DutyPeriod e) -> e.getSite().getSiteName());
		dutyPeriodConfig.setParentGroupIdFunction((DutyPeriod e) -> {
			if (e.getSite().getParentSite() != null) {
				return e.getSite().getParentSite().getSiteName();
			} else
				return null;
		});
		dutyPeriodConfig.setItemLabelFunction((DutyPeriod e) -> {
			if (e.getFunctionCategory() == null) {
				Log.info("Function Category is null: " + e.getUuid().toString());
				return null;
			} else
				return e.getPersonOnDuty().getCaption() + " " + e.getFunctionCategory().getCategoryName();
		});
		dutyPeriodConfig.setStyleFunction((DutyPeriod e) -> {
			String colorHex = getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
			}
			return "";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getDutyPeriodGroupedByUserConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupFunction((DutyPeriod e) -> e.getPersonOnDuty().getUuid().toString());
		dutyPeriodConfig.setParentGroupIdFunction((DutyPeriod e) -> {
			if (e.getPersonOnDuty().getDepartment() != null) {
				return e.getPersonOnDuty().getDepartment().getName();
			} else
				return null;
		});
		dutyPeriodConfig.setItemLabelFunction((DutyPeriod e) -> {
			Log.info(e.getPersonOnDuty().getInstanceName());
			if (e.getPersonOnDuty() == null) {
				Log.info("PersonOnDuty is null: " + e.getUuid().toString());
				return null;
			} else
				return e.getPersonOnDuty().getCaption();
		});
		dutyPeriodConfig.setStyleFunction((DutyPeriod e) -> {
			String colorHex = getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
			}
			return "";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getCampaignConfig() {
		TimelineConfig campaignTimelineConfig = new TimelineConfig();
		campaignTimelineConfig.setGroupFunction((Campaign e) -> {
			return e.getSite().getSiteName();
		});
		campaignTimelineConfig.setParentGroupIdFunction((Campaign e) -> {
			if (e.getSite().getParentSite() != null) {
				return e.getSite().getParentSite().getSiteName();
			} else
				return null;
		});
		campaignTimelineConfig.setItemLabelFunction((Campaign e) -> {
			return e.getCampaignNumber();
		});
		campaignTimelineConfig.setStyleFunction((Campaign e) -> {
			String colorHex = getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
			}
			return "";
		});
		return campaignTimelineConfig;
	}

	private String getSiteColorPreference(UUID siteId) {
		UserPreference userPreference;

		String queryString = "select e from paxbase$UserPreference e where e.userId = :userId and e.context=:context and e.entityUuid=:siteId";
		TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
				UserPreference.class);
		UserSessionSource session = AppBeans.get(UserSessionSource.class);
		query.setParameter("userId", session.getUserSession().getUser().getId());
		query.setParameter("context", "SiteColors");
		query.setParameter("siteId", siteId);
		userPreference = query.getFirstResult();
		if (userPreference != null) {
			return userPreference.getUserValue();
		}
		return null;
	}

	// Drei Kriterien: (implizit Type), Site, ServiceUser;
	private List<DutyPeriod> getDutyPeriods(List<OffshoreUser> personOnDutyList, List<Site> siteList,
			List<FunctionCategory> preferredFunctionCategories) {
		List<DutyPeriod> DutyPeriods;

		String queryString;
		String queryConcatenator = "";

		queryString = "select e from paxbase$DutyPeriod e ";

		if (personOnDutyList != null && personOnDutyList.size() > 0) {
			queryConcatenator = queryConcatenator.equals("") ?  "" : "where";
			queryString = queryString + "e.personOnDuty.id in :personsIdList ";
			queryConcatenator = "AND ";
		}
		if (siteList != null && siteList.size() > 0) {
			queryConcatenator = queryConcatenator.equals("") ?  "" : "where";
			queryString = queryString + queryConcatenator + "e.site.id in :siteIdList ";
			queryConcatenator = "AND ";
		}
		if (preferredFunctionCategories != null && preferredFunctionCategories.size() > 0) {
			queryConcatenator = queryConcatenator.equals("") ?  "" : "where";
			queryString = queryString + queryConcatenator + "e.functionCategory.id in :catIdList ";
			queryConcatenator = "AND ";
		}

		TypedQuery<DutyPeriod> query = persistence.getEntityManager().createQuery(queryString, DutyPeriod.class);
		if (personOnDutyList != null && personOnDutyList.size() > 0) {
			query.setParameter("personsIdList", getUUIDList(personOnDutyList));
		}
		if (siteList != null && siteList.size() > 0) {
			query.setParameter("siteIdList", getUUIDList(siteList));
		}
		if (preferredFunctionCategories != null && preferredFunctionCategories.size() > 0) {
			query.setParameter("catIdList", getUUIDList(preferredFunctionCategories));
		}

		DutyPeriods = query.getResultList();

		return DutyPeriods;
	}

	private List<Campaign> getCampaigns(List<Site> siteList, List<FunctionCategory> preferredFunctionCategories) {
		List<Campaign> campaigns;

		String queryString = "select e from paxbase$Campaign e where e.site.id in :idList AND e.functionCategory.id in :catIdList";

		TypedQuery<Campaign> query = persistence.getEntityManager().createQuery(queryString, Campaign.class);

		query.setParameter("idList", getUUIDList(siteList));
		query.setParameter("catIdList", getUUIDList(preferredFunctionCategories));
		campaigns = query.getResultList();

		return campaigns;
	}

	private List<UUID> getUUIDList(List<?> entityList) {
		List<UUID> uuidList = new ArrayList<UUID>();
		for (Object entity : entityList) {
			uuidList.add(((StandardEntity) entity).getId());
		}
		return uuidList;
	}

	private List<UserPreference> getUserPreferences(String context) {
		List<UserPreference> userPreferenceList;

		String queryString = "select e from paxbase$UserPreference e where e.userId = :userId and e.context=:context";
		TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
				UserPreference.class);
		UserSessionSource session = AppBeans.get(UserSessionSource.class);
		query.setParameter("userId", session.getUserSession().getUser().getId());
		query.setParameter("context", context);

		userPreferenceList = query.getResultList();

		return userPreferenceList;
	}

	/**
	 * @param userPreferenceList
	 *            UUIDs der Typen die als Präferencen gesetzt wurden
	 * @return List<PeriodType> Liste aller PeriodTypes (Campaing oder
	 *         DutyPeriod usw)
	 * 
	 */
	private List<FunctionCategory> loadPreferredFunctionCategories(List<UserPreference> userPreferenceList) {

		List<UUID> functionCategoryIds = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$FunctionCategory e where e.id in :entityUUIDs";

		TypedQuery<FunctionCategory> query = persistence.getEntityManager().createQuery(queryString,
				FunctionCategory.class);

		query.setParameter("entityUUIDs", functionCategoryIds);

		return query.getResultList();
	}

	private List<OffshoreUser> loadPreferredPersonsOnDuty(List<UserPreference> userPreferenceList) {
		List<OffshoreUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$OffshoreUser e where e.id in :entityUUIDs";

		TypedQuery<OffshoreUser> query = persistence.getEntityManager().createQuery(queryString, OffshoreUser.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}

	private List<OffshoreUser> loadPreferredPersonsByDepartment(List<UserPreference> userPreferenceList) {
		List<OffshoreUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$OffshoreUser e where e.departmend.Id in :entityUUIDs";

		TypedQuery<OffshoreUser> query = persistence.getEntityManager().createQuery(queryString, OffshoreUser.class);

		query.setParameter("entityUUIDs", getUUIDList(entityUUIDs));

		entityList = query.getResultList();

		return entityList;
	}

	private List<Site> loadPreferredSites(List<UserPreference> userPreferenceList) {
		List<Site> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$Site e where e.id in :entityUUIDs";

		TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}

	private List<UUID> getEntityUUIDsFromList(List<UserPreference> userPreferenceList) {
		List<UUID> periodTypeIds = new ArrayList<UUID>();
		for (UserPreference userPreference : userPreferenceList) {
			periodTypeIds.add(userPreference.getEntityUuid());
		}
		return periodTypeIds;
	}

	@Override
	public Collection<OffshoreUser> getPersonsOnDuty() {

		Collection<OffshoreUser> userList;

		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e.personOnDuty from paxbase$DutyPeriod e ";

			TypedQuery<OffshoreUser> query = persistence.getEntityManager().createQuery(queryString,
					OffshoreUser.class);

			userList = query.getResultList();

			tx.commit();
		}
		return userList;
	}

}