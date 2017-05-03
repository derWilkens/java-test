package local.paxbase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import local.paxbase.entity.PeriodImportRecord;

@Service(PeriodImportService.NAME)
public class PeriodImportServiceBean implements PeriodImportService {

	//Function periodImport(String rawPeriods)
	//for each line
	//neues item erstellen, nonpersistens entity
	//list hinzufügen
	//list fungiert dann als DS für Tabelle
	public List<PeriodImportRecord> parseCsv(String rawPeriods){
		ArrayList<PeriodImportRecord> parseResult = new ArrayList<PeriodImportRecord>();
		String[] lines = rawPeriods.split("[\r\n]");
		for(String line:lines){
			PeriodImportRecord record = new PeriodImportRecord();
			String[] values = line.split("\t");
			record.setItemDesignation(values[0]);
			record.setCampaignNo(values[1]);
			record.setStart(null);
			record.setEnd(null);
			record.setShutdown(null);
			parseResult.add(record);
		}
		return parseResult;
	}
}