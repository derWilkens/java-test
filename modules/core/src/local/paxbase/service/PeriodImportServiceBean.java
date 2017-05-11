package local.paxbase.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.PeriodImportStage;
import local.paxbase.entity.coredata.Site;

@Service(PeriodImportService.NAME)
public class PeriodImportServiceBean implements PeriodImportService {
	@Inject
	private Persistence persistence;

	// Function periodImport(String rawPeriods)
	// for each line
	// neues item erstellen, nonpersistens entity
	// list hinzufügen
	// list fungiert dann als DS für Tabelle
	public ArrayList<PeriodImportStage> parseCsv(String rawPeriods) {
		ArrayList<PeriodImportStage> parseResult = new ArrayList<PeriodImportStage>();
		String[] lines = rawPeriods.split("[\r\n]");

		for (String line : lines) {

			PeriodImportStage record = new PeriodImportStage();
			String[] values = line.split("\t");
			if (values.length < 5) {
				throw new RuntimeException("Bitte alle Felder füllen: " + line);
			}
			if (!values[0].startsWith("Item")) {
				record.setItemDesignation(values[0]);
				record.setCampaignNumber(values[1]);

				try {
					record.setStartDate(stringToDate(values[2]));
					record.setEndDate(stringToDate(values[3]));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				record.setShutdown(stringToBoolean(values[4]));
				parseResult.add(record);
			}
		}
		return parseResult;
	}

	private Date stringToDate(String rawDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
		Date date;
		date = format.parse(rawDate);
		return date;
	}

	private Boolean stringToBoolean(String rawBoolean) {
		return (!rawBoolean.toLowerCase().startsWith("n"));
	}

	@Override
	public Site getSiteByItemDesignation(String itemDesignation) {
		Site site;
		try (Transaction tx = persistence.createTransaction()) {

			String queryString = "select e from paxbase$Site e where e.itemDesignation = :itemDesignation ";

			TypedQuery<Site> query = persistence.getEntityManager().createQuery(queryString, Site.class);
			query.setParameter("itemDesignation", itemDesignation);
			site = query.getSingleResult();

			tx.commit();
		}
		return site;
	}

	@Override
	public Campaign getCampaignByIdAndSite(String campaignNumber, Site site) {
		Campaign campaign;
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$Campaign e where e.campaignNumber = :campaignNumber and e.site.id = :siteId";
			TypedQuery<Campaign> query = persistence.getEntityManager().createQuery(queryString, Campaign.class);
			query.setParameter("campaignNumber", campaignNumber);
			query.setParameter("siteId", site.getId());
			campaign = query.getFirstResult();
		}
		return campaign;
	}

	@Override
	public void createOrUpdateCampaign(Campaign campaign) {
		try (Transaction tx = persistence.createTransaction()) {
			Campaign tmpCampaign = getCampaignByIdAndSite(campaign.getCampaignNumber(), campaign.getSite());
			//tmpCampaign.setClient(client);
			if (tmpCampaign != null) {
				tmpCampaign.setShutdown(campaign.getShutdown());
				tmpCampaign.setStart(campaign.getStart());
				tmpCampaign.setEnd(campaign.getEnd());
				persistence.getEntityManager().persist(tmpCampaign);
			} else {
				persistence.getEntityManager().persist(campaign);
			}
			tx.commit();
			clearStageTable();
		}

	}

	@Override
	public void clearStageTable() {
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "delete from paxbase$PeriodImportStage e";
			persistence.getEntityManager().createQuery(queryString).executeUpdate();
			tx.commit();
		}
		
	}
}