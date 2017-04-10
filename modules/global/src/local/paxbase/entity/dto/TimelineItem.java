package local.paxbase.entity.dto;

import java.text.SimpleDateFormat;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;

import local.paxbase.entity.AbstractNotPersistentStringIdEntity;
import local.paxbase.entity.Period;

@MetaClass(name = "paxbase$TimelineItem")
public class TimelineItem extends AbstractNotPersistentStringIdEntity {
    private static final long serialVersionUID = -906092269229615052L;

    @MetaProperty
    protected String content;

    @MetaProperty
    protected String start;

    @MetaProperty
    protected String end;

    @MetaProperty
    protected String group;

    @MetaProperty
    protected String type;

    @MetaProperty
    protected String style;

    @MetaProperty
    protected String title;

    @MetaProperty
    protected Boolean editable;

    public TimelineItem(Period entity, String content, String groupId) {
    	super();
    	this.id = entity.getId().toString();
		this.content = content;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.start = entity.getStart() != null ? formatter.format(entity.getStart()):null;
		this.end = entity.getEnd()!=null ? formatter.format(entity.getEnd()):null;
		this.group = groupId;
		this.type = "box";
		this.editable = false;
		
	}

	public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getEditable() {
        return editable;
    }



}