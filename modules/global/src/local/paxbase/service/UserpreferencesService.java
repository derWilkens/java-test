package local.paxbase.service;

import java.util.List;
import java.util.UUID;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;

public interface UserpreferencesService {
    String NAME = "paxbase_UserpreferencesService";

    UserPreference getPreference(UserPreferencesContext context, UUID id);
    List<UserPreference> getPreferences(UserPreferencesContext context);
    UserPreference createPreference(UserPreferencesContext context, UUID entityId, String userValue);
    void deletePreference(UserPreferencesContext context, UUID preferenceUUID);
	String getSiteColorPreference(UUID siteId);
}