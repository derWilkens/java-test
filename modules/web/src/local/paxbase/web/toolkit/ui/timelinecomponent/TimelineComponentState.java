package local.paxbase.web.toolkit.ui.timelinecomponent;

import java.util.Collection;

import com.vaadin.shared.ui.JavaScriptComponentState;

import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;

@SuppressWarnings("serial")
public class TimelineComponentState extends JavaScriptComponentState {
	
	public String start;
	public String end;
	public String format;
	public String dataAttributes;
	public Collection<TimelineGroup> timelineGroups; 
	public Collection<TimelineItem> timelineItems;
	
}
