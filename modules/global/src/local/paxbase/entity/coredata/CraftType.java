/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

/**
 * @author christian
 */
@NamePattern("%s|name")
@Table(name = "PAXBASE_CRAFT_TYPE")
@Entity(name = "paxbase$CraftType")
public class CraftType extends StandardClientEntity {
    private static final long serialVersionUID = 5779475331537662563L;

    @Column(name = "NAME", nullable = false, length = 50)
    protected String name;

    @Column(name = "SEATS", nullable = false)
    protected Integer seats;

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getSeats() {
        return seats;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}