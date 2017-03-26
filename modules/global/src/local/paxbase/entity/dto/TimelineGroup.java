package local.paxbase.entity.dto;

import java.util.ArrayList;
import java.util.Collection;

public class TimelineGroup {
	
	protected String id;
	
	protected String content;

    protected String group;
    
    protected String subgroupOrder;
    
    protected String type; //Can be 'box' (default), 'point', 'range', or 'background'. 
    
    protected String style; //"color: red; background-color: pink;"
    
    protected String title; //Mouse-Over-Text
    
    protected boolean visible;
    
    protected Collection<TimelineItem> nestedGroups;
    
    protected boolean showNestedGroups;

	public TimelineGroup() {
		// TODO Auto-generated constructor stub
		nestedGroups = new ArrayList<TimelineItem>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubgroupOrder() {
		return subgroupOrder;
	}

	public void setSubgroupOrder(String subgroupOrder) {
		this.subgroupOrder = subgroupOrder;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Collection<TimelineItem> getNestedGroups() {
		return nestedGroups;
	}

	public void setNestedGroups(Collection<TimelineItem> nestedGroups) {
		this.nestedGroups = nestedGroups;
	}

	public boolean isShowNestedGroups() {
		return showNestedGroups;
	}

	public void setShowNestedGroups(boolean showNestedGroups) {
		this.showNestedGroups = showNestedGroups;
	}
    
    
    
    
}
