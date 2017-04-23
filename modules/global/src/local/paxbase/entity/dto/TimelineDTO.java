package local.paxbase.entity.dto;

import java.util.HashMap;
import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

import local.paxbase.entity.Period;

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

	public void addItem(Period entity, TimelineConfig timelineConfig) {
		TimelineItem item = new TimelineItem(entity, timelineConfig);
		this.timelineItemList.add(item);
		this.groupList.put(item.getGroup(), new TimelineGroup(entity, timelineConfig));

		String groupId = ((Function<Period, String>) timelineConfig.getGroupFunction()).apply(entity);
		String parentGroupId = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);
		
			TimelineGroup group = new TimelineGroup(entity, timelineConfig);

				TimelineGroup parentGroup = new TimelineGroup(parentGroupId, parentGroupId);

			}
		}
	}

	public void addItems(List<?> entities, TimelineConfig timelineConfig) {
		for (Object entity : entities) {
			addItem((Period) entity, timelineConfig);
		}

	}

}