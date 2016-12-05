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

/**
 * @author christian
 */
@Table(name = "PAXBASE_CREW_CHANGE")
@Entity(name = "paxbase$CrewChange")
public class CrewChange extends StandardClientEntity {
    private static final long serialVersionUID = 3072080113502808893L;

    @Temporal(TemporalType.DATE)
    @Column(name = "FLIGHT_DATE", nullable = false)
    protected Date flightDate;

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getFlightDate() {
        return flightDate;
    }


}