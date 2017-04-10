package local.paxbase.service;

import local.paxbase.entity.dto.TimelineDTO;

public interface TimelineService {
    String NAME = "paxbase_TimelineService";
    TimelineDTO getDto(String context);
}