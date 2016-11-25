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
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import com.haulmont.chile.core.annotations.NamePattern;

/**
 * @author christian
 */
@NamePattern("%s|id")
@Table(name = "PAXBASE_TRANSFER")
@Entity(name = "paxbase$Transfer")
public class Transfer extends StandardClientEntity {
    private static final long serialVersionUID = -5709533341256299692L;

    @Column(name = "SAP_ORDER_NO", length = 50)
    protected String sapOrderNo;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREW_CHANGE_ID")
    protected CrewChange crewChange;

    @Column(name = "MODE_OF_TRANSPORT", nullable = false)
    protected Integer modeOfTransport;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATED_BY_ID")
    protected Company operatedBy;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "transfer")
    protected Set<Waypoint> waypointList;

    public void setWaypointList(Set<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    public Set<Waypoint> getWaypointList() {
        return waypointList;
    }


    public ModeOfTransfer getModeOfTransport() {
        return modeOfTransport == null ? null : ModeOfTransfer.fromId(modeOfTransport);
    }

    public void setModeOfTransport(ModeOfTransfer modeOfTransport) {
        this.modeOfTransport = modeOfTransport == null ? null : modeOfTransport.getId();
    }


    public void setOperatedBy(Company operatedBy) {
        this.operatedBy = operatedBy;
    }

    public Company getOperatedBy() {
        return operatedBy;
    }


    public void setCrewChange(CrewChange crewChange) {
        this.crewChange = crewChange;
    }

    public CrewChange getCrewChange() {
        return crewChange;
    }


    public void setSapOrderNo(String sapOrderNo) {
        this.sapOrderNo = sapOrderNo;
    }

    public String getSapOrderNo() {
        return sapOrderNo;
    }




}