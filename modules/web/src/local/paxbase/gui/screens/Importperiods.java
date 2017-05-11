package local.paxbase.gui.screens;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.PeriodImportStage;
import local.paxbase.entity.coredata.Site;
import local.paxbase.service.PeriodImportService;

public class Importperiods extends AbstractWindow {

	@Inject
	private TextArea taImport;
	
	@Inject
	private PeriodImportService importService;
	
	@Inject
	private CollectionDatasource<PeriodImportStage, UUID> periodImportStagesDs;
	
    public void onImportRawData(Component source) {
    
        importService.clearStageTable();
        periodImportStagesDs.clear();
		
        ArrayList<PeriodImportStage> importResult = importService.parseCsv(taImport.getRawValue());
        
        
		final DataSupplier dataservice = periodImportStagesDs.getDataSupplier();
		for(PeriodImportStage parsedItem:importResult){
			final PeriodImportStage newItem = dataservice.newInstance(periodImportStagesDs.getMetaClass());
			newItem.setItemDesignation(parsedItem.getItemDesignation());
			Site tmpSite = importService.getSiteByItemDesignation(newItem.getItemDesignation());
			newItem.setCampaignNumber(parsedItem.getCampaignNumber());
			newItem.setEndDate(parsedItem.getEndDate());
			newItem.setShutdown(parsedItem.getShutdown());
			newItem.setStartDate(parsedItem.getStartDate());
			
			if (tmpSite == null){
				newItem.setImportLog("NAS nicht gefunden:" + parsedItem.getItemDesignation() + "\n");
			}
			if (newItem.getCampaignNumber() == null){
				newItem.setImportLog(newItem.getImportLog()+ "Kampagnen-Nr nicht gesetzt");
			}
			periodImportStagesDs.addItem(newItem);
			periodImportStagesDs.commit();
		}
    }
    public void onCreateCampaigns(Component source){
   
    	for(PeriodImportStage importItem: periodImportStagesDs.getItems()){
    		Campaign campaign = new Campaign();
    		campaign.setCampaignNumber(importItem.getCampaignNumber());
    		campaign.setEnd(importItem.getEndDate());
    		campaign.setShutdown(importItem.getShutdown());
    		campaign.setSite(importService.getSiteByItemDesignation(importItem.getItemDesignation()));
    		campaign.setStart(importItem.getStartDate());
    		importService.createOrUpdateCampaign(campaign);
    	}
    	periodImportStagesDs.clear();
    	//return periodImportStagesDs.getItems().size();
    }

    public void onClearStage(Component source) {
    	periodImportStagesDs.clear();
		periodImportStagesDs.commit();
    }

    public void onClearRawDataTextArea(Component source) {
        taImport.setValue("");
    }
}