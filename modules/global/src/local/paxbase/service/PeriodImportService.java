package local.paxbase.service;

import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.period.MaintenanceCampaign;

public interface PeriodImportService {
    String NAME = "paxbase_PeriodImportService";
    public int parseCsv(String rawPeriods);
	public Site getSiteByItemDesignation(String itemDesignation);
	public MaintenanceCampaign getCampaignByIdAndItemDesignation(String campaignNumber, String itemDesignation);
	public int createOrUpdateCampaign();
	public void clearStageTable();
    
}