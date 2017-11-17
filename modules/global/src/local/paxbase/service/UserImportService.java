package local.paxbase.service;

import local.paxbase.entity.coredata.AppUser;

public interface UserImportService {
    String NAME = "paxbase_UserImportService";
    public int parseCsv(String rawUsers);
	public AppUser getUserByEmail(String email);
	public int createOrUpdateUser();
	public void clearStageTable();
}