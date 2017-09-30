package local.paxbase.web.offshoreuser;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.service.TimelineService;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent;

public class Rotaplan extends AbstractLookup{
	
	@Inject
	private CollectionDatasource<OffshoreUser, UUID> offshoreUsersDs;
	
	//@Inject
	//private CollectionDatasource<DutyPeriod, UUID> dutyPeriodDs;
	@Inject
	private ScrollBoxLayout timelineBox;
	private RotaplanComponent rotaplan;
	
	@Inject
	private TimelineService timelineDTOService;
	private TimelineDTO dto;
	
	private Logger log = LoggerFactory.getLogger(Rotaplan.class);
	
	@Override
	public void init(Map<String, Object> params){
		super.init(params);
		// JS-UI-Komonente
		rotaplan = new RotaplanComponent();
		dto = timelineDTOService.getRotoplanDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
			rotaplan.refresh();
		}
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		box.setWidth("100%");
		box.addComponent(rotaplan);
	}
}