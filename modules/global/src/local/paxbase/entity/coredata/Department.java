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
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "PAXBASE_DEPARTMENT")
@Entity(name = "paxbase$Department")
public class Department extends StandardClientEntity {
    private static final long serialVersionUID = -7887700637280267255L;

    @Column(name = "NAME")
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_DEPARTMENT_ID")
    protected Department parentDepartment;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open", "clear"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEADER_ID")
    protected OffshoreUser leader;

    @Lookup(type = LookupType.SCREEN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPUTY_LEADER_ID")
    protected OffshoreUser deputyLeader;

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }



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



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}