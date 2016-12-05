/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.chile.core.annotations.NamePattern;


/**
 * @author christian
 */
@NamePattern("%s|companyName")
@Table(name = "PAXBASE_COMPANY")
@Entity(name = "paxbase$Company")
public class Company extends StandardClientEntity {
    private static final long serialVersionUID = -1802880052825458111L;

    @Column(name = "COMPANY_NAME", nullable = false, length = 100)
    protected String companyName;

    @Column(name = "CONTACT_PERSON", length = 100)
    protected String contactPerson;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    protected String email;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


}