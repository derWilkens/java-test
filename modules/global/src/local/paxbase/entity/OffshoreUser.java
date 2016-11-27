/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.security.entity.User;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.chile.core.annotations.MetaProperty;
import javax.persistence.Transient;
import com.haulmont.cuba.core.entity.Category;

/**
 * @author christian
 */

@Table(name = "PAXBASE_OFFSHORE_USER")
@NamePattern(" %s|user")
@Entity(name = "paxbase$OffshoreUser")
public class OffshoreUser extends StandardClientEntity {
    private static final long serialVersionUID = 6555877070622366614L;

    @Column(name = "WEIGHT")
    protected Integer weight;

    @Temporal(TemporalType.DATE)
    @Column(name = "WEIGHT_CHANGE_DATE")
    protected Date weightChangeDate;


    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @Transient
    @MetaProperty
    protected String osFirstname;

    @Transient
    @MetaProperty
    protected String osLastname;

    @Transient
    @MetaProperty
    protected String osEmail;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    protected Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }



    public void setOsFirstname(String osFirstname) {
        this.osFirstname = osFirstname;
    }

    public String getOsFirstname() {
        return osFirstname;
    }

    public void setOsLastname(String osLastname) {
        this.osLastname = osLastname;
    }

    public String getOsLastname() {
        return osLastname;
    }

    public void setOsEmail(String osEmail) {
        this.osEmail = osEmail;
    }

    public String getOsEmail() {
        return osEmail;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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