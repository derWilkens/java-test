/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.coredata.StandardClientEntity;
import local.paxbase.entity.coredata.Company;
import local.paxbase.entity.coredata.ModeOfTransfer;

/**
 * @author christian
 */
@NamePattern("%s|id")
@Table(name = "PAXBASE_TRANSFER")
@Entity(name = "paxbase$Transfer")
public class Transfer extends StandardClientEntity {
    private static final long serialVersionUID = -5709533341256299692L;

    @Column(name = "TRANSFER_ORDER_NO", length = 50)
    protected String transferOrderNo;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREW_CHANGE_ID")
    protected CrewChange crewChange;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATED_BY_ID")
    protected Company operatedBy;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "transfer")
    protected Set<Waypoint> waypointList;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODE_OF_TRANSFER_ID")
    protected ModeOfTransfer modeOfTransfer;

    public void setModeOfTransfer(ModeOfTransfer modeOfTransfer) {
        this.modeOfTransfer = modeOfTransfer;
    }

    public ModeOfTransfer getModeOfTransfer() {
        return modeOfTransfer;
    }


    public Company getOperatedBy() {
        return operatedBy;
    }

    public void setOperatedBy(Company operatedBy) {
        this.operatedBy = operatedBy;
    }



    public void setTransferOrderNo(String transferOrderNo) {
        this.transferOrderNo = transferOrderNo;
    }

    public String getTransferOrderNo() {
        return transferOrderNo;
    }


    public void setWaypointList(Set<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    public Set<Waypoint> getWaypointList() {
        return waypointList;
    }




    public void setCrewChange(CrewChange crewChange) {
        this.crewChange = crewChange;
    }

    public CrewChange getCrewChange() {
        return crewChange;
    }





}