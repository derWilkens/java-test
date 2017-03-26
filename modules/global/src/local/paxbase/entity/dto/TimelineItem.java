package local.paxbase.entity.dto;

import java.text.SimpleDateFormat;

import local.paxbase.entity.Period;


public class TimelineItem  {
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -825092735814905631L;

    //http://visjs.org/docs/timeline/#items
    
    protected String content;

    protected String start;

    protected String end;
    
    protected String group;
    
    protected String subgroup;
    
    protected String type; //Can be 'box' (default), 'point', 'range', or 'background'. 
    
    protected String style; //"color: red; background-color: pink;"
    
    protected String title; //Mouse-Over-Text
    
    protected boolean editable;
    
    

    public TimelineItem(Period period, String content) {
		super();
		this.content = content;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.start = formatter.format(period.getStart());
		this.end = formatter.format(period.getEnd());
				
	}

	public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
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

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}


    


}