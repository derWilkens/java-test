package local.paxbase.entity.dto;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.Period;

@MetaClass(name = "paxbase$TimelineDTO")
public class TimelineDTOX extends AbstractNotPersistentEntity {
	private static final long serialVersionUID = 3757688296152498888L;

	@MetaProperty
	protected HashMap<String, TimelineGroup> groupList;

	@MetaProperty
	protected HashMap<String, TimelineItem> timelineItemList;

	public HashMap<String, TimelineGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(HashMap<String, TimelineGroup> groupList) {
		this.groupList = groupList;
	}

	public TimelineDTOX() {
		this.timelineItemList = new HashMap<String, TimelineItem>();
		this.groupList = new HashMap<String, TimelineGroup>();
	}

	public void setTimelineItemList(HashMap<String, TimelineItem> timelineItemList) {
		this.timelineItemList = timelineItemList;
	}

	public HashMap<String, TimelineItem> getTimelineItemList() {
		return timelineItemList;
	}

	@SuppressWarnings("unchecked")
	public void addItem(Period entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.put(item.getId(), item);

		String groupId = ((Function<Period, String>) timelineConfig.getGroupFunction()).apply(entity);
		String parentGroupId = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);
		
		if (groupId != null && !this.groupList.containsKey(groupId)) {
			TimelineGroup group = new TimelineGroup(entity, timelineConfig);
			this.groupList.put(item.getGroup(), group);

			if (parentGroupId != null && !this.groupList.containsKey(parentGroupId)) {
				TimelineGroup parentGroup = new TimelineGroup(parentGroupId, parentGroupId);
				parentGroup.addSubgroup(groupId);
				this.groupList.put(item.getGroup(), parentGroup);

			}
		}
	}

	public void addItems(List<?> entities, TimelineConfig timelineConfig) {
		for (Object entity : entities) {
			addItem((Period) entity, timelineConfig);
		}

	}

}