package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;

@Table(name = "PAXBASE_SITE_TYPE")
@Entity(name = "paxbase$SiteType")
public class SiteType extends StandardEntity {
    private static final long serialVersionUID = 593633394072817102L;

    @Column(name = "NAME", length = 50)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}