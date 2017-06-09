package local.paxbase.entity.cap.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

@NamePattern("%s|name")
@Table(name = "PAXBASE_JOBFUNCTION")
@Entity(name = "paxbase$Jobfunction")
public class Jobfunction extends StandardEntity {
    private static final long serialVersionUID = -5146984712222632851L;

    @Column(name = "NAME", length = 30)
    protected String name;

    @OrderBy("name ASC")
    @JoinTable(name = "PAXBASE_FUNCTION_ROLE_LINK",
        joinColumns = @JoinColumn(name = "FUNCTION_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected List<Role> role;

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }




    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}