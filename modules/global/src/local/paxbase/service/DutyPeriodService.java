package local.paxbase.service;

import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.dto.DutyPeriodDTO;

public interface DutyPeriodService {
    String NAME = "paxbase_DutyPeriodService";
    DutyPeriod createDutyPeriod(DutyPeriodDTO dutyPeriod);
    DutyPeriod updateDutyPeriod(DutyPeriodDTO dutyPeriod);
    void deleteDutyPeriod(String uuid);
    
}