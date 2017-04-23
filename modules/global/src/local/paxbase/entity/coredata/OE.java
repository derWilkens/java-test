package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table(name = "PAXBASE_OE")
@Entity(name = "paxbase$OE")
public class OE extends StandardClientEntity {
    private static final long serialVersionUID = -7887700637280267255L;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_OE_ID")
    protected OE parentOE;

    public OE getParentOE() {
        return parentOE;
    }

    public void setParentOE(OE parentOE) {
        this.parentOE = parentOE;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}