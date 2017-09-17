package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import local.paxbase.entity.OffshoreUser;

@Table(name = "PAXBASE_OE")
@Entity(name = "paxbase$OE")
public class OE extends StandardClientEntity {
    private static final long serialVersionUID = -7887700637280267255L;

    @Column(name = "NAME")
    protected String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_OE_ID")
    protected OE parentOE;

    @Lookup(type = LookupType.SCREEN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEADER_ID")
    protected OffshoreUser leader;

    @Lookup(type = LookupType.SCREEN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPUTY_LEADER_ID")
    protected OffshoreUser deputyLeader;

    public void setLeader(OffshoreUser leader) {
        this.leader = leader;
    }

    public OffshoreUser getLeader() {
        return leader;
    }

    public void setDeputyLeader(OffshoreUser deputyLeader) {
        this.deputyLeader = deputyLeader;
    }

    public OffshoreUser getDeputyLeader() {
        return deputyLeader;
    }


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