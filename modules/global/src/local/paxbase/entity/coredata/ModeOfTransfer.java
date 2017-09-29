package local.paxbase.entity.coredata;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|mode")
@Table(name = "PAXBASE_MODE_OF_TRANSFER")
@Entity(name = "paxbase$ModeOfTransfer")
public class ModeOfTransfer extends StandardClientEntity {
    private static final long serialVersionUID = 7132601331111253754L;

    @Column(name = "MODE_", length = 50)
    protected String mode;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }



}