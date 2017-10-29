package local.paxbase.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.PopupView;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import elemental.json.JsonObject;
import local.paxbase.Utils;
import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.service.RotaplanService;
import local.paxbase.service.TimelineService;
import local.paxbase.service.UserpreferencesService;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent.RotaplandChangeListener;

public class RotaTimeline extends AbstractWindow {

	@Inject
	private DataManager dataManager;

	private RotaplanComponent rotaplan;
	private TimelineDTO dto;

	/* UI-Components */
	@Inject
	private PopupView campaignSitePopupView;
	@Inject
	private OptionsGroup campaignSiteOptionsGroup;
	@Inject
	private ScrollBoxLayout timelineBox;
	@Inject
	private CheckBox cbxDisplayCampaigns;

	/* Services */
	@Inject
	private TimelineService timelineDTOService;
	@Inject
	private RotaplanService rotaplanService;
	@Inject
	private UserpreferencesService preferencesService;

	/* Datasources */
	@Inject
	private Datasource<DutyPeriod> dutyPeriodDs;
	@Inject
	private CollectionDatasource<DutyPeriod, UUID> dutyPeriodsDs;
	@Inject
	private CollectionDatasource<FunctionCategory, UUID> functionCategoriesDs;
	@Inject
	private CollectionDatasource<Site, UUID> sitesDs;

	@Override
	public void init(Map<String, Object> params) {

		super.init(params);
		dutyPeriodsDs.refresh();
		// JS-UI-Komonente
		rotaplan = new RotaplanComponent();
		loadRotaplanDto();
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		// box.setWidth("100%");
		box.addComponent(rotaplan);
		rotaplan.setListener(new InnerListener());
		initCampaignSiteOptionGroup();
		initCheckBoxDisplayCampaigns();
	}

