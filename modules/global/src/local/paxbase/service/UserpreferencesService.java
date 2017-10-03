package local.paxbase.service;

import java.util.UUID;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;

public interface UserpreferencesService {
    String NAME = "paxbase_UserpreferencesService";

    UserPreference getPreference(UserPreferencesContext context, UUID id);
    
}