package local.paxbase.web.rotaplan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.BoxLayout;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.KeyCombination;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.ScrollBoxLayout;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.EntityOp;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import elemental.json.JsonObject;
import local.paxbase.entity.DutyPeriod;
import local.paxbase.entity.OffshoreUser;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineDTO;
import local.paxbase.service.RotaplanService;
import local.paxbase.service.TimelineService;
import local.paxbase.web.dutyperiod.DutyPeriodBrowse;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent;
import local.paxbase.web.toolkit.ui.timelinecomponent.RotaplanComponent.RotaplandChangeListener;

public class DutyPeriodRotaplan extends AbstractLookup {
	private RotaplanComponent rotaplan;
	@Inject
	private TimelineService timelineDTOService;
	@Inject
	private RotaplanService rotaplanService;
	
	private TimelineDTO dto;
	@Inject
	private ScrollBoxLayout timelineBox;
	@Inject
	private DataManager dataManager;
	@Inject
	private CollectionDatasource functionCategoriesDs;
	/* Start generated Code */
	/**
	 * The {@link CollectionDatasource} instance that loads a list of
	 * {@link DutyPeriod} records to be displayed in
	 * {@link DutyPeriodBrowse#dutyPeriodsTable} on the left
	 */
	@Inject
	private CollectionDatasource<DutyPeriod, UUID> dutyPeriodsDs;

	/**
	 * The {@link Datasource} instance that contains an instance of the selected
	 * entity in {@link DutyPeriodBrowse#dutyPeriodsDs}
	 * <p/>
	 * Containing instance is loaded in
	 * {@link CollectionDatasource#addItemChangeListener} with the view,
	 * specified in the XML screen descriptor. The listener is set in the
	 * {@link DutyPeriodBrowse#init(Map)} method
	 */
	@Inject
	private Datasource<DutyPeriod> dutyPeriodDs;

	/**
	 * The {@link Table} instance, containing a list of {@link DutyPeriod}
	 * records, loaded via {@link DutyPeriodBrowse#dutyPeriodsDs}
	 */
	@Inject
	private Table<DutyPeriod> dutyPeriodsTable;

	/**
	 * The {@link BoxLayout} instance that contains components on the left side
	 * of {@link SplitPanel}
	 */
	@Inject
	private BoxLayout lookupBox;

	/**
	 * The {@link BoxLayout} instance that contains components on the right side
	 * of {@link SplitPanel}
	 */
	@Inject
	private BoxLayout editBox;

	/**
	 * The {@link BoxLayout} instance that contains buttons to invoke Save or
	 * Cancel actions in edit mode
	 */
	@Inject
	private BoxLayout actionsPane;

	/**
	 * The {@link FieldGroup} instance that is linked to
	 * {@link DutyPeriodBrowse#dutyPeriodDs} and shows fields of the selected
	 * {@link DutyPeriod} record
	 */
	@Inject
	private FieldGroup fieldGroup;

	/**
	 * The {@link RemoveAction} instance, related to
	 * {@link DutyPeriodBrowse#dutyPeriodsTable}
	 */
	@Named("dutyPeriodsTable.remove")
	private RemoveAction dutyPeriodsTableRemove;

	@Inject
	private DataSupplier dataSupplier;

	/**
	 * {@link Boolean} value, indicating if a new instance of {@link DutyPeriod}
	 * is being created
	 */
	private boolean creating;

