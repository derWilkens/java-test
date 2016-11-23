/*
 * Copyright (c) 2016 saas
 */
package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.global.SendingStatus;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import com.haulmont.chile.core.annotations.NamePattern;

/**
 * @author christian
 */
@NamePattern("%s|name")
@Table(name = "SAAS_SITE")
@Entity(name = "saas$Site")
public class Site extends StandardClientEntity {
    private static final long serialVersionUID = -1161554407313338235L;

    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    protected String name;

    @Column(name = "SAP_DESIGNATION", length = 4)
    protected String sapDesignation;

    @Column(name = "SITE_TYPE", nullable = false)
    protected String siteType;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SITE_ID")
    protected Site parentSite;



    public void setParentSite(Site parentSite) {
        this.parentSite = parentSite;
    }

    public Site getParentSite() {
        return parentSite;
    }


    public SiteTypeEnum getSiteType() {
        return siteType == null ? null : SiteTypeEnum.fromId(siteType);
    }

    public void setSiteType(SiteTypeEnum siteType) {
        this.siteType = siteType == null ? null : siteType.getId();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSapDesignation(String sapDesignation) {
        this.sapDesignation = sapDesignation;
    }

    public String getSapDesignation() {
        return sapDesignation;
    }


}