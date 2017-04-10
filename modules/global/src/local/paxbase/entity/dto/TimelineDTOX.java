package local.paxbase.entity.dto;

import java.io.Serializable;
import java.util.ArrayList;

import local.paxbase.entity.GroupedBy;

public class TimelineDTOX implements Serializable{
	
	private static final long serialVersionUID = -5409206984283999021L;
	private ArrayList<TimelineGroup> groupList;
	private ArrayList<TimelineItem> timelineItemList;

	
	public TimelineDTOX( GroupedBy groupedBy) {
		groupList = new ArrayList<TimelineGroup>();
		timelineItemList = new ArrayList<TimelineItem>();
	}
	




	public void refresh(){
		

			//entweder die Site oder den Type aber welches Attribut der Enity 
			//abgefragt wird hängt ja vom preferredItem ab und auch nicht von der GroupBy-Eigenschaft	
			//das verstehe ich doch nach zwei Tagen schon nicht mehr
			//oder beides? Site und Type? Nur Kampagnen und HWAL
			//Klassischer Filter, zwei Kriterien, wobei die Werte der Kriterien eine Menge von Werten sein können
			//besser eine Liste von Werten
			//aber eine Positivliste, die Inital leer ist, zumindest per default
			
			//select * from (select * from campaings as period union select * from servicePeriod as period) 
			//where Type.uuid in (valueList) and site.uuid in (valueList)
			
			//es reicht, wenn ein Kriterium wahr ist...
			
//			if (preferredItems.contains(entity.getType().getUuid())){
//				timelineItemList
//						.add(new TimelineItem((Period) entity, entity.getLabel(), entity.getGroupLabel(groupedBy)));
//				groupKeys.put(entity.getGroupId(groupedBy), entity.getGroupLabel(groupedBy));
//			}
//			else 
//			if (entity.getClass().equals(Campaign.class)){
//				Campaign campaign = (Campaign)entity;
//				if (preferredItems.contains(campaign.getSite().getUuid())){
//					timelineItemList
//							.add(new TimelineItem((Period) entity, entity.getLabel(), entity.getGroupLabel(groupedBy)));
//					groupKeys.put(entity.getGroupId(groupedBy), entity.getGroupLabel(groupedBy));
//				}
//			}
//			else
//			if (entity.getClass().equals(ServicePeriod.class)){
//				ServicePeriod servicePeriod = (ServicePeriod)entity;
//				if (preferredItems.contains(servicePeriod.getSite().getUuid())){
//					timelineItemList
//							.add(new TimelineItem((Period) entity, entity.getLabel(), entity.getGroupLabel(groupedBy)));
//					groupKeys.put(entity.getGroupId(groupedBy), entity.getGroupLabel(groupedBy));
//				}
//			}

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
