package local.paxbase.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import local.paxbase.entity.coredata.Site;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@Listeners("paxbase_AdministrationPeriodListener")
@Table(name = "PAXBASE_ADMINISTRATION_PERIOD")
@Entity(name = "paxbase$AdministrationPeriod")
public class AdministrationPeriod extends Period {
    
	private static final long serialVersionUID = -6112448983753359413L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ON_DUTY_ID")
    protected OffshoreUser personOnDuty;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    public void setPersonOnDuty(OffshoreUser personOnDuty) {
        this.personOnDuty = personOnDuty;
    }

    public OffshoreUser getPersonOnDuty() {
        return personOnDuty;
    }


    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    
	@Override
	public UUID getPreferredUUID() {
		return this.getType().getId();
	}

	@Override
	public String getLabel() {
		return getType().getTypeName();
	}

	@Override
	public String getGroupLabel(GroupedBy groupedBy) {
		if (groupedBy.equals(GroupedBy.Site)){
			return getSite().getSiteName();
		}
		else if(groupedBy.equals(GroupedBy.User)){
			return getPersonOnDuty().getCaption();
		}
			
		return null;
	}
	@Override
	public UUID getGroupId(GroupedBy groupedBy) {
		if (groupedBy.equals(GroupedBy.Site)){
			return getSite().getId();
		}
		else if(groupedBy.equals(GroupedBy.User)){
			return getPersonOnDuty().getId();
		}
			
		return null;
	}

}