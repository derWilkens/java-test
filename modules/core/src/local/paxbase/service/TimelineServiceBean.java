package local.paxbase.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.GroupedBy;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.PeriodSubClass;
import local.paxbase.entity.ServicePeriod;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.coredata.PeriodType;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

@Service(TimelineService.NAME)
public class TimelineServiceBean implements TimelineService {

	@Inject
	private Persistence persistence;

	@Override
	public TimelineDTO getDto(String context) {
		
		GroupedBy groupedBy = GroupedBy.Site;
		TimelineDTO dto = new TimelineDTO(groupedBy);

		HashMap<String, String> groupKeys = new HashMap<String, String>();

		// preferences des Users für den context laden
		List<UserPreference> userPreferenceList = getUserPreferences(context);

		// weil das im Context der Kampagnenübersicht geladen wird
		// gucken wir, welche Entitäten wir denn brauchen
		// also erstmal die Typen laden, die in den Preferences sind

		List<PeriodType> preferredPeriodTypes = getPeriodTypes(userPreferenceList);
		try (Transaction tx = persistence.createTransaction()) {

			List<Site> preferredSites = getSites(userPreferenceList);
			for (PeriodType periodType : preferredPeriodTypes) {

				if (periodType.getPeriodSubClass() != null) {
					if (periodType.getPeriodSubClass().equals(PeriodSubClass.Campaign)) {
						// jetzt können die Kampagnen selektiert werden
						// deren Sites preferred sind
						for (Campaign entity : getCampaigns(preferredSites)) {

							dto.getTimelineItemList().add(new TimelineItem(entity, entity.getLabel(),
									entity.getGroupLabel(groupedBy)));
							groupKeys.put(entity.getGroupLabel(groupedBy), entity.getSite().getSiteName());
						}
					} else if (periodType.getPeriodSubClass().equals(PeriodSubClass.ServicePeriod)) {

						for (ServicePeriod entity : getServicePeriods(getPersonOnDutyList(userPreferenceList),
								preferredSites)) {

							dto.getTimelineItemList().add(new TimelineItem(entity, entity.getLabel(),
									entity.getGroupLabel(groupedBy)));
							groupKeys.put(entity.getGroupLabel(groupedBy), entity.getSite().getSiteName());
						}
					}

				}
			}

			for (Entry<String, String> groupKeyValue : groupKeys.entrySet()) {
				TimelineGroup timelineGroup = new TimelineGroup(groupKeyValue.getKey(), groupKeyValue.getValue());
				dto.getGroupList().add(timelineGroup);
			}
			tx.commit();
		}
		return dto;
	}

	// Drei Kriterien: (implizit Type), Site, ServiceUser;
	private List<ServicePeriod> getServicePeriods(List<OffshoreUser> personOnDutyList, List<Site> siteList) {
		List<ServicePeriod> servicePeriods;

		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e from paxbase$ServicePeriod e where e.personOnDuty in :persons and e.site in :sites";

			TypedQuery<ServicePeriod> query = persistence.getEntityManager().createQuery(queryString,
					ServicePeriod.class);

			query.setParameter("persons", getUUIDList(personOnDutyList));
			query.setParameter("sites", getUUIDList(siteList));

			servicePeriods = query.getResultList();

			tx.commit();
		}
		return servicePeriods;
	}

	public List<Campaign> getCampaigns(List<Site> siteList) {
		List<Campaign> campaigns;

		String queryString = "select e from paxbase$Campaign e where e.site.id in :idList";

		TypedQuery<Campaign> query = persistence.getEntityManager().createQuery(queryString, Campaign.class);

		query.setParameter("idList", getUUIDList(siteList));

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

	public List<UserPreference> getUserPreferences(String context) {
		List<UserPreference> userPreferenceList;

		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$UserPreference e where e.userId = :userId and e.context=:context";
			TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
					UserPreference.class);
			UserSessionSource session = AppBeans.get(UserSessionSource.class);
			query.setParameter("userId", session.getUserSession().getUser().getId());
			query.setParameter("context", context);

			userPreferenceList = query.getResultList();

			tx.commit();
		}
		return userPreferenceList;
	}

	/**
	 * @param userPreferenceList
	 *            UUIDs der Typen die als Präferencen gesetzt wurden
	 * @return List<PeriodType> Liste aller PeriodTypes (Campaing oder
	 *         ServicePeriod usw)
	 * 
	 */
	public List<PeriodType> getPeriodTypes(List<UserPreference> userPreferenceList) {
		List<PeriodType> entityList;

		List<UUID> periodTypeIds = getEntityUUIDList(userPreferenceList);

		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$PeriodType e where e.id in :periodIds";

			TypedQuery<PeriodType> query = persistence.getEntityManager().createQuery(queryString, PeriodType.class);

			query.setParameter("periodIds", periodTypeIds);

			entityList = query.getResultList();

			tx.commit();
		}
		return entityList;
	}

	public List<OffshoreUser> getPersonOnDutyList(List<UserPreference> userPreferenceList) {
		List<OffshoreUser> entityList;

		List<UUID> entityUUIDs = getEntityUUIDList(userPreferenceList);

		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$OffshoreUser e where e.id in :entityUUIDs";

			TypedQuery<OffshoreUser> query = persistence.getEntityManager().createQuery(queryString,
					OffshoreUser.class);

			query.setParameter("entityUUIDs", entityUUIDs);

			entityList = query.getResultList();

			tx.commit();
		}
		return entityList;
	}

	public List<Site> getSites(List<UserPreference> userPreferenceList) {
		List<Site> entityList;

		List<UUID> entityUUIDs = getEntityUUIDList(userPreferenceList);

		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$Site e where e.id in :entityUUIDs";

			TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);

			query.setParameter("entityUUIDs", entityUUIDs);

			entityList = query.getResultList();

			tx.commit();
		}
		return entityList;
	}

	private List<UUID> getEntityUUIDList(List<UserPreference> userPreferenceList) {
		List<UUID> periodTypeIds = new ArrayList<UUID>();
		for (UserPreference userPreference : userPreferenceList) {
			periodTypeIds.add(userPreference.getEntityUuid());
		}
		return periodTypeIds;
	}



}