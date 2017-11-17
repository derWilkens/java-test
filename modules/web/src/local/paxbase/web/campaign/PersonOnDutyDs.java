package local.paxbase.web.campaign;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;

import local.paxbase.entity.coredata.AppUser;
import local.paxbase.service.TimelineService;

public class PersonOnDutyDs extends CustomCollectionDatasource<AppUser, UUID> {
	
	private TimelineService timelineService = AppBeans.get(TimelineService.NAME);
	
	@Override
	protected Collection<AppUser> getEntities(Map<String, Object> params) {
		
		return timelineService.getPersonsOnDuty();
	}
}
