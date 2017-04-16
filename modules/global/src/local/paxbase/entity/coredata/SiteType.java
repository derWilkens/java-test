package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table(name = "PAXBASE_SITE_TYPE")
@Entity(name = "paxbase$SiteType")
public class SiteType extends StandardEntity {
    private static final long serialVersionUID = 593633394072817102L;

    @Column(name = "TYPE_", length = 50)
    protected String type;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_TYPE_ID")
    protected SiteType parentType;

    public void setParentType(SiteType parentType) {
        this.parentType = parentType;
    }

    public SiteType getParentType() {
        return parentType;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }



}