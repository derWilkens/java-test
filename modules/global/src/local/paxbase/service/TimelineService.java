package local.paxbase.service;

import java.util.Collection;

import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineItem;

public interface TimelineService {
    String NAME = "paxbase_TimelineService";
    TimelineDTO getDto(UserPreferencesContext context);
    TimelineDTO getRotoplanDto();
    Collection<OffshoreUser> getPersonsOnDuty();
    TimelineItem periodToTimelineItem(Period period, UserPreferencesContext context);
	TimelineDTO getEmlDto();
}