package local.paxbase.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import local.paxbase.entity.coredata.Site;

@Table(name = "PAXBASE_SERVICE_PERIOD")
@Entity(name = "paxbase$ServicePeriod")
public class ServicePeriod extends Period {
    
	private static final long serialVersionUID = -6112448983753359413L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFSHORE_USER_ID")
    protected OffshoreUser offshoreUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


    public void setOffshoreUser(OffshoreUser offshoreUser) {
        this.offshoreUser = offshoreUser;
    }

    public OffshoreUser getOffshoreUser() {
        return offshoreUser;
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
			return getOffshoreUser().getCaption();
		}
			
		return null;
	}
	@Override
	public UUID getGroupId(GroupedBy groupedBy) {
		if (groupedBy.equals(GroupedBy.Site)){
			return getSite().getId();
		}
		else if(groupedBy.equals(GroupedBy.User)){
			return getOffshoreUser().getId();
		}
			
		return null;
	}

}