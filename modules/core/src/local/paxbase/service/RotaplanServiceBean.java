package local.paxbase.service;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.Site;

@Service(RotaplanService.NAME)
public class RotaplanServiceBean implements RotaplanService {
	
	@Inject
	private Persistence persistence;
	
	@Override
	public Site getSiteByItemDesignation(String itemDesignation) {
		try (Transaction tx = persistence.createTransaction()) {
			
			String queryString = "select e from paxbase$Site e where e.itemDesignation = :itemDesignation";

			TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);

			query.setParameter("itemDesignation", itemDesignation);
			tx.commit();
			
			return query.getSingleResult();
		}
	}
	public Collection<Site> getPreferredSites(){
		EntityManager em = persistence.getEntityManager();
		List<UserPreference> userPreferenceList = UserPreferencesDataService.getUserPreferences(em, UserPreferencesContext.Rotaplan);
		
		return null;
	}
	

}