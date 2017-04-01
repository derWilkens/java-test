package local.paxbase.web.campaign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.BoxLayout;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.SplitPanel;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.data.GroupInfo;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import local.paxbase.entity.Campaign;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.coredata.Site;
import local.paxbase.entity.dto.TimelineGroup;
import local.paxbase.entity.dto.TimelineItem;
import local.paxbase.web.toolkit.ui.timelinecomponent.TimelineComponent;

public class CampaignBrowse extends AbstractLookup {

	/**
	 * The {@link CollectionDatasource} instance that loads a list of
	 * {@link Campaign} records to be displayed in
	 * {@link CampaignBrowse#campaignsTable} on the left
	 */
	@Inject
	private GroupDatasource<Campaign, UUID> campaignsDs;

	/**
	 * The {@link Datasource} instance that contains an instance of the selected
	 * entity in {@link CampaignBrowse#campaignsDs}
	 * <p/>
	 * Containing instance is loaded in
	 * {@link CollectionDatasource#addItemChangeListener} with the view,
	 * specified in the XML screen descriptor. The listener is set in the
	 * {@link CampaignBrowse#init(Map)} method
	 */
	@Inject
	private Datasource<Campaign> campaignDs;
	@Inject 
	private GroupDatasource<Campaign,UUID> preferredCampaignsDS;
	/**
	 * The {@link Table} instance, containing a list of {@link Campaign}
	 * records, loaded via {@link CampaignBrowse#campaignsDs}
	 */
	@Inject
	private Table<Campaign> campaignsTable;

	/**
	 * The {@link BoxLayout} instance that contains components on the left side
	 * of {@link SplitPanel}
	 */
	@Inject
	private BoxLayout lookupBox;

	/**
	 * The {@link BoxLayout} instance that contains buttons to invoke Save or
	 * Cancel actions in edit mode
	 */
	@Inject
	private BoxLayout actionsPane;

	/**
	 * The {@link FieldGroup} instance that is linked to
	 * {@link CampaignBrowse#campaignDs} and shows fields of the selected
	 * {@link Campaign} record
	 */
	@Inject
	private FieldGroup fieldGroup;

	@Inject
	private VBoxLayout timelineBox;

	/**
	 * The {@link RemoveAction} instance, related to
	 * {@link CampaignBrowse#campaignsTable}
	 */
	@Named("campaignsTable.remove")
	private RemoveAction campaignsTableRemove;

	@Inject
	private DataSupplier dataSupplier;

	private TimelineComponent timeline;

	/**
	 * {@link Boolean} value, indicating if a new instance of {@link Campaign}
	 * is being created
	 */
	private boolean creating;

	@Override
	public void init(Map<String, Object> params) {
		
		initSiteUserSettings();
		
		timeline = new TimelineComponent();

		campaignsDs.refresh();
		CampaignDTO campaignDTO = new CampaignDTO(preferredCampaignsDS);
		
		timeline.setTimelineGroups(campaignDTO.getGroupList());
		timeline.setTimelineItems(campaignDTO.getTimelineItemList());
		//timeline.

		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);
		timeline.setStart("2017-03-01");
		timeline.setEnd("2017-05-01");
		box.addComponent(timeline);

		campaignsDs.addCollectionChangeListener(e -> {
			// e.getDs().getItemIds().size();
			if (e.getDs() != null) {
				campaignDTO.refresh();
				
				
			}
		});
		campaignDs.addStateChangeListener(e -> {
			if (e.getState() != null) {
				System.out.println(e.getState().toString());
			}
		});
		;
		/*
		 * Adding {@link
		 * com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link
		 * campaignsDs} The listener reloads the selected record with the
		 * specified view and sets it to {@link campaignDs}
		 */
		campaignsDs.addItemChangeListener(e -> {
			if (e.getItem() != null) {
				Campaign reloadedItem = dataSupplier.reload(e.getDs().getItem(), campaignDs.getView());
				campaignDs.setItem(reloadedItem);
			}
		});

