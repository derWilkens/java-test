/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @Lookup(type = LookupType.DROPDOWN)
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