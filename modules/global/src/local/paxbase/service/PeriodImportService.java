package local.paxbase.service;

import java.util.List;

import local.paxbase.entity.PeriodImportRecord;

public interface PeriodImportService {
    String NAME = "paxbase_PeriodImportService";
    public List<PeriodImportRecord> parseCsv(String rawPeriods);
    
}