package local.paxbase.service;

import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineDTO;

public interface RotaplanService {
    String NAME = "paxbase_RotaplanService";
    Site getSiteByItemDesignation(String itemDesignation);
}