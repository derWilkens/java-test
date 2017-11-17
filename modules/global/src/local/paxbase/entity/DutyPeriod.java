package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.coredata.AppUser;
import local.paxbase.entity.coredata.Site;

@NamePattern(" - , %s|site")
@Table(name = "PAXBASE_DUTY_PERIOD")
@Entity(name = "paxbase$DutyPeriod")
public class DutyPeriod extends Period {
    private static final long serialVersionUID = 848103393103367871L;




    @Lookup(type = LookupType.SCREEN)
    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ON_DUTY_ID")
    protected AppUser personOnDuty;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;
    public AppUser getPersonOnDuty() {
        return personOnDuty;
    }

    public void setPersonOnDuty(AppUser personOnDuty) {
        this.personOnDuty = personOnDuty;
    }










    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
























}