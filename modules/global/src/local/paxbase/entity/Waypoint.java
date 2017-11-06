/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.Set;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.coredata.StandardClientEntity;

/**
 * @author christian
 */
@NamePattern("%s %s %s|site,takeOff,transferDuration")
@Table(name = "PAXBASE_WAYPOINT")
@Entity(name = "paxbase$Waypoint")
public class Waypoint extends StandardClientEntity {
    private static final long serialVersionUID = -4973545925621830772L;

    @Temporal(TemporalType.TIME)
    @Column(name = "TAKE_OFF", nullable = false)
    protected Date takeOff;

    @Temporal(TemporalType.TIME)
    @Column(name = "TRANSFER_DURATION")
    protected Date transferDuration;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_ID")
    protected Transfer transfer;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    @Column(name = "ORDER_NO")
    protected Integer orderNo;

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderNo() {
        return orderNo;
    }



    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }



    public Date getTransferDuration() {
        return transferDuration;
    }

    public void setTransferDuration(Date transferDuration) {
        this.transferDuration = transferDuration;
    }



    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }





    public void setTakeOff(Date takeOff) {
        this.takeOff = takeOff;
    }

    public Date getTakeOff() {
        return takeOff;
    }


}