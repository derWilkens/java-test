package local.paxbase.entity.dto;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import java.util.Date;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|id")
@MetaClass(name = "paxbase$DutyPeriodDTO")
public class DutyPeriodDTO extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = -7791200629921336685L;

    @MetaProperty
    protected String personUuid;

    @MetaProperty
    protected String functionCategoryUuid;

    @MetaProperty
    protected Date startDate;

    @MetaProperty
    protected Date endDate;

    @MetaProperty
    protected String siteUuid;

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

    public String getPersonUuid() {
        return personUuid;
    }

    public void setFunctionCategoryUuid(String functionCategoryUuid) {
        this.functionCategoryUuid = functionCategoryUuid;
    }

    public String getFunctionCategoryUuid() {
        return functionCategoryUuid;
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

    public void setSiteUuid(String siteUuid) {
        this.siteUuid = siteUuid;
    }

    public String getSiteUuid() {
        return siteUuid;
    }


}