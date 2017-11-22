package local.paxbase.service;

import java.util.Collection;

import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineItem;
import local.paxbase.entity.period.Period;

public interface TimelineService {
    String NAME = "paxbase_TimelineService";
    TimelineDTO getDto(UserPreferencesContext context);
    TimelineDTO getRotoplanDto();
    Collection<AppUser> getPersonsOnDuty();
    TimelineItem periodToTimelineItem(Period period, UserPreferencesContext context);
}