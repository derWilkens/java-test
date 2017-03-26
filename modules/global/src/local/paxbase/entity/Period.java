package local.paxbase.entity;

import javax.persistence.MappedSuperclass;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;

@MappedSuperclass
public class Period extends StandardEntity {
    private static final long serialVersionUID = -5029609650607107962L;

    @Temporal(TemporalType.DATE)
    @Column(name = "START_")
    protected Date start;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_")
    protected Date end;

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