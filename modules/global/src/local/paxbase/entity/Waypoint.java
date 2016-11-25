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

/**
 * @author christian
 */
@NamePattern("%s|site")
@Table(name = "PAXBASE_WAYPOINT")
@Entity(name = "paxbase$Waypoint")
public class Waypoint extends StandardClientEntity {
    private static final long serialVersionUID = -4973545925621830772L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TAKE_OFF", nullable = false)
    protected Date takeOff;

    @Column(name = "TRANSFER_DURATION")
    protected Integer transferDuration;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREV_WAYPOINT_ID")
    protected Waypoint prevWaypoint;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEXT_WAYPOINT_ID")
    protected Waypoint nextWaypoint;

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

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }



    public Waypoint getPrevWaypoint() {
        return prevWaypoint;
    }

    public void setPrevWaypoint(Waypoint prevWaypoint) {
        this.prevWaypoint = prevWaypoint;
    }


    public Waypoint getNextWaypoint() {
        return nextWaypoint;
    }

    public void setNextWaypoint(Waypoint nextWaypoint) {
        this.nextWaypoint = nextWaypoint;
    }


    public void setTakeOff(Date takeOff) {
        this.takeOff = takeOff;
    }

    public Date getTakeOff() {
        return takeOff;
    }

    public void setTransferDuration(Integer transferDuration) {
        this.transferDuration = transferDuration;
    }

    public Integer getTransferDuration() {
        return transferDuration;
    }


}