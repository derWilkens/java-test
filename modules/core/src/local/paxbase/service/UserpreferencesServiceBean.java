package local.paxbase.service;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;

@Service(UserpreferencesService.NAME)
public class UserpreferencesServiceBean implements UserpreferencesService {
	@Inject
	private Persistence persistence;

	@Override
	public UserPreference getPreference(UserPreferencesContext context, UUID entityUuid) {
		UserPreference preference = null;
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$UserPreference e where e.contextId = :context and e.entityUuid = :entityUuid";

			TypedQuery<UserPreference> query = persistence.getEntityManager().createQuery(queryString,
					UserPreference.class);
			query.setParameter("contextId", context);
			query.setParameter("entityUuid", entityUuid);
			try {
				preference = query.getSingleResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return preference;
		}
	}

}