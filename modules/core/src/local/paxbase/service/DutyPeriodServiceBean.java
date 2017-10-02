package local.paxbase.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;

import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.dto.DutyPeriodDTO;

@Service(DutyPeriodService.NAME)
public class DutyPeriodServiceBean implements DutyPeriodService {
	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;
	
	@Override
	public DutyPeriod createDutyPeriod(DutyPeriodDTO dutyPeriod) {
		try (Transaction tx = persistence.createTransaction()) {
			DutyPeriod newItem = metadata.create(DutyPeriod.class);
			//newItem.setClient(client);
//			newItem.setContractor(dutyPeriod.getContractor());
//			newItem.setEnd(dutyPeriod.getEnd());
//			newItem.setFunctionCategory(functionCategory);
//			newItem.setPersonOnDuty(personOnDuty);
			
			
			
		}
		return null;
	}

	@Override
	public DutyPeriod updateDutyPeriod(DutyPeriodDTO dutyPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDutyPeriod(String uuid) {
		// TODO Auto-generated method stub
		
	}

}