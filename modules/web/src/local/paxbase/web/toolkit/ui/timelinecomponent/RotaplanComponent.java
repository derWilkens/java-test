package local.paxbase.web.toolkit.ui.timelinecomponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonObject;
import local.paxbase.entity.Period;
import local.paxbase.entity.dto.FunctionCategoryDTO;
import local.paxbase.entity.dto.SiteItem;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;


@JavaScript({"rotaplan-connector.js", "vis.js"})
@StyleSheet({"vis.css", "timeline.css"})
public class RotaplanComponent extends AbstractJavaScriptComponent {
	   
	private static final long serialVersionUID = -1963421147896570853L;
	
	private HashMap<String, TimelineDTO> dtoList;
	
    public interface RotaplandChangeListener {
		void itemAdded(JsonObject jsonItem);
		void itemMoved(JsonObject jsonItem);
		void itemDeleted(JsonObject jsonItem);
		void editItem(String id);
		void addSubItem(JsonObject jsonItem);
    }

    private RotaplandChangeListener listener;
	
	public RotaplanComponent() {
		getState().timelineItems = new ArrayList<TimelineItem>();
		getState().timelineGroups = new ArrayList<TimelineGroup>();
		getState().siteItems = new ArrayList<SiteItem>();
		getState().standardDutyItems = new ArrayList<FunctionCategoryDTO>();
		
		dtoList = new HashMap<String,TimelineDTO>();
		
		addFunction("itemAdded", arguments -> {
            listener.itemAdded(arguments.getObject(0));
        });
		addFunction("itemMoved", arguments -> {
            listener.itemMoved(arguments.getObject(0));
        });
		addFunction("itemDeleted", arguments -> {
            listener.itemDeleted(arguments.getObject(0));
        });
		addFunction("editItem", arguments -> {
            listener.editItem(arguments.getString(0));
        });
	}

    @Override
    protected TimelineComponentState getState() {
        return (TimelineComponentState) super.getState();
    }
    
	public String getStart() {
		return getState().start;
	}
	public void setStart(String start) {
		getState().start = start;
	}
	public String getEnd() {
		return getState().end;
	}
	public void setEnd(String end) {
		getState().end = end;
	}
	public String getFormat() {
		return getState().format;
	}
	public void setFormat(String format) {
		getState().format = format;
	}
	public String getDataAttributes() {
		return getState().dataAttributes;
	}
	public void setDataAttributes(String dataAttributes) {
		getState().dataAttributes = dataAttributes;
	}
	public Collection<TimelineGroup> getTimelineGroups() {
		return getState().timelineGroups;
	}
	public void setTimelineGroups(List<TimelineGroup> timelineGroups) {
		getState().timelineGroups = timelineGroups;
	}
	public Collection<TimelineItem> getTimelineItems() {
		return getState().timelineItems;
	}
	public void setTimelineItems(List<TimelineItem> timelineItems) {
		getState().timelineItems = timelineItems;
	}
	public Collection<SiteItem> getSiteItems() {
		return getState().siteItems;
	}
	public void setSiteItems(List<SiteItem> siteItems) {
		getState().siteItems = siteItems;
	}
	public Collection<FunctionCategoryDTO> getStandardDutyItems() {
		return getState().standardDutyItems;
	}
	public void setStandardDutyItems(List<FunctionCategoryDTO> standardDutyItems) {
		getState().standardDutyItems = standardDutyItems;
	}	
    public RotaplandChangeListener getListener() {
        return listener;
    }
    public void setListener(RotaplandChangeListener listener) {
        this.listener = listener;
    }

	public void addDTO(String key, TimelineDTO dto) {
		getState().timelineItems.clear();
		getState().timelineGroups.clear(); 
		getState().siteItems.clear();
		getState().standardDutyItems.clear();
		
		dtoList.put(key, dto);
		for (TimelineDTO timelineDTO : dtoList.values()) {
			getState().timelineItems.addAll(timelineDTO.getTimelineItemList());
			getState().timelineGroups.addAll(timelineDTO.getGroupList());
			getState().siteItems.addAll(timelineDTO.getSiteItems());
			getState().standardDutyItems.addAll(timelineDTO.getStandardDutyItems());
		}
	}

	public void addTimelineItem(TimelineItem timelineItem) {
		getState().timelineItems.removeIf(i -> i.getId().equals(timelineItem.getId()));
		getState().timelineItems.add(timelineItem);
	}

	public void removeItem(Period period) {
		getState().timelineItems.removeIf(i -> i.getId().equals(period.getId()));
		
	}
}