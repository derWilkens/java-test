package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "PAXBASE_SITE_CATEGORY")
@Entity(name = "paxbase$SiteCategory")
public class SiteCategory extends StandardEntity {
    private static final long serialVersionUID = 8818245036847572405L;

    @Column(name = "NAME", length = 50)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}