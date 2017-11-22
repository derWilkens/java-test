package local.paxbase.service;

import java.util.List;


public interface CapService {
    String NAME = "paxbase_CapService";
    
    public List<Role> getAvailableUserRoles(DutyPeriod dutyPeriod);
}
