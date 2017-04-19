package local.paxbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;
import javax.persistence.Inheritance;
import javax.persistence.DiscriminatorColumn;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import local.paxbase.entity.coredata.Company;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "PAXBASE_CONTRACTOR_DUTY_PERIOD")
@Entity(name = "paxbase$ContractorDutyPeriod")
public class ContractorDutyPeriod extends Period {
    private static final long serialVersionUID = -5054440920627308284L;

    @Lookup(type = LookupType.DROPDOWN)
    @OnDelete(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    protected Company contractor;

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public Company getContractor() {
        return contractor;
    }


}