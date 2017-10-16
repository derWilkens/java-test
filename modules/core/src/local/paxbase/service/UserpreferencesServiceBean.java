package local.paxbase.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;

@Service(UserpreferencesService.NAME)
public class UserpreferencesServiceBean extends PreferencesService implements UserpreferencesService {
	@Inject
	private Persistence persistence;
	@Inject
	private DataManager dataManager;
	@Inject
	private Metadata metadata;
	
	@Override
	public UserPreference getPreference(UserPreferencesContext context, UUID entityUuid) {
		UserPreference preference = null;
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$UserPreference e where e.contextId = :context and e.entityUuid = :entityUuid and e.userUuid = :userId";

			TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
					UserPreference.class);
			query.setParameter("contextId", context);
			query.setParameter("entityUuid", entityUuid);
			query.setParameter("userId", AppBeans.get(UserSessionSource.class).getUserSession().getUser().getId());
			try {
				preference = query.getSingleResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return preference;
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
			persistence.getEntityManager().remove(item);
		}
	}

}