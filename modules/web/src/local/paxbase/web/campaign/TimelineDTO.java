package local.paxbase.web.campaign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.UUID;

import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;

import local.paxbase.entity.GroupedBy;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

public class TimelineDTO {
	
	private ArrayList<TimelineGroup> groupList;
	private ArrayList<TimelineItem> timelineItemList;
	private CollectionDatasource<Period, UUID> periodDs;
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;
	private HashSet<UUID> preferredItems;
	private GroupedBy groupedBy;
	private String filteredEntityType; 
	
	public TimelineDTO(CollectionDatasource<Period, UUID> periodDs, CollectionDatasource<UserPreference, UUID> userPreferencesDs, GroupedBy groupedBy) {
		this.periodDs = periodDs;
		this.userPreferencesDs = userPreferencesDs;
		this.groupedBy = groupedBy;
	}
	
	public TimelineDTO(GroupedBy site) {
		// TODO Auto-generated constructor stub
	}



	public void refresh(){
		
		preferredItems = new HashSet<>();
		for (UserPreference preference : userPreferencesDs.getItems()) {
			preferredItems.add(preference.getEntityUuid());
		}
		
		groupList = new ArrayList<TimelineGroup>();
		timelineItemList = new ArrayList<TimelineItem>();
		//Collection<GroupInfo> groupInfos = campaignsDs.rootGroups();
		HashMap<UUID, String> groupKeys = new HashMap<UUID, String>();

		for (Period entity : periodDs.getItems()) {
			
			//entweder die Site oder den Type aber welches Attribut der Enity 
			//abgefragt wird hängt ja vom preferredItem ab und auch nicht von der GroupBy-Eigenschaft	
			//das verstehe ich doch nach zwei Tagen schon nicht mehr
			
			if (preferredItems.contains(entity) || true) {
				timelineItemList
						.add(new TimelineItem((Period) entity, entity.getLabel(), entity.getGroupLabel(groupedBy)));
				groupKeys.put(entity.getGroupId(groupedBy), entity.getGroupLabel(groupedBy));
			}
		}

		for (Entry<UUID, String> groupKeyValue : groupKeys.entrySet()) {
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
