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

@NamePattern("%s %s %s|start,end,type")
@MappedSuperclass
public class Period extends StandardEntity implements PreferredEntity {
    private static final long serialVersionUID = -5029609650607107962L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_")
    protected Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_")
    protected Date end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected PeriodType type;
    
    public void setType(PeriodType type) {
        this.type = type;
    }

    public PeriodType getType() {
        return type;
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

	@Override
	public UUID getPreferredUUID() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return getType().getTypeName() + " " + DateFormatter.toMMJJJJ(getStart()) + " - " + DateFormatter.toMMJJJJ(getEnd());
	}

	@Override
	public String getGroupLabel(GroupedBy groupedBy) {
		return "";
	}

	@Override
	public UUID getGroupId(GroupedBy groupedBy) {
		// TODO Auto-generated method stub
		return null;
	}


}