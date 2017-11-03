package local.paxbase.entity.dto;

import java.util.ArrayList;
import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.Period;

@MetaClass(name = "paxbase$TimelineDTO")
public class TimelineDTO extends AbstractNotPersistentEntity {
	private static final long serialVersionUID = 3757688296152498888L;

	@MetaProperty
	protected List<TimelineGroup> groupList;

	@MetaProperty
	protected List<TimelineGroup> parentGroupList;

	@MetaProperty
	protected List<TimelineItem> timelineItemList;
	
	@MetaProperty
	protected List<SiteItem> siteItems;
	
	@MetaProperty
	protected List<FunctionCategoryDTO> standardDutyItems;
		

	public void setParentGroupList(List<TimelineGroup> parentGroupList) {
		this.parentGroupList = parentGroupList;
	}

	public List<TimelineGroup> getParentGroupList() {
		return parentGroupList;
	}

	public List<TimelineGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TimelineGroup> groupList) {
		this.groupList = groupList;
	}

	public TimelineDTO() {
		this.timelineItemList = new ArrayList<TimelineItem>();
		this.groupList = new ArrayList<TimelineGroup>();
		this.parentGroupList = new ArrayList<TimelineGroup>();
		this.siteItems = new ArrayList<SiteItem>();
		this.standardDutyItems = new ArrayList<FunctionCategoryDTO>();
	}

	public void setTimelineItemList(List<TimelineItem> timelineItemList) {
		this.timelineItemList = timelineItemList;
	}

	public List<TimelineItem> getTimelineItemList() {
		return timelineItemList;
	}
	public List<SiteItem> getSiteItems() {
		return siteItems;
	}

	public void setSiteItems(List<SiteItem> siteItems) {
		this.siteItems = siteItems;
	}
	public List<FunctionCategoryDTO> getStandardDutyItems() {
		return standardDutyItems;
	}

	public void setStandardDutyItems(List<FunctionCategoryDTO> standardDutyItems) {
		this.standardDutyItems = standardDutyItems;
	}

	@SuppressWarnings("unchecked")
	public void addItem(Period entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.add(item);

		//String groupId = ((Function<Period, String>) timelineConfig.getGroupIdFunction()).apply(entity);
		//String parentGroupId = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);

//		if (groupId != null && !this.groupList.contains(groupId)) {
//			TimelineGroup group = new TimelineGroup(entity, timelineConfig);
//			this.groupList.add(group);
//
//			if (parentGroupId != null && !this.groupList.contains(parentGroupId)) {
//				TimelineGroup parentGroup = new TimelineGroup(parentGroupId, parentGroupId);
//				parentGroup.addSubgroup(groupId);
//				this.parentGroupList.add(parentGroup);
//			}
//		}
	}

	public void addItems(List<?> entities, TimelineConfig timelineConfig) {
		for (Object entity : entities) {
			addItem((Period) entity, timelineConfig);
		}

	}



}