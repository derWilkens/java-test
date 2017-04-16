package local.paxbase.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.cuba.core.entity.StandardEntity;

import local.paxbase.DateFormatter;
import local.paxbase.entity.coredata.PeriodType;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import local.paxbase.entity.coredata.FunctionCategory;

@NamePattern("%s %s %s|start,end,category")
@MappedSuperclass
public class Period extends StandardEntity {
    private static final long serialVersionUID = -5029609650607107962L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_")
    protected Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_")
    protected Date end;
    
    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    protected FunctionCategory category;

    public void setCategory(FunctionCategory category) {
        this.category = category;
    }

    public FunctionCategory getCategory() {
        return category;
    }


    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

}