package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s %s %s|amountFrom,amountTo,requiredNumber")
@Table(name = "PAXBASE_NUMBER_RANGE_RULE")
@Entity(name = "paxbase$NumberRangeRule")
public class NumberRangeRule extends StandardEntity {
    private static final long serialVersionUID = 297262149072164491L;

    @Column(name = "AMOUNT_FROM")
    protected Integer amountFrom;

    @Column(name = "AMOUNT_TO")
    protected Integer amountTo;

    @Column(name = "REQUIRED_NUMBER")
    protected Integer requiredNumber;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITE_ROLE_RULE_ID")
    protected SiteRoleRule siteRoleRule;

    public void setSiteRoleRule(SiteRoleRule siteRoleRule) {
        this.siteRoleRule = siteRoleRule;
    }

    public SiteRoleRule getSiteRoleRule() {
        return siteRoleRule;
    }


    public void setAmountFrom(Integer amountFrom) {
        this.amountFrom = amountFrom;
    }

    public Integer getAmountFrom() {
        return amountFrom;
    }

    public void setAmountTo(Integer amountTo) {
        this.amountTo = amountTo;
    }

    public Integer getAmountTo() {
        return amountTo;
    }

    public void setRequiredNumber(Integer requiredNumber) {
        this.requiredNumber = requiredNumber;
    }

    public Integer getRequiredNumber() {
        return requiredNumber;
    }


}