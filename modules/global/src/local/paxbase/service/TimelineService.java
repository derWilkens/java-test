package local.paxbase.service;

import java.util.Collection;

import com.haulmont.cuba.security.entity.User;

import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.dto.TimelineDTO;

public interface TimelineService {
    String NAME = "paxbase_TimelineService";
    TimelineDTO getDto(String context);
    TimelineDTO getRotoplanDto();
    Collection<OffshoreUser> getPersonsOnDuty();
}