package local.paxbase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.coredata.StandardClientEntity;
import local.paxbase.entity.coredata.Company;
import local.paxbase.entity.coredata.Site;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@NamePattern(" - , %s|site")
@Table(name = "PAXBASE_DUTY_PERIOD")
@Entity(name = "paxbase$DutyPeriod")
public class DutyPeriod extends Period {
    private static final long serialVersionUID = 848103393103367871L;




    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ON_DUTY_ID")
    protected OffshoreUser personOnDuty;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
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

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    protected Company contractor;

    public void setPersonOnDuty(OffshoreUser personOnDuty) {
        this.personOnDuty = personOnDuty;
    }

    public OffshoreUser getPersonOnDuty() {
        return personOnDuty;
    }


    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Company getContractor() {
        return contractor;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }










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









}