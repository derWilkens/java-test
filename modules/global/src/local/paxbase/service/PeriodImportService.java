package local.paxbase.service;

import java.util.ArrayList;

import local.paxbase.entity.PeriodImportStage;

public interface PeriodImportService {
    String NAME = "paxbase_PeriodImportService";
    public ArrayList<PeriodImportStage> parseCsv(String rawPeriods);
    
}