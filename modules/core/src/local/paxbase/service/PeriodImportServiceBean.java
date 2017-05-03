package local.paxbase.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import local.paxbase.entity.PeriodImportStage;

@Service(PeriodImportService.NAME)
public class PeriodImportServiceBean implements PeriodImportService {

	//Function periodImport(String rawPeriods)
	//for each line
	//neues item erstellen, nonpersistens entity
	//list hinzufügen
	//list fungiert dann als DS für Tabelle
	public ArrayList<PeriodImportStage> parseCsv(String rawPeriods){
		ArrayList<PeriodImportStage> parseResult = new ArrayList<PeriodImportStage>();
		String[] lines = rawPeriods.split("[\r\n]");
		for(String line:lines){
			PeriodImportStage record = new PeriodImportStage();
			String[] values = line.split("\t");
			record.setItemDesignation(values[0]);
			record.setCampaignNumber(values[1]);
			record.setStartDate(null);
			record.setEndDate(null);
			record.setShutdown(null);
			parseResult.add(record);
		}
		return parseResult;
	}
}