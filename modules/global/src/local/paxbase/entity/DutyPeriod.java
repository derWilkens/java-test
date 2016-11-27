package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s - %s, %s|beginDate,endDate,site")
@Table(name = "PAXBASE_DUTY_PERIOD")
@Entity(name = "paxbase$DutyPeriod")
public class DutyPeriod extends StandardClientEntity {
    private static final long serialVersionUID = 848103393103367871L;

    @Temporal(TemporalType.DATE)
    @Column(name = "BEGIN_DATE", nullable = false)
    protected Date beginDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    protected Date endDate;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    protected OffshoreUser user;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;


    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTBOUND_TRANSFER_ID")
    protected Transfer outboundTransfer;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INBOUND_TRANSFER_ID")
    protected Transfer inboundTransfer;

    public void setOutboundTransfer(Transfer outboundTransfer) {
        this.outboundTransfer = outboundTransfer;
    }

    public Transfer getOutboundTransfer() {
        return outboundTransfer;
    }

    public void setInboundTransfer(Transfer inboundTransfer) {
        this.inboundTransfer = inboundTransfer;
    }

    public Transfer getInboundTransfer() {
        return inboundTransfer;
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

    public void setUser(OffshoreUser user) {
        this.user = user;
    }

    public OffshoreUser getUser() {
        return user;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


}