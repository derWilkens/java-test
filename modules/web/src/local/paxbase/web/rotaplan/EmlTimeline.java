package local.paxbase.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowManager.OpenType;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.components.Component.ValueChangeEvent;
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
import local.paxbase.entity.dto.TimelineConfig;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.entity.dto.TimelineItem;
import local.paxbase.service.TimelineService;
import local.paxbase.service.UserpreferencesService;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent.RotaplandChangeListener;

public class EmlTimeline extends AbstractWindow {

	@Inject
	private DataManager dataManager;

	private RotaplanComponent rotaplan;
	private TimelineDTO dto;

	/* UI-Components */
	@Inject
	private ScrollBoxLayout timelineBox;
	@Inject
	private OptionsGroup siteChooser;

	/* Services */
	@Inject
	private TimelineService timelineDTOService;
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

	private Site displayedSite = null;
	
	@Override
	public void init(Map<String, Object> params) {

		super.init(params);
		dutyPeriodsDs.refresh();
		rotaplan = new RotaplanComponent();
		loadEmlDto();
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		box.addComponent(rotaplan);
		rotaplan.setListener(new InnerListener());
		initSiteChooser();
	}

	private void loadEmlDto() {
		dto = timelineDTOService.getEmlDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
		}
	}

	private void initSiteChooser() {
		List<Site> preferredSites = preferencesService.getSites(UserPreferencesContext.SiteEml);
		List<UserPreference> displayedSitePreference = preferencesService.getPreferences(UserPreferencesContext.EmlDisplaySite);
		
		if (!displayedSitePreference.isEmpty()) {
			try {
				displayedSite = preferredSites.stream()
						.filter(s -> displayedSitePreference.get(0).getEntityUuid().toString().equals(s.getUuid().toString()))
						.findFirst().get();
			} catch (Exception e1) {
				// die displayed Site ist nicht bei den prefered Sites, macht aber nix. 
			}
		}
		
		Map<String, UUID> map = new LinkedHashMap<>();
		for(Site site:preferredSites){
			map.put(site.getItemDesignation(), site.getId());
		}
		siteChooser.setOptionsMap(map);
		if(displayedSite != null ){
			siteChooser.setValue(displayedSite.getId());
		}
		siteChooser.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChanged(ValueChangeEvent e) {
				preferencesService.deletePreferenceByEntity(UserPreferencesContext.EmlDisplaySite, null);
				preferencesService.createPreference(UserPreferencesContext.EmlDisplaySite, (UUID)e.getValue(), null);
				
			}
		});
	}

	public TimelineConfig getDutyPeriodGroupedByUserConfig() {
		TimelineConfig dutyPeriodConfig = new TimelineConfig();
		dutyPeriodConfig.setGroupIdFunction((DutyPeriod e) -> e.getPersonOnDuty().getUuid().toString());
		dutyPeriodConfig.setGroupLabelFunction((DutyPeriod e) -> e.getPersonOnDuty().getInstanceName());
		dutyPeriodConfig.setParentGroupIdFunction((DutyPeriod e) -> null);
		dutyPeriodConfig.setItemLabelFunction((DutyPeriod e) -> {
			String result = "";
			if (e.getSite() != null) {
				result = e.getSite().getItemDesignation();
			}
			if (e.getFunctionCategory() != null) {
				result = result + " " + e.getFunctionCategory().getCategoryName();
			}
			return result;
		});
		dutyPeriodConfig.setStyleFunction((DutyPeriod e) -> {
			if (null != e.getSite()) {
				String colorHex = preferencesService.getSiteColorPreference(e.getSite().getUuid());
				if (colorHex != null) {
					return "background-color: #" + colorHex + ";";
				}
			}
			return "";
		});
		dutyPeriodConfig.setEditableFunction((DutyPeriod e) -> {
			return true;
		});
		dutyPeriodConfig.setTypeFunction((DutyPeriod e) -> {
			return "range";
		});
		return dutyPeriodConfig;
	}

	class InnerListener implements RotaplandChangeListener {
		boolean isCalledAlready;

		@Override
		public void itemAdded(JsonObject jsonItem) {
			// hier belassen
			// wie wird im Backend aus einem DetachedObject ein AttachedObject?
			// Wenn es unvollständig ist, muss der editor geöffnet werden und es
			// darf vorher nicht gespeichert sein
			// das TimelineItem muss auch zurückgegeben werden

			if (isCalledAlready)
				return;
			isCalledAlready = true;
			boolean itemIncomplete = false;

			// Neues Objekt erzeugen im dem DS hinzufügen
			DataSupplier dataservice = dutyPeriodDs.getDataSupplier();
			DutyPeriod newItem = dataservice.newInstance(dutyPeriodDs.getMetaClass());

			// Datum
			try {
				if (jsonItem.hasKey("start")) {
					newItem.setStart(jsonDateToDateWoTime(jsonItem.getString("start")));
				} else {
					newItem.setStart(Utils.clearDate(new Date()));
					itemIncomplete = true;
				}

				int duration = 1; // entweder das gelieferte Endedatum nehmen,
									// oder die Duration oder 1
				// if (jsonItem.hasKey("end")) {
				// newItem.setEnd(jsonDateToDate(jsonItem.getString("end")));
				// }
				// else
				if (jsonItem.hasKey("duration") && !jsonItem.getString("duration").equals("null")) {
					duration = Integer.parseInt(jsonItem.getString("duration"));
				}

				Calendar c = Calendar.getInstance();
				c.setTime(newItem.getStart());
				c.add(Calendar.DAY_OF_YEAR, duration);
				newItem.setEnd(c.getTime());

			} catch (Exception e) {
				e.printStackTrace();
				itemIncomplete = true;
			}

			// Person über dataManager laden
			if (jsonItem.hasKey("userId")) {
				newItem.setPersonOnDuty(getUserById(jsonItem.getString("userId")));
			} else {
				itemIncomplete = true;
			}

			// Site über dataManager laden
			if (jsonItem.hasKey("siteId") && !jsonItem.getString("siteId").equals("null")) {
				sitesDs.refresh();
				newItem.setSite(sitesDs.getItem(UUID.fromString(jsonItem.getString("siteId"))));
			} else {
				// itemIncomplete = true; nicht jeder Dienst braucht eine Site,
				// wenn doch kann manuell nachgepflegt werden.
			}

			// FunctionCategory über dataManager laden
			if (jsonItem.hasKey("functionCategoryId")) {
				functionCategoriesDs.refresh();
				newItem.setFunctionCategory(
						functionCategoriesDs.getItem(UUID.fromString(jsonItem.getString("functionCategoryId"))));
			} else {
				itemIncomplete = true;
			}

			if (itemIncomplete) {
				openEditor(newItem, OpenType.DIALOG).addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(newItem,
								UserPreferencesContext.Rotaplan);
						rotaplan.addTimelineItem(timelineItem);
						isCalledAlready = false;
					}
				});
				;
			} else {

				dutyPeriodDs.setItem((DutyPeriod) newItem);
				dutyPeriodsDs.updateItem(newItem);
				getDsContext().commit();
				TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(newItem,
						UserPreferencesContext.Rotaplan);
				rotaplan.addTimelineItem(timelineItem);
				isCalledAlready = false;
			}
		}

		private OffshoreUser getUserById(String id) {
			LoadContext<OffshoreUser> loadContext = LoadContext.create(OffshoreUser.class).setId(UUID.fromString(id))
					.setView("offshoreUser-browser-view");
			return dataManager.load(loadContext);
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			dutyPeriodsDs.refresh();
			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (jsonItem.hasKey("group")) {
				// wenn die Person geändert wurde
				if (!dutyPeriod.getPersonOnDuty().getId().toString().equals(jsonItem.getString("group"))) {
					dutyPeriod.setPersonOnDuty(getUserById(jsonItem.getString("group")));
				}
			}
			try {
				dutyPeriod.setStart(jsonDateToDate(jsonItem.getString("start")));
				dutyPeriod.setEnd(jsonDateToDate(jsonItem.getString("end")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// getDsContext().commit();//den Context vorher committen, sonst
			// wird beim schließen gefragt.
			dutyPeriodDs.setItem(dutyPeriod);
			// dutyPeriodDs.commit();
			getDsContext().commit();
			dutyPeriod = dutyPeriodsDs.getItem(dutyPeriod.getId());
			TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(dutyPeriod,
					UserPreferencesContext.Rotaplan);
			rotaplan.addTimelineItem(timelineItem);
		}

		@Override
		public void editItem(String id) {

			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(id));

			if (dutyPeriod != null) {
				Window openEditor = openEditor(dutyPeriod, WindowManager.OpenType.DIALOG);
				openEditor.addCloseListener(new CloseListener() {

					@Override
					public void windowClosed(String actionId) {
						dutyPeriodsDs.refresh();
						DutyPeriod editedDutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(id));
						TimelineItem timelineItem = timelineDTOService.periodToTimelineItem(editedDutyPeriod,
								UserPreferencesContext.Rotaplan);
						rotaplan.addTimelineItem(timelineItem);
					}
				});
			}
		}

		@Override
		public void itemDeleted(JsonObject jsonItem) {
			dutyPeriodsDs.refresh();
			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (dutyPeriod != null) {
				dutyPeriodsDs.removeItem(dutyPeriod);
				getDsContext().commit();
				// Das Item ist im Frontend schon entfernt worden. Aber was ist,
				// wenn das programmatisch gemacht wird?
				rotaplan.removeItem(dutyPeriod);
			}
		}

		private Date jsonDateToDate(String rawDate) throws ParseException {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			return formatter.parse(rawDate);
		}

		private Date jsonDateToDateWoTime(String rawDate) throws ParseException {
			return dateToDateWoTime(jsonDateToDate(rawDate));
		}

		private Date dateToDateWoTime(Date date) throws ParseException {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.parse(formatter.format(date));
		}

		@Override
		public void addSubItem(JsonObject jsonItem) {
			// TODO Auto-generated method stub

		}
	}
}