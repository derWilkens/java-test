package local.paxbase.service;

import local.paxbase.entity.coredata.Site;

public interface RotaplanService {
    String NAME = "paxbase_RotaplanService";
    Site getSiteByItemDesignation(String itemDesignation);
}