package local.paxbase.web.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.Period;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

public class CampaignDTO {
	
	private ArrayList<TimelineGroup> groupList;
	private ArrayList<TimelineItem> timelineItemList;
	private GroupDatasource<Campaign, UUID> campaignsDs;
	
	public CampaignDTO(GroupDatasource<Campaign, UUID> campaignsDs) {
		
		groupList = new ArrayList<TimelineGroup>();
		timelineItemList = new ArrayList<TimelineItem>();
		this.campaignsDs = campaignsDs;
	}
	private void transformDTO(){
		
		//Collection<GroupInfo> groupInfos = campaignsDs.rootGroups();
		HashMap<String, String> groupKeys = new HashMap<String, String>();

		for (Entity entity : campaignsDs.getItems()) {
			Campaign campaign = (Campaign) entity;
			timelineItemList.add(new TimelineItem((Period) campaign, campaign.getCampaignNumber(),
					campaign.getSite().getId().toString()));
			groupKeys.put(campaign.getSite().getId().toString(), campaign.getSite().getSiteName());
		}

		for (Entry<String, String> groupKeyValue : groupKeys.entrySet()) {
			TimelineGroup timelineGroup = new TimelineGroup(groupKeyValue.getKey(), groupKeyValue.getValue());
			groupList.add(timelineGroup);
		}
	}
	
	public void refresh() {
		transformDTO();
		
	}
	public ArrayList<TimelineGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList<TimelineGroup> groupList) {
		this.groupList = groupList;
	}

	public ArrayList<TimelineItem> getTimelineItemList() {
		return timelineItemList;
	}

	public void setTimelineItemList(ArrayList<TimelineItem> timelineItemList) {
		this.timelineItemList = timelineItemList;
	}



}
