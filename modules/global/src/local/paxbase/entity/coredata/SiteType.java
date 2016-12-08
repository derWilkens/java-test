package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;

@Table(name = "PAXBASE_SITE_TYPE")
@Entity(name = "paxbase$SiteType")
public class SiteType extends StandardEntity {
    private static final long serialVersionUID = 593633394072817102L;

    @Column(name = "TYPE_", length = 50)
    protected String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }



}