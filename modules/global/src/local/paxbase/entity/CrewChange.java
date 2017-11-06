/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import local.paxbase.entity.coredata.StandardClientEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.chile.core.annotations.Composition;
import java.util.List;
import javax.persistence.OrderBy;

/**
 * @author christian
 */
@Listeners("paxbase_CrewChangeEntityListener")
@Table(name = "PAXBASE_CREW_CHANGE")
@Entity(name = "paxbase$CrewChange")
public class CrewChange extends StandardClientEntity {
    private static final long serialVersionUID = 3072080113502808893L;

    @Temporal(TemporalType.DATE)
    @Column(name = "FLIGHT_DATE", nullable = false)
    protected Date flightDate;

    @OrderBy("transferOrderNo")
    @Composition
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "crewChange")
    protected List<Transfer> transfers;

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }




    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getFlightDate() {
        return flightDate;
    }


}