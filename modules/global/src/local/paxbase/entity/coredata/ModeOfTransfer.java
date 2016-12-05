package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "PAXBASE_MODE_OF_TRANSFER")
@Entity(name = "paxbase$ModeOfTransfer")
public class ModeOfTransfer extends StandardEntity {
    private static final long serialVersionUID = 7132601331111253754L;

    @Column(name = "NAME", length = 50)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}