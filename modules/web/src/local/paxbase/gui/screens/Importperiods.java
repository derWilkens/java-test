package local.paxbase.gui.screens;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;

import local.paxbase.entity.PeriodImportStage;
import local.paxbase.service.PeriodImportService;

public class Importperiods extends AbstractWindow {

	@Inject
	private TextArea taImport;
	
	@Inject
	private PeriodImportService importService;
	
	@Inject
	private CollectionDatasource<PeriodImportStage, UUID> periodImportStagesDs;
	
    public void onImportRawData(Component source) {
        ArrayList<PeriodImportStage> importResult = importService.parseCsv(taImport.getRawValue());
        periodImportStagesDs.clear();
        
		final DataSupplier dataservice = periodImportStagesDs.getDataSupplier();
		for(PeriodImportStage parsedItem:importResult){
			final PeriodImportStage newItem = dataservice.newInstance(periodImportStagesDs.getMetaClass());
			newItem.setCampaignNumber(parsedItem.getCampaignNumber());
			newItem.setEndDate(parsedItem.getEndDate());
			newItem.setItemDesignation(parsedItem.getItemDesignation());
			newItem.setShutdown(parsedItem.getShutdown());
			newItem.setStartDate(parsedItem.getStartDate());
			periodImportStagesDs.addItem(newItem);
			periodImportStagesDs.commit();
		}
    }

}