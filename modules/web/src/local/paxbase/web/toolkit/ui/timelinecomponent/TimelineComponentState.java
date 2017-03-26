package local.paxbase.web.toolkit.ui.timelinecomponent;

import java.util.Collection;

import com.vaadin.shared.ui.JavaScriptComponentState;

import local.paxbase.entity.dto.TimelineItem;

public class TimelineComponentState extends JavaScriptComponentState {
	
	public String start;
	public String end;
	public String format;
	public String dataAttributes;
	public Collection<TimelineItem> durationList;
	
}
