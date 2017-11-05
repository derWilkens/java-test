package local.paxbase.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.util.Date;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "paxbase$DutyPeriodDTO")
public class DutyPeriodDTO extends BaseUuidEntity {
    private static final long serialVersionUID = -7791200629921336685L;

    @MetaProperty
    protected String personId;

    @MetaProperty
    protected String siteId;

    @MetaProperty
    protected String itemDesignation;

    @MetaProperty
    protected String functionCategoryId;

    @MetaProperty
    protected Date startDate;

    @MetaProperty
    protected String categoryName;

    @MetaProperty
    protected Date endDate;

    @MetaProperty
    protected Integer duration;

    @MetaProperty
    protected String color;

    public void setItemDesignation(String itemDesignation) {
        this.itemDesignation = itemDesignation;
    }

    public String getItemDesignation() {
        return itemDesignation;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }


    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setFunctionCategoryId(String functionCategoryId) {
        this.functionCategoryId = functionCategoryId;
    }

    public String getFunctionCategoryId() {
        return functionCategoryId;
    }


    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }


}