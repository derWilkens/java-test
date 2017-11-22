package local.paxbase.entity.period;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.coredata.Site;
import javax.persistence.MappedSuperclass;
import com.haulmont.cuba.core.global.DesignSupport;
import java.util.List;
import javax.persistence.OneToMany;

import local.paxbase.entity.period.Period;
import local.paxbase.entity.period.DutyPeriod;

@Table(name = "PAXBASE_OPERATION_PERIOD")
@Entity(name = "paxbase$OperationPeriod")
@NamePattern(" - , |")
public class OperationPeriod extends SitePeriod {
    private static final long serialVersionUID = 848103393103367871L;













    @OneToMany(mappedBy = "operationPeriod")
    protected List<AttendencePeriod> attendencePeriods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_PERIOD_ID")
    protected OperationPeriod parentPeriod;


    public void setAttendencePeriods(List<AttendencePeriod> attendencePeriods) {
        this.attendencePeriods = attendencePeriods;
    }

    public List<AttendencePeriod> getAttendencePeriods() {
        return attendencePeriods;
    }


    public OperationPeriod getParentPeriod() {
        return parentPeriod;
    }

    public void setParentPeriod(OperationPeriod parentPeriod) {
        this.parentPeriod = parentPeriod;
    }
































}