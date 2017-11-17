package local.paxbase.service;

import java.util.UUID;

import com.haulmont.cuba.core.entity.Entity;

import local.paxbase.entity.coredata.AppUser;

public interface EntityService {
    String NAME = "paxbase_EntityService";
    
    public AppUser getAppUser(UUID appUserId);

	public <T extends Entity<K>, K> T getById(Class<T> entityClass, K id);
    
}