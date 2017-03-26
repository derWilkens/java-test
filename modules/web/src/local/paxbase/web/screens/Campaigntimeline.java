package local.paxbase.web.screens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.Period;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;
import local.paxbase.web.toolkit.ui.timelinecomponent.TimelineComponent;

public class Campaigntimeline extends AbstractWindow {
	@Inject
	GroupDatasource<?, String> campaignsDs;
//	GroupDatasourceImpl i;
	@Inject
	GroupTable<Campaign> campaignsTable;
	
	@Override
	public void init(Map<String, Object> params) {
		// TODO Auto-generated method stub
		super.init(params);
		//campaignsTable.
		TimelineComponent timeline = new TimelineComponent();

    	ArrayList<TimelineGroup> periodList = new ArrayList<TimelineGroup>();
//    	campaignsDs.groupBy(new String[] {"siteName"});
    	campaignsDs.refresh();
    	
    	Collection<GroupInfo> groupInfos = campaignsDs.rootGroups();
    	
    	for (GroupInfo groupId : groupInfos) {
			
    		TimelineGroup group = new TimelineGroup();
    		group.setContent(groupId.getPropertyValue("siteName").toString());
    		periodList.add(new TimelineGroup());
	
	    	for (Entity entity: campaignsDs.getChildItems(groupId)){
	    		Campaign campaign = (Campaign) entity;
	    		group.getNestedGroups().add(new TimelineItem((Period)campaign, campaign.getCampaignNumber()));
	    	}
	    	periodList.add(group);
    	
    		
//    		campaignsDs.getChildItems(groupInfo.
//    		TimelineItem item = new TimelineItem(period, content)
		}
    	
    	
    	
//    	for (Duration duration: durationsDs.getItems()) {
//			durationList.add(new TimelineItem(duration));
//		}
//    	vis.setDurationList(durationList);
//    	com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(vbox1);
//        box.addComponent(vis);
	}
	
}