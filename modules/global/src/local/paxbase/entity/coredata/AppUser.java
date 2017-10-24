/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.core.entity.annotation.Listeners;

/**
 * @author christian
 */
@Listeners("paxbase_UserEntityListener")
@Entity(name = "paxbase$AppUser")
public class AppUser extends User {
    private static final long serialVersionUID = 6123712345961441544L;

    @Column(name = "CLIENT")
    protected Integer client;

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getClient() {
        return client;
    }



}