/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.coredata.AppUser;

/**
 * @author christian
 */

@Listeners("paxbase_OffshoreUserEntityListener")
@NamePattern(" %s, %s|lastName,firstName")
@Entity(name = "paxbase$OffshoreUser")
public class OffshoreUser extends AppUser {
    private static final long serialVersionUID = 6555877070622366614L;

    @Column(name = "WEIGHT")
    protected Integer weight;

    @Temporal(TemporalType.DATE)
    @Column(name = "WEIGHT_CHANGE_DATE")
    protected Date weightChangeDate;


    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected local.paxbase.entity.coredata.Company company;

    public local.paxbase.entity.coredata.Company getCompany() {
        return company;
    }

    public void setCompany(local.paxbase.entity.coredata.Company company) {
        this.company = company;
    }






    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeightChangeDate(Date weightChangeDate) {
        this.weightChangeDate = weightChangeDate;
    }

    public Date getWeightChangeDate() {
        return weightChangeDate;
    }


}