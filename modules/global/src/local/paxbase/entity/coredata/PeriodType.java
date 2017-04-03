package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "PAXBASE_PERIOD_TYPE")
@Entity(name = "paxbase$PeriodType")
public class PeriodType extends StandardEntity {
    private static final long serialVersionUID = 8534126243989364712L;

    @Column(name = "TYPE_NAME", length = 20)
    protected String typeName;

    @Column(name = "TYPE_GROUP", length = 20)
    protected String typeGroup;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }


    public void setTypeGroup(String typeGroup) {
        this.typeGroup = typeGroup;
    }

    public String getTypeGroup() {
        return typeGroup;
    }




}