		/*
		 * Adding {@link CreateAction} to {@link campaignsTable} The listener
		 * removes selection in {@link campaignsTable}, sets a newly created
		 * item to {@link campaignDs} and enables controls for record editing
		 */
		campaignsTable.addAction(new CreateAction(campaignsTable) {
			@Override
			protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs,
					Map<String, Object> params) {
				campaignsTable.setSelected(Collections.emptyList());
				campaignDs.setItem((Campaign) newItem);
				refreshOptionsForLookupFields();
				enableEditControls(true);
			}
		});

		/*
		 * Adding {@link EditAction} to {@link campaignsTable} The listener
		 * enables controls for record editing
		 */
		campaignsTable.addAction(new EditAction(campaignsTable) {
			@Override
			protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs,
					Map<String, Object> params) {
				if (campaignsTable.getSelected().size() == 1) {
					refreshOptionsForLookupFields();
					enableEditControls(false);
				}
			}
		});

		/*
		 * Setting {@link RemoveAction#afterRemoveHandler} for {@link
		 * campaignsTableRemove} to reset record, contained in {@link
		 * campaignDs}
		 */
		campaignsTableRemove.setAfterRemoveHandler(removedItems -> campaignDs.setItem(null));

		disableEditControls();

	}

	@Inject
	private Table siteUserSettings;
	@Inject
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;
	@Inject
	private ComponentsFactory componentsFactory;

	@SuppressWarnings({ "unchecked" })
	private void initSiteUserSettings() {
		siteUserSettings.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = componentsFactory.createComponent(CheckBox.class);
			userPreferencesDs.refresh();
			for(UserPreference userPreference : userPreferencesDs.getItems()){
				if(entity.getId().equals(userPreference.getEntityUuid()) && userPreference.getContext().equals("CampaignBrowse")){
					checkBox.setValue(true);
				}
			}
				
			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {

					if(checkBox.isChecked()){
				        final DataSupplier dataservice = userPreferencesDs.getDataSupplier();

				        final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
						//UserPreference newItem = new UserPreference();
						newItem.setContext("CampaignBrowse");
						newItem.setEntityUuid((UUID)siteUserSettings.getSingleSelected().getId());
						//userPreferencesDs.setItem((UserPreference) newItem);
						userPreferencesDs.addItem((UserPreference) newItem);
						userPreferencesDs.commit();
					}
					else{
						UserPreference tmp = null;
						Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
						while (iter.hasNext()) {
							UserPreference userPreference = (UserPreference) iter.next();
							if (userPreference.getEntityUuid().equals(siteUserSettings.getSingleSelected().getId())){
								tmp = userPreference;
							}
						}
						userPreferencesDs.removeItem(tmp);
						//userPreferencesDs.getItems().removeIf(f -> f.getEntityUuid().equals(siteUserSettings.getSingleSelected().getId()));
						userPreferencesDs.commit();
					}
						
				}
//		          @Override
//		          public void valueChange(Property.ValueChangeEvent event) {
//		            if(selectedItemIds.contains(itemId)){
//		              selectedItemIds.remove(itemId);
//		            } else {
//		              selectedItemIds.add(itemId);
//		            }
//				}
		          });
			return checkBox;
		});
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

		Campaign editedItem = campaignDs.getItem();
		if (creating) {
			campaignsDs.includeItem(editedItem);
		} else {
			campaignsDs.updateItem(editedItem);
		}
		campaignsTable.setSelected(editedItem);

		disableEditControls();
	}

	/**
	 * Method that is invoked by clicking Cancel button, discards changes and
	 * disables controls for record editing
	 */
	public void cancel() {
		Campaign selectedItem = campaignsDs.getItem();
		if (selectedItem != null) {
			Campaign reloadedItem = dataSupplier.reload(selectedItem, campaignDs.getView());
			campaignsDs.setItem(reloadedItem);
		} else {
			campaignDs.setItem(null);
		}

		disableEditControls();
	}

	/**
	 * Enabling controls for record editing
	 * 
	 * @param creating
	 *            indicates if a new instance of {@link Campaign} is being
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
		campaignsTable.requestFocus();
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

	public Component generateSelectedCell(Site entity) {
		return null; // PlainTextCell(entity.getInstanceName());
	}
}