package local.paxbase.service;

import local.paxbase.entity.dto.TimelineDTO;

public interface EmlService {
    String NAME = "paxbase_EmlService";
	TimelineDTO getEmlDto();
}