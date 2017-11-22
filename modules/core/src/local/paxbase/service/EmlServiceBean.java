package local.paxbase.service;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.PeriodSubClass;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.coredata.SiteRoleRule;
import local.paxbase.entity.dto.TimelineConfig;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.period.AttendencePeriod;
import local.paxbase.entity.period.DutyPeriod;

@Service(EmlService.NAME)
public class EmlServiceBean extends PreferencesService implements EmlService {
	@Inject
	private Persistence persistence;
	@Inject
	private EntityService entityService;
	@Inject
	private UserpreferencesService userPreferenceSerivce;

	@Override
	public TimelineDTO getEmlDto() {
		TimelineDTO dto = new TimelineDTO();
		// TimelineConfig emlConfig = getDutyPeriodGroupedByUserConfig();
		try (Transaction tx = persistence.createTransaction()) {

			UserPreference userPreference = getUserPreference(persistence.getEntityManager(),
					UserPreferencesContext.EmlDisplaySite);

			if (userPreference != null) {
				Site site = entityService.getById(Site.class, userPreference.getEntityUuid());
				
				// DutyPeriod lastPeriod = getLastPeriod(siteId);
				// List<DutyPeriod> comingPeriods = getComingPeriods(siteId);

				// Die dutyperiods vom typ modeofoperations müssen als
				// Background eingeblendet werden
				


				TimelineGroup availableRoles = new TimelineGroup("avail", "Verfügbar", null);
				TimelineGroup requiredRoles = new TimelineGroup("req", "Erforderlich", null);

				List<DutyPeriod> requiredRoleDutyPeriods = getRequiredRoleDutyPeriods(site);
				List<DutyPeriod> availableRoleDutyPeriods = getAvailableRoleDutyPeriods(site);
						
				dto.addItems(requiredRoleDutyPeriods, getEmlRoleDutyConfig("Erforderlich"));
				dto.addItems(availableRoleDutyPeriods, getEmlRoleDutyConfig("Verfügbar"));

				// Maximalausprägung der Rollen ermitteln
				// definierte Rollen für eine Site holen
				// Anzahl der multiplizieren, Identifier hochzählen, label:
				// 'ERTM 1', ID: roleID würde dann
				// mehrfach vorkommen
				// beim Laden müssen die Rollen wieder in der richtigen
				// Reihenfolge erscheinen
				// die Reihenfolge ist egal, eigentlich

				// die DutyPeriod muss eine Referenz auf die Rolle haben
				// (FunctionCategory)
				// aber bei der Verknüpfung wird die Referenz um die zusätzliche
				// Information der Nummer der Rolle ergänzt

			}

			// Gruppen sind die Rollen, die in der Maximalausprägung zu besetzen
			// sind

			tx.commit();
		}

		// alle Dummy-Periods gehören in die oberste Gruppe
		// beim Drag auf die Rolle wird nur die Site gesetzt
		// Alternativ kann auch per Doppelklick die Zuordnung gemacht werden
		// das Datum des Einsatzes zu verschieben ist nicht sinnvoll, kann aber
		// schnell passieren

		return null;
	}



	private List<DutyPeriod> getRequiredRoleDutyPeriods(Site site) {
		List<DutyPeriod> comingModeOfOperationPeriods = getComingModeOfOperationPeriods(site);
		if (comingModeOfOperationPeriods.size() == 0) {
			throw new RuntimeException("Bitte zuerst die Bemannungsphasen eintragen. (Stammdaten - Bemannung");
		}
		
		List<DutyPeriod> requiredRoleDutyPeriods = new ArrayList<DutyPeriod>();

		int maxPob = 1;
		List<SiteRoleRule> siteRoleRules = site.getSiteRoleRules();
		for (DutyPeriod dutyPeriod : comingModeOfOperationPeriods) {
			for (SiteRoleRule siteRoleRule : siteRoleRules) {
				for (int i = 0; i < siteRoleRule.getRequiredNumberOfRoles(maxPob); i++) {
					DutyPeriod dummy = new DutyPeriod();
					//dummy.setSite(dutyPeriod.getSite());
					dummy.setStart(dutyPeriod.getStart());
					dummy.setEnd(dutyPeriod.getEnd());
					dummy.setFunctionCategory(siteRoleRule.getFunctionCategory());
					requiredRoleDutyPeriods.add(dummy);
				}
			}
		}
		return requiredRoleDutyPeriods;
	}

	private List<DutyPeriod> getComingModeOfOperationPeriods(Site site) {
		return getComingPeriods(site, PeriodSubClass.ModeOfOperation);
	}

	private List<DutyPeriod> getAvailableRoleDutyPeriods(Site site) {
		return getComingPeriods(site, PeriodSubClass.DutyPeriod);
	}
	
	@SuppressWarnings("unchecked")
	private List<DutyPeriod> getComingPeriods(Site site, PeriodSubClass subClass) {
		return (List<DutyPeriod>) persistence.getEntityManager()
				.createQuery("SELECT e from paxbase$DutyPeriod e "
						+ "where e.site.id = :siteId and e.functionCategory.periodSubClass = :subClass "
						+ "and e.end >= :today")
				.setParameter("siteId", site.getId()).setParameter("subClass", subClass)
				.setParameter("today", new Date()).getResultList();
	}
	
	
	private TimelineConfig getEmlRoleDutyConfig(String parentGroupLabel) {
		TimelineConfig config = new TimelineConfig();
		config.setGroupIdFunction((AttendencePeriod e) -> e.getFunctionCategory().getUuid().toString()); // die
																									// Eml-Rolle-ID
		config.setGroupLabelFunction((AttendencePeriod e) -> e.getFunctionCategory().getCategoryName()); //
		config.setParentGroupIdFunction((AttendencePeriod e) -> parentGroupLabel);// entweder
																			// avail
																			// oder
																			// req?
		config.setItemLabelFunction((AttendencePeriod e) -> e.getPersonOnDuty().getInstanceName());
		config.setStyleFunction((AttendencePeriod e) -> {
			if (null != e.getOperationPeriod().getSite()) {
				String colorHex = userPreferenceSerivce.getRoleColorPreference(e.getFunctionCategory().getUuid());
				if (colorHex != null) {
					Color c = Color.decode("0x" + colorHex);
					String rgb = c.getRed() + "," + c.getGreen() + "," + c.getBlue();
					return "background-color: rgba(" + rgb + ", 0.6);";
				}
			}
			return "";
		});
		config.setEditableFunction((DutyPeriod e) -> {
			return false;
		});
		config.setTypeFunction((DutyPeriod e) -> {
			return "range";
		});
		return config;
	}
}