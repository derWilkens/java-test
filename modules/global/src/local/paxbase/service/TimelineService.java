package local.paxbase.service;

import java.util.Collection;

import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.dto.TimelineDTO;

public interface TimelineService {
    String NAME = "paxbase_TimelineService";
    TimelineDTO getDto(UserPreferencesContext context);
    TimelineDTO getRotoplanDto();
    Collection<OffshoreUser> getPersonsOnDuty();
}