package local.paxbase.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.Metadata;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.PeriodImportStage;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.Site;

@Service(PeriodImportService.NAME)
public class PeriodImportServiceBean implements PeriodImportService {
	@Inject
	private Persistence persistence;

	@Inject
	private Metadata metadata;

	@SuppressWarnings("resource")
	public int parseCsv(String rawPeriods) {
		int counter = 0;
		clearStageTable();

		String[] lines = rawPeriods.split("[\r\n]");

		PeriodImportStage periodImportItem;
		for (String line : lines) {
			Transaction tx = persistence.createTransaction();
			String[] values = line.split("\t");
			String errorMsg = "";
			if (!values[0].startsWith("NAS")) {
				periodImportItem = metadata.create(PeriodImportStage.class);
				if (values.length < 5) {
					errorMsg += "Bitte alle Felder füllen: " + line + "\n";
				}
				else{
				periodImportItem.setItemDesignation(values[0]);
				periodImportItem.setCampaignNumber(values[1]);
				if (periodImportItem.getCampaignNumber() == null) {
					errorMsg += "Kampagnen-Nr nicht gesetzt \n";
				}

				try {
					getSiteByItemDesignation(values[0]);
				} catch (Exception e) {
					errorMsg += "NAS nicht gefunden: " + values[0] + "\n";
				}

				try {
					periodImportItem.setStartDate(stringToDate(values[2]));
				} catch (ParseException e) {
					errorMsg += "Startdatum muss im Format dd.mm.jjjj vorliegen: " + values[2] + "\n";
				}
				try {
					periodImportItem.setEndDate(stringToDate(values[3]));
				} catch (ParseException e) {
					errorMsg += "Endedatum muss im Format dd.mm.jjjj vorliegen: " + values[3] + "\n";
				}
				periodImportItem.setShutdown(stringToBoolean(values[4]));
				}
				try {
					periodImportItem.setImportLog(errorMsg);
					persistence.getEntityManager().persist(periodImportItem);
					tx.commit();
					
				} catch (Exception e) {
					e.printStackTrace();
					tx = persistence.createTransaction();
					periodImportItem = metadata.create(PeriodImportStage.class);
					periodImportItem.setImportLog(errorMsg + " " + e.getMessage());
					persistence.getEntityManager().persist(periodImportItem);
					tx.commit();
				}
				counter++;
			}
		}

		return counter;
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
	public Campaign getCampaignByIdAndItemDesignation(String campaignNumber, String itemDesignation) {
		Campaign campaign;
		
			String queryString = "select e from paxbase$Campaign e where e.campaignNumber = :campaignNumber and e.site.itemDesignation = :itemDesignation";
			TypedQuery<Campaign> query = persistence.getEntityManager().createQuery(queryString, Campaign.class);
			query.setParameter("campaignNumber", campaignNumber);
			query.setParameter("itemDesignation", itemDesignation);
			campaign = query.getFirstResult();
		
		return campaign;
	}

	@Override
	public int createOrUpdateCampaign() {
		int counter = 0;
		try (Transaction tx = persistence.createTransaction()) {

			List<PeriodImportStage> importItemList = persistence.getEntityManager()
					.createQuery("select e from paxbase$PeriodImportStage e where e.importLog = ''",
							PeriodImportStage.class)
					.getResultList();

			FunctionCategory kampagne = getFunctionCategoryByName("Kampagne");
			FunctionCategory abschaltkampagne = getFunctionCategoryByName("Abschaltkampagne");

			for (PeriodImportStage importItem : importItemList) {
				boolean error = false;
				Campaign tmpCampaign;
				tmpCampaign = getCampaignByIdAndItemDesignation(importItem.getCampaignNumber(),
						importItem.getItemDesignation());

				if (tmpCampaign == null) {
					tmpCampaign = metadata.create(Campaign.class);

					try {
						tmpCampaign.setSite(getSiteByItemDesignation(importItem.getItemDesignation()));
					} catch (Exception e) {
						e.printStackTrace();
						importItem.setImportLog("NAS konnte nicht gefunden werden: " + importItem.getItemDesignation());
						persistence.getEntityManager().persist(importItem);
						error = true;
					}
					tmpCampaign.setCampaignNumber(importItem.getCampaignNumber());
					if (importItem.getShutdown() == true) {
						tmpCampaign.setFunctionCategory(abschaltkampagne);
					} else {
						tmpCampaign.setFunctionCategory(kampagne);
					}
				}
				tmpCampaign.setShutdown(importItem.getShutdown());
				tmpCampaign.setStart(importItem.getStartDate());
				tmpCampaign.setEnd(importItem.getEndDate());

				if (!error) {
					persistence.getEntityManager().persist(tmpCampaign);
					persistence.getEntityManager().remove(importItem);
					counter++;
				}

			}

			tx.commit();
		}
		
		return counter;
	}

	private FunctionCategory getFunctionCategoryByName(String categoryName) {
		FunctionCategory functionCategory;
		try (Transaction tx = persistence.createTransaction()) {
			String queryString = "select e from paxbase$FunctionCategory e where e.categoryName = :categoryName";
			TypedQuery<FunctionCategory> query = persistence.getEntityManager().createQuery(queryString,
					FunctionCategory.class);
			query.setParameter("categoryName", categoryName);
			functionCategory = query.getFirstResult();
		}
		return functionCategory;
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