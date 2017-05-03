package local.paxbase.gui.screens;

import java.util.List;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.TextArea;

import local.paxbase.entity.PeriodImportRecord;
import local.paxbase.service.PeriodImportService;

public class Importperiods extends AbstractWindow {

	@Inject
	private TextArea taImport;
	
	@Inject
	private PeriodImportService importService;
	
    public void onImportRawData(Component source) {
        List<PeriodImportRecord> records =  importService.parseCsv(taImport.getRawValue());
        
    }

}