	@Override
	public void init(Map<String, Object> params) {
		// JS-UI-Komonente
		rotaplan = new RotaplanComponent();
		dto = timelineDTOService.getRotoplanDto();
		if (dto != null) {
			rotaplan.addDTO("rotaplan", dto);
			rotaplan.refresh();
		}
		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		// box.setWidth("100%");
		box.addComponent(rotaplan);
		rotaplan.setListener(new InnerListener());

		/* Start generated Code */
		/*
		 * Adding {@link
		 * com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link
		 * dutyPeriodsDs} The listener reloads the selected record with the
		 * specified view and sets it to {@link dutyPeriodDs}
		 */
		dutyPeriodsDs.addItemChangeListener(e -> {
			if (e.getItem() != null) {
				DutyPeriod reloadedItem = dataSupplier.reload(e.getDs().getItem(), dutyPeriodDs.getView());
				dutyPeriodDs.setItem(reloadedItem);
			}
		});

		/*
		 * Adding {@link CreateAction} to {@link dutyPeriodsTable} The listener
		 * removes selection in {@link dutyPeriodsTable}, sets a newly created
		 * item to {@link dutyPeriodDs} and enables controls for record editing
		 */
		dutyPeriodsTable.addAction(new CreateAction(dutyPeriodsTable) {
			@Override
			protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs,
					Map<String, Object> params) {
				dutyPeriodsTable.setSelected(Collections.emptyList());
				dutyPeriodDs.setItem((DutyPeriod) newItem);
				refreshOptionsForLookupFields();
				enableEditControls(true);
			}
		});

