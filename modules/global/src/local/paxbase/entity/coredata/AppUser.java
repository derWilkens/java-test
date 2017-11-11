/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import local.paxbase.entity.cap.coredata.Jobfunction;

/**
 * @author christian
 */
@Listeners("paxbase_UserEntityListener")
@Entity(name = "paxbase$AppUser")
public class AppUser extends User {
    private static final long serialVersionUID = 6123712345961441544L;

    @Column(name = "CLIENT")
    protected Integer client;

    @JoinTable(name = "PAXBASE_APP_USER_JOBFUNCTION_LINK",
        joinColumns = @JoinColumn(name = "APP_USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "JOBFUNCTION_ID"))
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected List<Jobfunction> jobfunction;

    public void setJobfunction(List<Jobfunction> jobfunction) {
        this.jobfunction = jobfunction;
    }

    public List<Jobfunction> getJobfunction() {
        return jobfunction;
    }


    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getClient() {
        return client;
    }



}