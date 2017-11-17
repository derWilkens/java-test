package local.paxbase.service;

import java.util.UUID;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;

import local.paxbase.entity.coredata.AppUser;

@Service(EntityService.NAME)
public class EntityServiceBean implements EntityService {
	
	@Inject
	private Persistence persistence;

	@Override
	public AppUser getAppUser(UUID appUserId) {
		try (Transaction tx = persistence.createTransaction()) {
			return persistence.getEntityManager().find(AppUser.class, appUserId);

		}
	}
	
	@Override
	public <T extends Entity<K>, K> T getById(Class<T> entityClass, K id){
		try (Transaction tx = persistence.createTransaction()) {
			return persistence.getEntityManager().find(entityClass, id);

		}
	}
	
}