package local.paxbase.entity.dto;

import java.util.ArrayList;
import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.GroupedBy;

@MetaClass(name = "paxbase$TimelineDTO")
public class TimelineDTO extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 3757688296152498888L;

    @MetaProperty
    protected List<TimelineGroup> groupList;

    @MetaProperty
    protected List<TimelineItem> timelineItemList;

	private GroupedBy groupedBy;

    public List<TimelineGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<TimelineGroup> groupList) {
        this.groupList = groupList;
    }

    

    public TimelineDTO(GroupedBy groupedBy) {
		this.groupedBy = groupedBy;
		this.timelineItemList = new ArrayList<TimelineItem>();
		this.groupList = new ArrayList<TimelineGroup>();
	}

	
    public void setTimelineItemList(List<TimelineItem> timelineItemList) {
        this.timelineItemList = timelineItemList;
    }

    public List<TimelineItem> getTimelineItemList() {
        return timelineItemList;
    }

}