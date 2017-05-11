package local.paxbase.service;

import java.util.ArrayList;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.PeriodImportStage;
import local.paxbase.entity.coredata.Site;

public interface PeriodImportService {
    String NAME = "paxbase_PeriodImportService";
    public ArrayList<PeriodImportStage> parseCsv(String rawPeriods);
	public Site getSiteByItemDesignation(String itemDesignation);
	public Campaign getCampaignByIdAndSite(String campaignNumber, Site tmpSite);
	public void createOrUpdateCampaign(Campaign campaign);
	public void clearStageTable();
    
}