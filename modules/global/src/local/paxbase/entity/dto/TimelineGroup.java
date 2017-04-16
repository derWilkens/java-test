package local.paxbase.entity.dto;

import java.util.List;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;

import local.paxbase.entity.AbstractNotPersistentStringIdEntity;

@MetaClass(name = "paxbase$TimelineGroup")
public class TimelineGroup extends AbstractNotPersistentStringIdEntity {
    private static final long serialVersionUID = 3768626212104478342L;

    @MetaProperty
    protected String content;

    @MetaProperty
    protected String subgroupOrder;

    @MetaProperty
    protected String type;

    @MetaProperty
    protected String style;

    @MetaProperty
    protected String title;

    @MetaProperty
    protected Boolean visible;

    @MetaProperty
    protected List<String> nestedGroups;

    @MetaProperty
    protected Boolean showNestedGroups;
    

	public TimelineGroup(String id, String content) {
		this.id = id;
		this.content = content;
		visible = true;
	}

	public List<String> getNestedGroups() {
        return nestedGroups;
    }

    public void setNestedGroups(List<String> nestedGroups) {
        this.nestedGroups = nestedGroups;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSubgroupOrder(String subgroupOrder) {
        this.subgroupOrder = subgroupOrder;
    }

    public String getSubgroupOrder() {
        return subgroupOrder;
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

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setShowNestedGroups(Boolean showNestedGroups) {
        this.showNestedGroups = showNestedGroups;
    }

    public Boolean getShowNestedGroups() {
        return showNestedGroups;
    }
}