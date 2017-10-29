package local.paxbase.service;

import local.paxbase.entity.OffshoreUser;

public interface UserImportService {
    String NAME = "paxbase_UserImportService";
    public int parseCsv(String rawUsers);
	public OffshoreUser getUserByEmail(String email);
	public int createOrUpdateUser();
	public void clearStageTable();
}