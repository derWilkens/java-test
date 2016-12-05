package local.paxbase.entity.coredata;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import local.paxbase.entity.coredata.CraftType;

@Table(name = "PAXBASE_PAYLOAD")
@Entity(name = "paxbase$Payload")
public class Payload extends StandardEntity {
    private static final long serialVersionUID = -6052308096298262761L;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CRAFT_TYPE_ID")
    protected CraftType craftType;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SITE_A_ID")
    protected Site siteA;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SITE_B_ID")
    protected Site siteB;

    @Column(name = "PAYLOAD", nullable = false)
    protected Integer payload;

    public CraftType getCraftType() {
        return craftType;
    }

    public void setCraftType(CraftType craftType) {
        this.craftType = craftType;
    }




    public void setSiteA(Site siteA) {
        this.siteA = siteA;
    }

    public Site getSiteA() {
        return siteA;
    }

    public void setSiteB(Site siteB) {
        this.siteB = siteB;
    }

    public Site getSiteB() {
        return siteB;
    }

    public void setPayload(Integer payload) {
        this.payload = payload;
    }

    public Integer getPayload() {
        return payload;
    }


}