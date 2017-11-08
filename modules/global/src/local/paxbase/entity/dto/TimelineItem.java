package local.paxbase.entity.dto;

import java.text.SimpleDateFormat;
import java.util.function.Function;

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
    
    @MetaProperty
    protected Boolean stack;
    

	@MetaProperty
    protected String subgroupId;

	public TimelineItem(Period entity, String content, String groupId, String subgroupId, String style) {
    	super();
    	this.id = entity.getId().toString();
		this.content = content;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.start = entity.getStart() != null ? formatter.format(entity.getStart()):null;
		this.end = entity.getEnd()!=null ? formatter.format(entity.getEnd()):null;
		this.group = groupId;
		this.subgroupId = subgroupId;
		this.style = style;
		this.type = "range";
		this.editable = true;
		this.stack = false;
		
	}

	@SuppressWarnings("unchecked")
	public TimelineItem(Period entity, TimelineConfig timelineConfig) {
    	super();
    	this.id = entity.getId().toString();
		this.content = ((Function<Period, String>) timelineConfig.getItemLabelFunction()).apply(entity);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.start = entity.getStart() != null ? formatter.format(entity.getStart()):null;
		this.end = entity.getEnd()!=null ? formatter.format(entity.getEnd()):null;
		this.group = ((Function<Period, String>) timelineConfig.getGroupIdFunction()).apply(entity);
		this.subgroupId = ((Function<Period, String>) timelineConfig.getParentGroupIdFunction()).apply(entity);
		this.style = ((Function<Period, String>)timelineConfig.getStyleFunction()).apply(entity);
		this.type = ((Function<Period, String>)timelineConfig.getTypeFunction()).apply(entity);
		this.editable = ((Function<Period, Boolean>) timelineConfig.getEditableFunction()).apply(entity);
	}

	public TimelineItem(Object array) {
		// TODO Auto-generated constructor stub
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

    public String getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(String subgroupId) {
		this.subgroupId = subgroupId;
	}
    public Boolean getStack() {
		return stack;
	}

	public void setStack(Boolean stacked) {
		this.stack = stacked;
	}

}