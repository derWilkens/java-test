package local.paxbase.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.Department;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.PeriodSubClass;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.FunctionCategoryDTO;
import local.paxbase.entity.dto.SiteItem;
import local.paxbase.entity.dto.TimelineConfig;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

@Service(TimelineService.NAME)
public class TimelineServiceBean extends PreferencesService implements TimelineService {

	@Inject
	private Persistence persistence;

	@Inject
	private UserpreferencesService userPreferenceSerivce;

	@Override
	public TimelineDTO getDto(UserPreferencesContext context) {

		TimelineDTO dto = new TimelineDTO();

		TimelineConfig campaignTimelineConfig = getCampaignConfig();

		TimelineConfig dutyPeriodConfig = getDutyPeriodGroupedBySiteConfig();

		try (Transaction tx = persistence.createTransaction()) {
			// preferences des Users für den context laden
			List<UserPreference> userPreferenceList = getUserPreferences(persistence.getEntityManager(), context);

			// weil das im Context der Kampagnenübersicht geladen wird
			// gucken wir, welche Entitäten wir denn brauchen
			// also erstmal die Typen laden, die in den Preferences sind

			List<Site> preferredSites = getPreferredSites(persistence.getEntityManager(), context);
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
		TimelineConfig rotaplanConfig = getDutyPeriodGroupedByUserConfig();
		TimelineConfig campaignConfig = getCampaignAsBackgroundConfig();
		try (Transaction tx = persistence.createTransaction()) {

			List<TimelineGroup> groups = new ArrayList<TimelineGroup>();
			// Alle preferred Departments holen
			List<Department> preferredDepartments = getPreferredDepartments(persistence.getEntityManager(),
					UserPreferencesContext.RotaplanDepartments);
			for (Department department : preferredDepartments) {
				TimelineGroup group = new TimelineGroup(department.getUuid().toString(), department.getAcronym(),  department.getAcronym());
				group.setSubgroupOrder("content");
				Comparator<OffshoreUser> byLastName = Comparator.comparing(
						u -> u.getLastName()
//						OffshoreUser::getLastName);
						);
				List<OffshoreUser> members = department.getMembers().stream().sorted(byLastName).collect(Collectors.toList());
				int counter = 0;
				for (OffshoreUser user : members) {
					// der parentGroup eine nested hinzufügen
					group.addSubgroup(user.getUuid().toString());
					// NestedGroup einzel erzeugen und auch noch der Liste
					// hinzufügen
					
					TimelineGroup subGroup = new TimelineGroup(user.getUuid().toString(), user.getInstanceName(), String.valueOf(counter));
					subGroup.setShowNestedGroups(false);
					groups.add(subGroup);
					//counter++;
				}
				groups.add(group);
			}

			// Alle User der preferred Departements holen
			// List<OffshoreUser> preferredPersons =
			// getPersonsByPreferredDepartment(persistence.getEntityManager(),
			// UserPreferencesContext.RotaplanDepartments);

			// for (OffshoreUser user : preferredPersons) {
			// groups.add(new TimelineGroup(user.getUuid().toString(),
			// user.getInstanceName()));
			// }

			dto.getGroupList().addAll(groups);

			List<OffshoreUser> preferredPersons = getPersonsByPreferredDepartment(persistence.getEntityManager(),
					UserPreferencesContext.RotaplanDepartments);
			// Periods der preferred User holen
			List<DutyPeriod> dutyPeriods = getDutyPeriods(preferredPersons, null, null);

			dto.addItems(dutyPeriods, rotaplanConfig);

			// preferred Sites holen, hierfür werden die Kampagnen angezeigt und
			// die
			// und die Buttons angezeigt
			List<Site> preferredSites = getPreferredSites(persistence.getEntityManager(),
					UserPreferencesContext.SiteRotaplan);
			if (getPreference(persistence.getEntityManager(), UserPreferencesContext.RotaplanDisplayCampaigns,
					null) != null) {
				List<FunctionCategory> functionCategories = new ArrayList<FunctionCategory>();
				functionCategories = loadCampaignFunctionCategories();
				List<Campaign> campaigns = getCampaigns(preferredSites, functionCategories);
				// Die Campaigns müssen ins DTO
				dto.addItems(campaigns, campaignConfig);
			}
			if (preferredSites.size() == 0) {
				preferredSites = getSites(persistence.getEntityManager());
			}
			for (Site site : preferredSites) {
				SiteItem item1 = new SiteItem();
				item1.setSiteName(site.getItemDesignation());
				item1.setColor("#0b7eea");
				dto.getSiteItems().add(item1);
			}
			//preferred StandardDuties holen
			List<UserPreference> preferredDuties = getUserPreferences(persistence.getEntityManager(), UserPreferencesContext.Rotaplan);
			List<FunctionCategory> loadPreferredFunctionCategories = loadPreferredFunctionCategories(preferredDuties);
			for(FunctionCategory category: loadPreferredFunctionCategories){
				dto.getStandardDutyItems().add(new FunctionCategoryDTO(category.getId(), category.getCategoryName(), category.getPeriodSubClass()));
			}
			tx.commit();
		}
		return dto;

	}

	private TimelineConfig getDutyPeriodGroupedBySiteConfig() {

		TimelineConfig dutyPeriodConfig = new TimelineConfig();

		dutyPeriodConfig.setGroupIdFunction((DutyPeriod e) -> e.getSite().getSiteName());
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
			String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((DutyPeriod e) -> {
			return true;
		});
		dutyPeriodConfig.setTypeFunction((DutyPeriod e) -> {
			return "range";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getDutyPeriodGroupedByUserConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupIdFunction((DutyPeriod e) -> e.getPersonOnDuty().getUuid().toString());
		dutyPeriodConfig.setGroupLabelFunction((DutyPeriod e) -> e.getPersonOnDuty().getInstanceName());
		dutyPeriodConfig.setParentGroupIdFunction((DutyPeriod e) -> null);
		dutyPeriodConfig.setItemLabelFunction((DutyPeriod e) -> {
			String result = "";
			if (e.getSite() != null) {
				result = e.getSite().getItemDesignation();
			}
			if (e.getFunctionCategory() != null) {
				result = result + " " + e.getFunctionCategory().getCategoryName();
			}
			return result;
		});
		dutyPeriodConfig.setStyleFunction((DutyPeriod e) -> {
			if (null != e.getSite()) {
				String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
				if (colorHex != null) {
					return "background-color: #" + colorHex + ";";
				}
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((DutyPeriod e) -> {
			return true;
		});
		dutyPeriodConfig.setTypeFunction((DutyPeriod e) -> {
			return "range";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getCampaignAsBackgroundConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupIdFunction((Campaign e) -> null);
		dutyPeriodConfig.setGroupLabelFunction((Campaign e) -> null);
		dutyPeriodConfig.setParentGroupIdFunction((Campaign e) -> null);
		dutyPeriodConfig.setItemLabelFunction((Campaign e) -> {
			return e.getSite().getItemDesignation() + "<BR>" + e.getCampaignNumber();
		});
		dutyPeriodConfig.setStyleFunction((Campaign e) -> {
			if (null != e.getSite()) {
				String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
				if (colorHex != null) {
					return "background-color: #" + colorHex + ";";
				}
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((Campaign e) -> {
			return false;
		});
		dutyPeriodConfig.setTypeFunction((Campaign e) -> {
			return "background";
		});
		return dutyPeriodConfig;
	}

	private TimelineConfig getCampaignConfig() {
		TimelineConfig campaignTimelineConfig = new TimelineConfig();
		campaignTimelineConfig.setGroupIdFunction((Campaign e) -> {
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
			String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
			if (colorHex != null) {
				return "background-color: #" + colorHex + ";";
			}
			return "";
		});
		return campaignTimelineConfig;
	}

	// Drei Kriterien: (implizit Type), Site, ServiceUser;
	private List<DutyPeriod> getDutyPeriods(List<OffshoreUser> personOnDutyList, List<Site> siteList,
			List<FunctionCategory> preferredFunctionCategories) {
		List<DutyPeriod> dutyPeriods;

		String queryString;
		String queryConcatenator = "where ";

		queryString = "select e from paxbase$DutyPeriod e ";

		if (personOnDutyList != null && personOnDutyList.size() > 0) {
			queryString = queryString + queryConcatenator + "e.personOnDuty.id IN :personsIdList ";
			queryConcatenator = "AND ";
		}
		if (siteList != null && siteList.size() > 0) {
			queryString = queryString + queryConcatenator + "e.site.id IN :siteIdList ";
			queryConcatenator = "AND ";
		}
		if (preferredFunctionCategories != null && preferredFunctionCategories.size() > 0) {
			queryString = queryString + queryConcatenator + "e.functionCategory.id IN :catIdList ";
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

		dutyPeriods = query.getResultList();

		return dutyPeriods;
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

	private List<FunctionCategory> loadCampaignFunctionCategories() {

		String queryString = "select e from paxbase$FunctionCategory e where e.periodSubClass = :periodSubClass";

		TypedQuery<FunctionCategory> query = persistence.getEntityManager().createQuery(queryString,
				FunctionCategory.class);

		query.setParameter("periodSubClass", PeriodSubClass.Campaign);

		return query.getResultList();
	}

	private List<OffshoreUser> loadPreferredPersonsOnDuty(List<UserPreference> userPreferenceList) {
		List<OffshoreUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$OffshoreUser e where e.id in :entityUUIDs order by e.lastName asc";

		TypedQuery<OffshoreUser> query = persistence.getEntityManager().createQuery(queryString, OffshoreUser.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
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

	@Override
	public TimelineItem periodToTimelineItem(Period period, UserPreferencesContext context) {
		TimelineConfig timelineConfig = null;
		TimelineItem item = null;
		if (context.equals(UserPreferencesContext.CampaignBrowse)) {
			timelineConfig = getCampaignConfig();

		} else if (context.equals(UserPreferencesContext.Rotaplan)) {
			timelineConfig = getDutyPeriodGroupedByUserConfig();
		}
		
		try (Transaction tx = persistence.createTransaction()) {
			period = persistence.getEntityManager().merge(period);
			item = new TimelineItem(period, timelineConfig);
			tx.commit();
		}
		return item;
	}

}