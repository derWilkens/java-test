package local.paxbase.entity.period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s |campaignNumber")
@Table(name = "PAXBASE_MAINTENANCE_CAMPAIGN")
@Entity(name = "paxbase$MaintenanceCampaign")
public class MaintenanceCampaign extends SitePeriod {
	private static final long serialVersionUID = 2133165937821283408L;

	@Column(name = "CAMPAIGN_NUMBER", length = 10)
	protected String campaignNumber;

	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
	}

	public String getCampaignNumber() {
		return campaignNumber;
	}

}