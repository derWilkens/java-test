package local.paxbase.service;

import java.util.UUID;

import local.paxbase.entity.UserPreference;

public interface UserpreferencesService {
    String NAME = "paxbase_UserpreferencesService";

    UserPreference getPreference(String string, UUID id);
    
}