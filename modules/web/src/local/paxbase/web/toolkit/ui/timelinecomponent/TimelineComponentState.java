package local.paxbase.web.toolkit.ui.timelinecomponent;

import java.util.List;

import com.vaadin.shared.ui.JavaScriptComponentState;

import local.paxbase.entity.dto.FunctionCategoryDTO;
import local.paxbase.entity.dto.SiteItem;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;


public class TimelineComponentState extends JavaScriptComponentState {
	
	private static final long serialVersionUID = -5285468521541170565L;
	
	public String start;
	public String end;
	public String format;
	public String dataAttributes;
	public List<TimelineGroup> timelineGroups; 
	public List<TimelineItem> timelineItems;
	public List<SiteItem> siteItems;
	public List<FunctionCategoryDTO> standardDutyItems;
}