	private void loadRotaplanDto() {
		dto = timelineDTOService.getRotoplanDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
			rotaplan.refresh();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initCampaignSiteOptionGroup() {
		sitesDs.refresh();
		List<Site> preferredSiteList = new ArrayList<Site>();
		List<UserPreference> preferredSitesRotaplan = preferencesService
				.getPreferences(UserPreferencesContext.SiteRotaplan);

		for (UserPreference userPreference : preferredSitesRotaplan) {
			for (Iterator iterator = sitesDs.getItemIds().iterator(); iterator.hasNext();) {
				UUID uuid = (UUID) iterator.next();
				if (userPreference.getEntityUuid().equals(uuid)) {
					preferredSiteList.add((Site) campaignSiteOptionsGroup.getOptionsDatasource().getItem(uuid));
				}
			}
		}
		campaignSiteOptionsGroup.setValue(preferredSiteList);

		/* Group EventListener */
		campaignSiteOptionsGroup.addValueChangeListener(e -> {
			// String selectedValue = e.getValue() == null ? "0" :
			// String.valueOf(((Collection) e.getValue()).size());
			// showNotification("selected: " + selectedValue,
			// NotificationType.HUMANIZED);
			LinkedHashSet<Site> currentVal = null;
			LinkedHashSet<Site> prevVal = null;

			currentVal = (LinkedHashSet<Site>) e.getValue();

			if (e.getPrevValue() instanceof LinkedHashSet) {
				prevVal = (LinkedHashSet<Site>) e.getPrevValue();
			}
			if (prevVal != null) {
				Site removedSite = getRemovedItem(prevVal, currentVal);
				if (removedSite != null) {
					preferencesService.deletePreference(UserPreferencesContext.SiteRotaplan, removedSite.getUuid());
				}
			}
			Site addedSite = getAddedItem(prevVal, currentVal);

			if (addedSite != null) {
				preferencesService.createPreference(UserPreferencesContext.SiteRotaplan, addedSite.getUuid(), null);
			}
			loadRotaplanDto();
		});
	}

	private void initCheckBoxDisplayCampaigns() {
		UserPreference preferenceUUID = preferencesService
				.getPreference(UserPreferencesContext.RotaplanDisplayCampaigns, null);
		if (preferenceUUID != null) {
			cbxDisplayCampaigns.setValue(true);
		}
		cbxDisplayCampaigns.addValueChangeListener(event -> {
			if (Boolean.TRUE.equals(event.getValue())) {
				preferencesService.createPreference(UserPreferencesContext.RotaplanDisplayCampaigns, null, "true");
				// showNotification("set", NotificationType.HUMANIZED);

			} else {
				preferencesService.deletePreference(UserPreferencesContext.RotaplanDisplayCampaigns, null);
			}
			loadRotaplanDto();
		});
	}

	private Site getAddedItem(LinkedHashSet<Site> prevVal, LinkedHashSet<Site> currentVal) {
		for (Site site : currentVal) {
			if (prevVal == null || !prevVal.contains(site)) {
				return site;
			}
		}
		return null;
	}

	private Site getRemovedItem(LinkedHashSet<Site> prevVal, LinkedHashSet<Site> currentVal) {
		for (Site site : prevVal) {
			if (!currentVal.contains(site)) {
				return site;
			}
		}
		return null;
	}

	public void openSiteCampaingChooser() {
		campaignSitePopupView.setPopupVisible(true);
	}

	public void openDepartmentChooser() {
		Window departmentChoose = openWindow("paxbase$DepartmentUser.choose", WindowManager.OpenType.DIALOG);
		departmentChoose.addCloseListener(new CloseListener() {

			@Override
			public void windowClosed(String actionId) {
				loadRotaplanDto();
			}
		});
	}

	public void reloadDutyPeriods() {

	}

	class InnerListener implements RotaplandChangeListener {
		boolean isCalledAlready;

		@SuppressWarnings("rawtypes")
		@Override
		public void itemAdded(JsonObject jsonItem) {
			if (isCalledAlready)
				return;
			isCalledAlready = true;
			boolean itemIncomplete = false;
			// if (jsonItem != null && jsonItem.getString("content") != null &&
			// !jsonItem.getString("content").equals("new item")) {

			// Neues Objekt erzeugen im dem DS hinzufügen
			DataSupplier dataservice = dutyPeriodDs.getDataSupplier();
			DutyPeriod newItem = dataservice.newInstance(dutyPeriodDs.getMetaClass());

			// Datum
			try {
				if (jsonItem.hasKey("start")) {
					newItem.setStart(jsonDateToDate(jsonItem.getString("start")));
				} else {
					newItem.setStart(Utils.clearDate(new Date()));
					itemIncomplete = true;
				}

				if (jsonItem.hasKey("end")) {
					newItem.setEnd(jsonDateToDate(jsonItem.getString("end")));
				} else {
					Calendar c = Calendar.getInstance();
					c.setTime(Utils.clearDate(newItem.getStart()));
					c.add(Calendar.DAY_OF_YEAR, 1);
					newItem.setEnd(c.getTime());
					itemIncomplete = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				itemIncomplete = true;
			}

			// Person über dataManager laden (Item field: group)
			if (jsonItem.hasKey("group")) {
				LoadContext<OffshoreUser> loadContext = LoadContext.create(OffshoreUser.class)
						.setId(UUID.fromString(jsonItem.getString("group"))).setView("offshoreUser-browser-view");
				newItem.setPersonOnDuty(dataManager.load(loadContext));
			} else {
				itemIncomplete = true;
			}
			
			functionCategoriesDs.refresh();
			Collection items = functionCategoriesDs.getItems();
			// Wenn eine Site gesetzt ist, dann ist der Typ "Offshore"
			// TODO: kann ja auch ne Onshore-Site sein...
			String content = null;
			if (jsonItem.hasKey("content")) {
				content = jsonItem.getString("content");
				Site site = rotaplanService.getSiteByItemDesignation(content);
				if (site != null) {
					newItem.setSite(site);
					for (Iterator iterator = items.iterator(); iterator.hasNext();) {
						FunctionCategory cat = (FunctionCategory) iterator.next();
						if (cat.getCategoryName().equals("Offshore")) {
							newItem.setFunctionCategory(cat);
							break;
						}
					}
				} else {
					itemIncomplete = true;
				}
			} else {
				itemIncomplete = true;
			}
			
			if (itemIncomplete) {
				openEditor(newItem, OpenType.DIALOG).addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						loadRotaplanDto();
						isCalledAlready = false;
					}
				});
				;
			} else {
				dutyPeriodDs.setItem((DutyPeriod) newItem);
				dutyPeriodsDs.updateItem(newItem);
				getDsContext().commit();
				loadRotaplanDto();
				isCalledAlready = false;
			}
			// }
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			dutyPeriodsDs.refresh();
			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));

			try {

				dutyPeriod.setStart(jsonDateToDate(jsonItem.getString("start")));
				dutyPeriod.setEnd(jsonDateToDate(jsonItem.getString("end")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dutyPeriodDs.setItem(dutyPeriod);
			getDsContext().commit();
		}

		@Override
		public void itemDeleted(JsonObject jsonItem) {
			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (dutyPeriod != null) {
				dutyPeriodsDs.removeItem(dutyPeriod);
				getDsContext().commit();
				loadRotaplanDto();
			}
		}

		private Date jsonDateToDate(String rawDate) throws ParseException {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			Date date;
			date = format.parse(rawDate);
			return date;
		}

		@Override
		public void editItem(String id) {

			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(id));

			if (dutyPeriod != null) {
				Window openEditor = openEditor(dutyPeriod, WindowManager.OpenType.DIALOG);
				openEditor.addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						loadRotaplanDto();
					}
				});
			}
		}

	}
}