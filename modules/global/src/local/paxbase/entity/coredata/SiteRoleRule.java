package local.paxbase.entity.coredata;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import local.paxbase.entity.cap.coredata.Role;

@NamePattern("%s %s|site,role")
@Table(name = "PAXBASE_SITE_ROLE_RULE")
@Entity(name = "paxbase$SiteRoleRule")
public class SiteRoleRule extends StandardEntity {
    private static final long serialVersionUID = -8195290062635403814L;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ID")
    protected Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    protected Role role;



    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "siteRoleRule")
    protected List<NumberRangeRule> rangeRule;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public void setRangeRule(List<NumberRangeRule> rangeRule) {
        this.rangeRule = rangeRule;
    }

    public List<NumberRangeRule> getRangeRule() {
        return rangeRule;
    }



    public void setSite(Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }


}