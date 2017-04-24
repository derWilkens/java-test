package local.paxbase.entity.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.Period;

@MetaClass(name = "paxbase$TimelineDTO")
public class TimelineDTO extends AbstractNotPersistentEntity {
	private static final long serialVersionUID = 3757688296152498888L;

	@MetaProperty
	protected Set<TimelineGroup> groupList;

	@MetaProperty
	protected Set<TimelineGroup> parentGroupList;

	@MetaProperty
	protected Set<TimelineItem> timelineItemList;

	public void setParentGroupList(Set<TimelineGroup> parentGroupList) {
		this.parentGroupList = parentGroupList;
	}

	public Set<TimelineGroup> getParentGroupList() {
		return parentGroupList;
	}

	public Set<TimelineGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(Set<TimelineGroup> groupList) {
		this.groupList = groupList;
	}

	public TimelineDTO() {
		this.timelineItemList = new HashSet<TimelineItem>();
		this.groupList = new HashSet<TimelineGroup>();
		this.parentGroupList = new HashSet<TimelineGroup>();
	}

	public void setTimelineItemList(Set<TimelineItem> timelineItemList) {
		this.timelineItemList = timelineItemList;
	}

	public Set<TimelineItem> getTimelineItemList() {
		return timelineItemList;
	}

	@SuppressWarnings("unchecked")
	public void addItem(Period entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.add(item);

		String groupId = ((Function<Period, String>) timelineConfig.getGroupFunction()).apply(entity);
		String parentGroupId = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);

		if (groupId != null && !this.groupList.contains(groupId)) {
			TimelineGroup group = new TimelineGroup(entity, timelineConfig);
			this.groupList.add(group);

			if (parentGroupId != null && !this.groupList.contains(parentGroupId)) {
				TimelineGroup parentGroup = new TimelineGroup(parentGroupId, parentGroupId);
				parentGroup.addSubgroup(groupId);
				this.parentGroupList.add(parentGroup);

			}
		}
	}

	public void addItems(List<?> entities, TimelineConfig timelineConfig) {
		for (Object entity : entities) {
			addItem((Period) entity, timelineConfig);
		}

	}

}