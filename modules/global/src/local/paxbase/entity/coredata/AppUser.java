/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.security.entity.User;

/**
 * @author christian
 */
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