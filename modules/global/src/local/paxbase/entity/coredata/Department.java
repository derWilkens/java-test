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
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@NamePattern("%s|name")
@Table(name = "PAXBASE_DEPARTMENT")
@Entity(name = "paxbase$Department")
public class Department extends StandardClientEntity {
    private static final long serialVersionUID = -7887700637280267255L;

    @Column(name = "NAME", length = 100)
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

    @Column(name = "ACRONYM", length = 15)
    protected String acronym;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @OneToMany(mappedBy = "department")
    protected List<OffshoreUser> members;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @OneToMany(mappedBy = "parentDepartment")
    protected List<Department> subDepartment;

    public void setSubDepartment(List<Department> subDepartment) {
        this.subDepartment = subDepartment;
    }

    public List<Department> getSubDepartment() {
        return subDepartment;
    }


    public void setMembers(List<OffshoreUser> members) {
        this.members = members;
    }

    public List<OffshoreUser> getMembers() {
        return members;
    }


    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getAcronym() {
        return acronym;
    }


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