		/*
		 * Adding {@link EditAction} to {@link dutyPeriodsTable} The listener
		 * enables controls for record editing
		 */
		dutyPeriodsTable.addAction(new EditAction(dutyPeriodsTable) {
			@Override
			protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs,
					Map<String, Object> params) {
				if (dutyPeriodsTable.getSelected().size() == 1) {
					refreshOptionsForLookupFields();
					enableEditControls(false);
				}
			}

			@Override
			public void refreshState() {
				if (target != null) {
					CollectionDatasource ds = target.getDatasource();
					if (ds != null && !captionInitialized) {
						setCaption(messages.getMainMessage("actions.Edit"));
					}
				}
				super.refreshState();
			}

			@Override
			protected boolean isPermitted() {
				CollectionDatasource ownerDatasource = target.getDatasource();
				boolean entityOpPermitted = security.isEntityOpPermitted(ownerDatasource.getMetaClass(),
						EntityOp.UPDATE);
				if (!entityOpPermitted) {
					return false;
				}
				return super.isPermitted();
			}
		});

		/*
		 * Setting {@link RemoveAction#afterRemoveHandler} for {@link
		 * dutyPeriodsTableRemove} to reset record, contained in {@link
		 * dutyPeriodDs}
		 */
		dutyPeriodsTableRemove.setAfterRemoveHandler(removedItems -> dutyPeriodDs.setItem(null));

		/*
		 * Adding ESCAPE shortcut that invokes cancel() method
		 */
		editBox.addShortcutAction(
				new ShortcutAction(new KeyCombination(KeyCombination.Key.ESCAPE), shortcutTriggeredEvent -> cancel()));

		disableEditControls();
	}

	private void refreshOptionsForLookupFields() {
		for (Component component : fieldGroup.getOwnComponents()) {
			if (component instanceof LookupField) {
				CollectionDatasource optionsDatasource = ((LookupField) component).getOptionsDatasource();
				if (optionsDatasource != null) {
					optionsDatasource.refresh();
				}
			}
		}
	}

	/**
	 * Method that is invoked by clicking Ok button after editing an existing or
	 * creating a new record
	 */
	public void save() {
		if (!validate(Collections.singletonList(fieldGroup))) {
			return;
		}
		getDsContext().commit();

		DutyPeriod editedItem = dutyPeriodDs.getItem();
		if (creating) {
			dutyPeriodsDs.includeItem(editedItem);
		} else {
			dutyPeriodsDs.updateItem(editedItem);
		}
		dutyPeriodsTable.setSelected(editedItem);

		disableEditControls();
	}

	/**
	 * Method that is invoked by clicking Cancel button, discards changes and
	 * disables controls for record editing
	 */
	public void cancel() {
		DutyPeriod selectedItem = dutyPeriodsDs.getItem();
		if (selectedItem != null) {
			DutyPeriod reloadedItem = dataSupplier.reload(selectedItem, dutyPeriodDs.getView());
			dutyPeriodsDs.setItem(reloadedItem);
		} else {
			dutyPeriodDs.setItem(null);
		}

		disableEditControls();
	}

	/**
	 * Enabling controls for record editing
	 * 
	 * @param creating
	 *            indicates if a new instance of {@link DutyPeriod} is being
	 *            created
	 */
	private void enableEditControls(boolean creating) {
		this.creating = creating;
		initEditComponents(true);
		fieldGroup.requestFocus();
	}

	/**
	 * Disabling editing controls
	 */
	private void disableEditControls() {
		initEditComponents(false);
		dutyPeriodsTable.requestFocus();
	}

	/**
	 * Initiating edit controls, depending on if they should be enabled/disabled
	 * 
	 * @param enabled
	 *            if true - enables editing controls and disables controls on
	 *            the left side of the splitter if false - visa versa
	 */
	private void initEditComponents(boolean enabled) {
		fieldGroup.setEditable(enabled);
		actionsPane.setVisible(enabled);
		lookupBox.setEnabled(!enabled);
	}

	class InnerListener implements RotaplandChangeListener {

		@Override
		public void itemAdded(JsonObject jsonItem) {
			if (!jsonItem.getString("content").equals("new item")) {
				
				DataSupplier dataservice = dutyPeriodDs.getDataSupplier();
				DutyPeriod newItem = dataservice.newInstance(dutyPeriodDs.getMetaClass());

				dutyPeriodsTable.setSelected(Collections.emptyList());
				dutyPeriodDs.setItem((DutyPeriod) newItem);
				refreshOptionsForLookupFields();
				enableEditControls(true);

				try {
					newItem.setStart(jsonDateToDate(jsonItem.getString("start")));
					newItem.setEnd(jsonDateToDate(jsonItem.getString("end")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String userUuid = jsonItem.getString("group");
				LoadContext<OffshoreUser> loadContext = LoadContext.create(OffshoreUser.class)
						.setId(UUID.fromString(userUuid)).setView("offshoreUser-browser-view");
				newItem.setPersonOnDuty(dataManager.load(loadContext));

				@SuppressWarnings("rawtypes")
				Collection items = functionCategoriesDs.getItems();
				String content = jsonItem.getString("content");
				
				Site site = rotaplanService.getSiteByItemDesignation(content);
				if(site != null){
					newItem.setSite(site);
					//newItem.setFunctionCategory(functionCategoriesDs.get );
				} else {
					for (Iterator iterator = items.iterator(); iterator.hasNext();) {
						FunctionCategory cat = (FunctionCategory) iterator.next();
						if (cat.getCategoryName().equals(content)) {
							newItem.setFunctionCategory(cat);
							break;
						}
					}
				}
				save();

				rotaplan.addDTO("rotaplan", timelineDTOService.getRotoplanDto());
				rotaplan.refresh();
			}
		}

		@Override
		public void itemMoved(JsonObject jsonItem) {
			enableEditControls(false);

			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));

			try {

				dutyPeriod.setStart(jsonDateToDate(jsonItem.getString("start")));
				dutyPeriod.setEnd(jsonDateToDate(jsonItem.getString("end")));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dutyPeriodDs.setItem(dutyPeriod);
			save();

		}

		@Override
		public void itemDeleted(JsonObject jsonItem) {
			DutyPeriod dutyPeriod = dutyPeriodsDs.getItem(UUID.fromString(jsonItem.getString("id")));
			if (dutyPeriod != null) {
				dutyPeriodsDs.removeItem(dutyPeriod);
				getDsContext().commit();
				rotaplan.addDTO("rotaplan", timelineDTOService.getRotoplanDto());
				rotaplan.refresh();
			}
		}

	}

	private Date jsonDateToDate(String rawDate) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		Date date;
		date = format.parse(rawDate);
		return date;
	}
}