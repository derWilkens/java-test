package local.paxbase.web.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

public class CampaignDTO {
	
	private ArrayList<TimelineGroup> groupList;
	private ArrayList<TimelineItem> timelineItemList;
	private GroupDatasource<Campaign, UUID> campaignsDs;
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;
	private HashSet<UUID> preferredSites;
	
	public CampaignDTO(GroupDatasource<Campaign, UUID> campaignsDs, CollectionDatasource<UserPreference, UUID> userPreferencesDs) {
		

		this.campaignsDs = campaignsDs;
		this.userPreferencesDs = userPreferencesDs;
		
		refresh();
	}
	public void refresh(){
		
		preferredSites = new HashSet<>();
		for (UserPreference preference : userPreferencesDs.getItems()) {
			preferredSites.add(preference.getEntityUuid());
		}
		
		groupList = new ArrayList<TimelineGroup>();
		timelineItemList = new ArrayList<TimelineItem>();
		//Collection<GroupInfo> groupInfos = campaignsDs.rootGroups();
		HashMap<String, String> groupKeys = new HashMap<String, String>();

		for (Entity entity : campaignsDs.getItems()) {
			Campaign campaign = (Campaign) entity;
			if(preferredSites.contains(campaign.getSite().getUuid())){
			timelineItemList.add(new TimelineItem((Period) campaign, campaign.getCampaignNumber(),
					campaign.getSite().getId().toString()));
			groupKeys.put(campaign.getSite().getId().toString(), campaign.getSite().getSiteName());
			}
		}

		for (Entry<String, String> groupKeyValue : groupKeys.entrySet()) {
			TimelineGroup timelineGroup = new TimelineGroup(groupKeyValue.getKey(), groupKeyValue.getValue());
			groupList.add(timelineGroup);
		}
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
