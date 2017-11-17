package local.paxbase.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
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
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.cap.coredata.Role;
import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.coredata.Department;
import local.paxbase.entity.coredata.DutyPeriodTemplate;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.NumberRangeRule;
import local.paxbase.entity.coredata.PeriodSubClass;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.coredata.SiteRoleRule;
import local.paxbase.entity.dto.DutyPeriodDTO;
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

				if (periodSubClass.equals(PeriodSubClass.ModeOfOperation)) {
					dto.addItems(getCampaigns(preferredSites, preferredFunctionCategories), campaignTimelineConfig);

				} else if (periodSubClass.equals(PeriodSubClass.Administration)) {
					List<AppUser> personsOnDuty = loadPreferredPersonsOnDuty(userPreferenceList);
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

		try (Transaction tx = persistence.createTransaction()) {

			// Alle Teams mit Members holen
			dto.getGroupList().addAll(getTimelineGroups());

			// Periods der preferred User holen
			List<AppUser> preferredPersons = getPersonsByPreferredDepartment(persistence.getEntityManager(),
					UserPreferencesContext.RotaplanDepartments);
			List<DutyPeriod> dutyPeriods = getDutyPeriods(preferredPersons, null, null);
			dto.addItems(dutyPeriods, rotaplanConfig);

			if (!getUserPreferences(persistence.getEntityManager(), UserPreferencesContext.RotaplanDisplayCampaigns)
					.isEmpty()) {
				// Campaigns werden anhand der preferred Sites und preferred
				// FunctionCategories vom SubClassTyp Campaign(!) geholt
				List<UserPreference> preferredRotaplanFunctionCategories = getUserPreferences(
						persistence.getEntityManager(), UserPreferencesContext.Rotaplan);
				List<Site> preferredSites = getPreferredSites(persistence.getEntityManager(),
						UserPreferencesContext.Rotaplan);
				List<FunctionCategory> preferredFunctionCategories = loadPreferredFunctionCategories(
						preferredRotaplanFunctionCategories);
				Set<PeriodSubClass> preferredSubClassList = new HashSet<PeriodSubClass>();

				for (FunctionCategory functionCategory : preferredFunctionCategories) {
					if (functionCategory.getPeriodSubClass() != null) {
						preferredSubClassList.add(functionCategory.getPeriodSubClass());
					}
				}
				dto.addItems(getCampaigns(preferredSites, preferredFunctionCategories),
						getCampaignAsBackgroundConfig());
			}

			// preferred Sites holen, hierfür die Drag-Buttons angezeigt
			dto.setDutyPeriodTemplates(getDutyPeriodTemplatesDTO());

			tx.commit();
		}
		return dto;

	}

	@Override
	public TimelineDTO getEmlDto() {
		TimelineDTO dto = new TimelineDTO();
		TimelineConfig emlConfig = getDutyPeriodGroupedByUserConfig();
		try (Transaction tx = persistence.createTransaction()) {
			List<UserPreference> userPreferences = getUserPreferences(persistence.getEntityManager(),
					UserPreferencesContext.EmlDisplaySite);
			if (!userPreferences.isEmpty()) {
				UUID siteId = userPreferences.get(0).getEntityUuid();
				DutyPeriod lastPeriod = getLastPeriod(siteId);
				//theoretisch muss pro Tag die POB gezählt werden und auf der Basis die Rollen bestimmt werden
				int pob = getPobForDate(siteId, new Date());
				//getRolesForSite(siteId);
			}
			tx.commit();
		}
		// Gruppen sind die Rollen, die in der Maximalausprägung zu besetzen sind
		
		// alle Dummy-Periods gehören in die oberste Gruppe
		// beim Drag auf die Rolle wird nur die Site gesetzt
		// Alternativ kann auch per Doppelklick die Zuordnung gemacht werden
		// das Datum des Einsatzes zu verschieben ist nicht sinnvoll, kann aber
		// schnell passieren

		return null;
	}

	private List<DutyPeriodDTO> getDutyPeriodTemplatesDTO() {
		List<DutyPeriodDTO> dutyPeriodTemplates = new ArrayList<DutyPeriodDTO>();
		for (DutyPeriodTemplate template : getDutyPeriodTemplates()) {
			DutyPeriodDTO templateDTO = new DutyPeriodDTO();
			templateDTO.setPersonId(template.getUser().getId().toString());
			if (template.getSite() != null) {
				templateDTO.setSiteId(template.getSite().getId().toString());
				templateDTO.setItemDesignation(template.getSite().getItemDesignation());
				String colorHex = userPreferenceSerivce.getSiteColorPreference(template.getSite().getUuid());
				if (colorHex != null) {
					templateDTO.setColor("#" + colorHex);
				}
			}
			templateDTO.setFunctionCategoryId(template.getFunctionCategory().getId().toString());
			templateDTO.setCategoryName(template.getFunctionCategory().getCategoryName());
			templateDTO.setDuration(template.getDefaultDuration());
			dutyPeriodTemplates.add(templateDTO);
		}
		return dutyPeriodTemplates;
	}

	private List<TimelineGroup> getTimelineGroups() {
		List<TimelineGroup> groups = new ArrayList<TimelineGroup>();
		// Alle preferred Departments holen
		List<Department> preferredDepartments = getPreferredDepartments(persistence.getEntityManager(),
				UserPreferencesContext.RotaplanDepartments);
		for (Department department : preferredDepartments) {
			TimelineGroup group = new TimelineGroup(department.getUuid().toString(), department.getAcronym(),
					department.getAcronym());
			group.setSubgroupOrder("content");
			Comparator<AppUser> byLastName = Comparator.comparing(u -> u.getLastname()
			// OffshoreUser::getLastName);
			);
			List<AppUser> members = department.getMembers().stream().sorted(byLastName)
					.collect(Collectors.toList());
			int counter = 0;
			for (AppUser user : members) {
				// der parentGroup eine nested hinzufügen
				group.addSubgroup(user.getUuid().toString());
				// NestedGroup einzel erzeugen und auch noch der Liste
				// hinzufügen

				TimelineGroup subGroup = new TimelineGroup(user.getUuid().toString(), user.getInstanceName(),
						String.valueOf(counter));
				subGroup.setShowNestedGroups(false);
				groups.add(subGroup);
				// counter++;
			}
			groups.add(group);
		}
		return groups;
	}

	private List<DutyPeriodTemplate> getDutyPeriodTemplates() {

		String queryString = "select e from paxbase$DutyPeriodTemplate e where e.user.id = :userId";

		TypedQuery<DutyPeriodTemplate> query = persistence.getEntityManager().createQuery(queryString,
				DutyPeriodTemplate.class);

		query.setParameter("userId", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getId());
		query.addViewName("dutyPeriodTemplate-view");
		return query.getResultList();
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
				return e.getPersonOnDuty().getInstanceName() + " " + e.getFunctionCategory().getCategoryName();
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
				result = result + " - " + e.getFunctionCategory().getCategoryName();
			}
			if (e.getRemark() != null) {
				result = result + " - " + e.getRemark();
			}
			return result;
		});
		dutyPeriodConfig.setStyleFunction((DutyPeriod e) -> {
			if (null != e.getSite()) {
				String colorHex = userPreferenceSerivce.getSiteColorPreference(e.getSite().getUuid());
				if (colorHex != null) {
					Color c = Color.decode("0x"+colorHex);
					String rgb =  c.getRed() + "," + c.getGreen() + "," + c.getBlue();
					return "background-color: rgba("+rgb+", 0.6);";
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
			return e.getSite().getItemDesignation() + " " + e.getCampaignNumber();
		});
		dutyPeriodConfig.setStyleFunction((Campaign e) -> {
			if (null != e.getSite()) {
				String colorHex = userPreferenceSerivce.getSiteBackgroundColorPreferrence(e.getSite().getUuid());
				if (colorHex != null) {
					Color c = Color.decode("0x"+colorHex);
					String rgb =  c.getRed() + "," + c.getGreen() + "," + c.getBlue();
					return "background-color: rgba("+rgb+", 0.4);";
				}
					//return "background-color: #" + Integer.toHexString(((Integer.decode("0x"+colorHex) & 0x7f7f7f << 1))) + ";"; 
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
		if (true)
			return null;
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
				String rgb = String.valueOf(Color.decode("0x"+colorHex).getRGB());
				return "background-color: rgba("+rgb+", 0.6);";
			}
			return "";
		});
		campaignTimelineConfig.setTypeFunction((DutyPeriod e) -> {
			return "range";
		});
		return campaignTimelineConfig;
	}

	// drei Kriterien: (implizit Type), Site, ServiceUser;
	private List<DutyPeriod> getDutyPeriods(List<AppUser> personOnDutyList, List<Site> siteList,
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

		// String queryString = "select e from paxbase$Campaign e where
		// e.site.id in :idList AND e.functionCategory.id in :catIdList";
		String queryString = "select e from paxbase$Campaign e where e.site.id in :idList";

		TypedQuery<Campaign> query = persistence.getEntityManager().createQuery(queryString, Campaign.class);

		query.setParameter("idList", getUUIDList(siteList));
		// query.setParameter("catIdList",
		// getUUIDList(preferredFunctionCategories));
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

	private List<AppUser> loadPreferredPersonsOnDuty(List<UserPreference> userPreferenceList) {
		List<AppUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$OffshoreUser e where e.id in :entityUUIDs order by e.lastname asc";

		TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString, AppUser.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}

	@Override
	public Collection<AppUser> getPersonsOnDuty() {

		Collection<AppUser> userList;

		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e.personOnDuty from paxbase$DutyPeriod e ";

			TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString,
					AppUser.class);

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
			period = persistence.getEntityManager().find(DutyPeriod.class, period.getId()); // war
			// mal
			// merge,
			// aber
			// warum?
			item = new TimelineItem(period, timelineConfig);
			tx.commit();
		}
		return item;
	}

	/*
	 * für den definierten Zeitraum in der Zukunft werden sämtliche Dutyperiods ausgewertet.
	 * Daraus ergibt sich tagesscharf die POB 
	 * Map mit Datum - Anzahl
	 * 
	 */
	private List<Role> getRoleDutyPeriodsForSite(UUID siteId) {
		
		Collection<Role> userList;
		int pob = 0;
		try (Transaction tx = persistence.createTransaction()) {
			Site tmpSite = null;
			//über aller SitRoleRules 
			for (SiteRoleRule rule : tmpSite.getSiteRoleRules()) {
				int requiredNumberOfRoles;
				for (NumberRangeRule rangeRule: rule.getRangeRule()) {
					if(pob > rangeRule.getAmountFrom() && pob <= rangeRule.getAmountTo()){
						requiredNumberOfRoles = rangeRule.getRequiredNumber();
					}
				} 
				rule.getRole();
			}
			String queryString = "select e.roles from paxbase$Site e ";

			TypedQuery<AppUser> query = persistence.getEntityManager().createQuery(queryString,
					AppUser.class);

			// userList = query.getResultList();

			tx.commit();
		}
		return null;
	}
	
	private int getPobForDate(UUID siteId, Date date) {
		//select period where siteId, periodType = "Anwesend" and start <= date and end <= date
		return persistence.getEntityManager()
		.createNativeQuery("SELECT e from paxbase$DutyPeriod e where e.site.id = :siteId and e.functionCategory.periodSubClass = :periodType")
		.setParameter("siteId", siteId)
		.setParameter("periodType", PeriodSubClass.DutyPeriod).getResultList().size();
	}
	
	@SuppressWarnings("unchecked")
	public DutyPeriod getLastPeriod(UUID siteId){
		
		return (DutyPeriod)persistence.getEntityManager()
		.createNativeQuery("SELECT e from paxbase$DutyPeriod e where e.site.id = :siteId")
		.setParameter("siteId", siteId)
		.getResultList()
		.stream().max(Comparator.comparing(DutyPeriod::getEnd)).get();
	}
}