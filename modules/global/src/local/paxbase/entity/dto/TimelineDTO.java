package local.paxbase.entity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.GroupedBy;

@MetaClass(name = "paxbase$TimelineDTO")
public class TimelineDTO extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 3757688296152498888L;

    @MetaProperty
    protected HashMap<String,TimelineGroup> groupList;

    @MetaProperty
    protected List<TimelineItem> timelineItemList;


    public  HashMap<String,TimelineGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList( HashMap<String,TimelineGroup> groupList) {
        this.groupList = groupList;
    }

    public TimelineDTO() {
		this.timelineItemList = new ArrayList<TimelineItem>();
		this.groupList = new  HashMap<String,TimelineGroup>();
	}

    public void setTimelineItemList(List<TimelineItem> timelineItemList) {
        this.timelineItemList = timelineItemList;
    }

    public List<TimelineItem> getTimelineItemList() {
        return timelineItemList;
    }

	public void addItem(Campaign entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.add(item);
		this.groupList.put(item.getGroup(), new TimelineGroup(entity, timelineConfig));
		
	}

}