package local.paxbase.service;

import java.util.List;

import local.paxbase.entity.cap.coredata.Role;
import local.paxbase.entity.period.AttendencePeriod;

public interface CapService {
    String NAME = "paxbase_CapService";
    
    public List<Role> getAvailableUserRoles(AttendencePeriod dutyPeriod);
}