package local.paxbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import local.paxbase.entity.coredata.Site;

@NamePattern("%s %s|campaignNumber,site")
@Table(name = "PAXBASE_CAMPAIGN")
@Entity(name = "paxbase$Campaign")
public class Campaign extends Period {
    private static final long serialVersionUID = 2133165937821283408L;

    @Column(name = "CAMPAIGN_NUMBER", length = 10)
    protected String campaignNumber;

    @Column(name = "SHUTDOWN_")
    protected Boolean shutdown;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


    public void setShutdown(Boolean shutdown) {
        this.shutdown = shutdown;
    }

    public Boolean getShutdown() {
        return shutdown;
    }


    public void setCampaignNumber(String campaignNumber) {
        this.campaignNumber = campaignNumber;
    }

    public String getCampaignNumber() {
        return campaignNumber;
    }

    
}