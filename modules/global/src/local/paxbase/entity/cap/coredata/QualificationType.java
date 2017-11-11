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
import javax.persistence.OneToMany;

@NamePattern("%s|name")
@Table(name = "PAXBASE_QUALIFICATION_TYPE")
@Entity(name = "paxbase$QualificationType")
public class QualificationType extends StandardClientEntity {
    private static final long serialVersionUID = 154912806578790643L;

    @Column(name = "NAME")
    protected String name;





    @Column(name = "VALIDITY")
    protected Integer validity;

    @OneToMany(mappedBy = "qualificationType")
    protected List<RoleQualificationType> roleQualificationType;

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Integer getValidity() {
        return validity;
    }


    public void setRoleQualificationType(List<RoleQualificationType> roleQualificationType) {
        this.roleQualificationType = roleQualificationType;
    }

    public List<RoleQualificationType> getRoleQualificationType() {
        return roleQualificationType;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}