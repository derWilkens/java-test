package local.paxbase.web.offshoreuser;

import java.util.Map;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent;

public class Rotaplan extends AbstractLookup {
	
	//@Inject
	//private CollectionDatasource<OffshoreUser, UUID> offshoreUsersDs;
	
	//@Inject
	//private CollectionDatasource<DutyPeriod, UUID> dutyPeriodDs;
	@Inject
	private VBoxLayout timelineBox;
	private RotaplanComponent rotaplan;
	
	@Override
	public void init(Map<String, Object> params) {
		// JS-UI-Komponente
		rotaplan = new RotaplanComponent();
		
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		box.addComponent(rotaplan);
	}
}