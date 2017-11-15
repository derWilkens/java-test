package local.paxbase.entity.cap.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import local.paxbase.entity.coredata.StandardClientEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import local.paxbase.entity.cap.coredata.Jobfunction;

import local.paxbase.entity.cap.coredata.QualificationType;
import javax.persistence.OneToMany;
import local.paxbase.entity.coredata.Site;

import local.paxbase.entity.coredata.SiteRoleRule;

@NamePattern("%s|name")
@Table(name = "PAXBASE_ROLE")
@Entity(name = "paxbase$Role")
public class Role extends StandardClientEntity {
    private static final long serialVersionUID = -1065729188379194360L;

    @Column(name = "NAME", length = 30)
    protected String name;

    @JoinTable(name = "PAXBASE_FUNCTION_ROLE_LINK",
        joinColumns = @JoinColumn(name = "ROLE_ID"),
        inverseJoinColumns = @JoinColumn(name = "FUNCTION_ID"))
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToMany
    protected List<Jobfunction> functions;






    @OneToMany(mappedBy = "role")
    protected List<RoleQualificationType> roleQualificationType;





    public void setRoleQualificationType(List<RoleQualificationType> roleQualificationType) {
        this.roleQualificationType = roleQualificationType;
    }

    public List<RoleQualificationType> getRoleQualificationType() {
        return roleQualificationType;
    }


    public List<Jobfunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Jobfunction> functions) {
        this.functions = functions;
    }




    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}