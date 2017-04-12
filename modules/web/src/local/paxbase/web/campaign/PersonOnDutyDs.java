package local.paxbase.web.campaign;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import com.haulmont.cuba.security.entity.User;

import local.paxbase.service.TimelineService;

public class PersonOnDutyDs extends CustomCollectionDatasource<User, UUID> {
	
	private TimelineService timelineService = AppBeans.get(TimelineService.NAME);

	@Override
	protected Collection<User> getEntities(Map<String, Object> params) {
		
		return timelineService.getPersonsOnDutyList();
	}
}
