package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import local.paxbase.entity.coredata.Site;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|campaignNo,site")
@Table(name = "PAXBASE_CAMPAIGN")
@Entity(name = "paxbase$Campaign")
public class Campaign extends StandardEntity {
    private static final long serialVersionUID = 2133165937821283408L;

    @Column(name = "CAMPAIGN_NO", length = 10)
    protected String campaignNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE")
    protected Date beginDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    protected Date endDate;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    public void setCampaignNo(String campaignNo) {
        this.campaignNo = campaignNo;
    }

    public String getCampaignNo() {
        return campaignNo;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


}