package local.paxbase.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;

@Service(UserpreferencesService.NAME)
public class UserpreferencesServiceBean extends PreferencesService implements UserpreferencesService {
	@Inject
	private Persistence persistence;
	@Inject
	private Metadata metadata;
	
	@Override
	public UserPreference getPreference(UserPreferencesContext context, UUID id) {
		try (Transaction tx = persistence.createTransaction()) {
			return getPreference(persistence.getEntityManager(), context, id);
		}
	}

	@Override
	public List<UserPreference> getPreferences(UserPreferencesContext context) {
		try (Transaction tx = persistence.createTransaction()) {
			return getUserPreferences(persistence.getEntityManager(), context);
		}
	}

	@Override
	public UserPreference createPreference(UserPreferencesContext context, UUID entityId, String userValue) {
		final UserPreference newItem = metadata.create(UserPreference.class);
		try (Transaction tx = persistence.createTransaction()) {
			newItem.setContextId(context);
			newItem.setEntityUuid(entityId);
			newItem.setUserValue(userValue);

			persistence.getEntityManager().persist(newItem);
			tx.commit();
			tx.close();
		}
		return newItem;
	}

	@Override
	public void deletePreference(UserPreferencesContext context, UUID entityUUID) {
		try (Transaction tx = persistence.createTransaction()) {
			UserPreference item = getPreference(context, entityUUID);
			if(item!=null){
				persistence.getEntityManager().remove(item);
			}
			tx.commit();
			
		}
	}
	@Override
	public String getSiteColorPreference(UUID siteId) {
		UserPreference userPreference;

		String queryString = "select e from paxbase$UserPreference e where e.userId = :userId and e.contextId=:context and e.entityUuid=:siteId";
		TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
				UserPreference.class);
		UserSessionSource session = AppBeans.get(UserSessionSource.class);
		query.setParameter("userId", session.getUserSession().getUser().getId());
		query.setParameter("context", UserPreferencesContext.SiteColors);
		query.setParameter("siteId", siteId);
		userPreference = query.getFirstResult();
		if (userPreference != null) {
			return userPreference.getUserValue();
		}
		return null;
	}
}