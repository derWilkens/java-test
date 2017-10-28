package local.paxbase.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.Department;
import local.paxbase.entity.coredata.Site;

public abstract class PreferencesService {
	
	protected List<UserPreference> getUserPreferences(EntityManager em, UserPreferencesContext context) {

		String queryString = "select e from paxbase$UserPreference e where e.userId = :userId and e.contextId=:contextId";
		
		TypedQuery<UserPreference> query = em.createQuery(queryString,
				UserPreference.class);
		UserSessionSource session = AppBeans.get(UserSessionSource.class);
		query.setParameter("userId", session.getUserSession().getUser().getId());
		query.setParameter("contextId", context);

		return query.getResultList();
	}

	public UserPreference getPreference(EntityManager em, UserPreferencesContext context, UUID entityUuid) {
		
			String queryString = "select e from paxbase$UserPreference e where e.contextId = :contextId and e.entityUuid = :entityUuid and e.userId = :userId";

			TypedQuery<UserPreference> query = em.createQuery(queryString,
					UserPreference.class);
			query.setParameter("contextId", context);
			query.setParameter("entityUuid", entityUuid);
			query.setParameter("userId", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getId());
	
			return query.getFirstResult();
	}
	
	protected List<Site> getPreferredSites(EntityManager em, UserPreferencesContext context){
		List<UserPreference> userPreferenceList = getUserPreferences(em,context);
		return loadPreferredSites(em, userPreferenceList);
	}
	protected List<OffshoreUser> getPersonsByPreferredDepartment(EntityManager em, UserPreferencesContext context){
		List<UserPreference> preferredDepartemtPreferenceList = getUserPreferences(em,context);
		return loadPersonsByPreferredDepartment(em, preferredDepartemtPreferenceList);
	}
	

	private List<Site> loadPreferredSites(EntityManager em, List<UserPreference> userPreferenceList) {
		List<Site> entityList;

		List<UUID> entityUUIDs = getEntityUUIDsFromList(userPreferenceList);

		String queryString = "select e from paxbase$Site e where e.id in :entityUUIDs";

		TypedQuery<Site> query = em.createQuery(queryString, Site.class);

		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}
	protected List<Site> getSites(EntityManager em) {
		List<Site> entityList;

		String queryString = "select e from paxbase$Site e ";

		TypedQuery<Site> query = em.createQuery(queryString, Site.class);

		entityList = query.getResultList();

		return entityList;
	}	
	private List<OffshoreUser> loadPersonsByPreferredDepartment(EntityManager em, List<UserPreference> userPreferenceList) {
		List<OffshoreUser> entityList;

		List<String> entityUUIDs = getUUIDsToStringFromList(userPreferenceList);

		String queryString = "select e from paxbase$OffshoreUser e where e.department.id IN :entityUUIDs";

		TypedQuery<OffshoreUser> query = em.createQuery(queryString, OffshoreUser.class);
		
		query.setParameter("entityUUIDs", entityUUIDs);

		entityList = query.getResultList();

		return entityList;
	}
	
	public List<UUID> getEntityUUIDsFromList(List<UserPreference> userPreferenceList) {
		List<UUID> periodTypeIds = new ArrayList<UUID>();
		for (UserPreference userPreference : userPreferenceList) {
			periodTypeIds.add(userPreference.getEntityUuid());
		}
		return periodTypeIds;
	}
	public List<String> getUUIDsToStringFromList(List<UserPreference> userPreferenceList){
		List<String> ids = new ArrayList<String>();
		for (UserPreference userPreference : userPreferenceList) {
			ids.add(userPreference.getEntityUuid().toString());
		}
		return ids;
	}
	
	public List<String> getUUIDList(List<?> entityList) {
		List<String> uuidList = new ArrayList<String>();
		for (Object entity : entityList) {
			uuidList.add(((StandardEntity) entity).getId().toString());
		}
		return uuidList;
	}
}