package local.paxbase.web.toolkit.ui.timelinecomponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonArray;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;
import local.paxbase.web.campaign.TimelineDTO;

@SuppressWarnings("serial")
@JavaScript({"timelinecomponent-connector.js", "vis.js"})
@StyleSheet({"vis.css"})
public class TimelineComponent extends AbstractJavaScriptComponent {
	   
	private List<TimelineDTO> dtoList;
	
    public interface ValueChangeListener {
        void valueChanged(String[] newData);
    }

    private ValueChangeListener listener;
    
	
	public TimelineComponent() {
		dtoList = new ArrayList<TimelineDTO>();
		addFunction("valueChanged", arguments -> {
            JsonArray array = arguments.getArray(0);
            String[] values = new String[1];
            values[0] = array.getString(0);
          
            listener.valueChanged(values);
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
	public void setTimelineGroups(Collection<TimelineGroup> timelineGroups) {
		getState().timelineGroups = timelineGroups;
	}
	public Collection<TimelineItem> getTimelineItems() {
		return getState().timelineItems;
	}
	public void setTimelineItems(Collection<TimelineItem> timelineItems) {
		getState().timelineItems = timelineItems;
	}
	public void addDTO(TimelineDTO dto){
		dtoList.add(dto);
	}
	public void refresh(){
		getState().timelineItems = new ArrayList<TimelineItem>();
		getState().timelineGroups = new ArrayList<TimelineGroup>();
		
		for (TimelineDTO timelineDTO : dtoList) {
			timelineDTO.refresh();
			getState().timelineItems.addAll(timelineDTO.getTimelineItemList());
			getState().timelineGroups.addAll(timelineDTO.getGroupList());
		}
		
	}
	
	
	
	
}