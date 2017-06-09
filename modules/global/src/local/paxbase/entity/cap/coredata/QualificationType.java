package local.paxbase.entity.cap.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import local.paxbase.entity.coredata.StandardClientEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|name")
@Table(name = "PAXBASE_QUALIFICATION_TYPE")
@Entity(name = "paxbase$QualificationType")
public class QualificationType extends StandardClientEntity {
    private static final long serialVersionUID = 154912806578790643L;

    @Column(name = "NAME")
    protected String name;

    @JoinTable(name = "PAXBASE_ROLE_QUALIFICATION_TYPE_LINK",
        joinColumns = @JoinColumn(name = "QUALIFICATION_TYPE_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected List<Role> roles;

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}