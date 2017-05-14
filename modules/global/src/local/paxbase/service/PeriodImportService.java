package local.paxbase.service;

import java.util.Collection;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.PeriodImportStage;
import local.paxbase.entity.coredata.Site;

public interface PeriodImportService {
    String NAME = "paxbase_PeriodImportService";
    public int parseCsv(String rawPeriods);
	public Site getSiteByItemDesignation(String itemDesignation);
	public Campaign getCampaignByIdAndItemDesignation(String campaignNumber, String itemDesignation);
	public int createOrUpdateCampaign();
	public void clearStageTable();
    
}