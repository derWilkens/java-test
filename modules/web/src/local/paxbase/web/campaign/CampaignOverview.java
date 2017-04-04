
package local.paxbase.web.campaign;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.gui.components.WebComponentsHelper;
import com.vaadin.ui.Layout;

import local.paxbase.entity.GroupedBy;
import local.paxbase.entity.Period;
import local.paxbase.entity.UserPreference;
import local.paxbase.entity.coredata.PeriodType;
import local.paxbase.entity.coredata.Site;
import local.paxbase.web.toolkit.ui.timelinecomponent.TimelineComponent;

public class CampaignOverview extends AbstractLookup {

	/**
	 * Alle UserPreferences des aktuell angemeldeten Benutzers
	 */
	@Inject
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;

	/**
	 * PeriodTypes werden in der Tabelle typeUserSettings angezeigt Selektierte
	 * Types werden in die Preferences geschrieben
	 */
	// @Inject
	// private CollectionDatasource<Period, UUID> periodTypesDs;
	@Inject
	private Table<PeriodType> typeUserSettings;

	/**
	 * Sites werden in Tabelle angezeigt Selektierte Sites werden in die
	 * Preferences geschrieben
	 */
	// @Inject
	// private CollectionDatasource<Site, UUID> sitesDs;

	@Inject
	private Table<Site> siteUserSettings;

	@Inject
	private ComponentsFactory typeSelectedComponentsFactory;

	@Inject
	private ComponentsFactory siteSelectedComponentsFactory;

	/**
	 * Alle Kampagnen zur Anzeige in der Timeline, werden anschließend nach
	 * User-Präferenzen gefilter
	 */
	@Inject
	private CollectionDatasource<Period, UUID> campaignsDs;

	/**
	 * Alle ServicePeriods zur Anzeige in der Timeline, werden anschließend nach
	 * User-Präferenzen gefilter
	 */
	@Inject
	private CollectionDatasource<Period, UUID> servicePeriodsDs;

	@Inject
	private VBoxLayout timelineBox;

	private TimelineComponent timeline;

	// private TimelineDTO campaignDTO;
	// private TimelineDTO servicePeriodDTO;

	@Override
	public void init(Map<String, Object> params) {

		userPreferencesDs.refresh();
		campaignsDs.refresh();
		servicePeriodsDs.refresh();
		
		initSiteUserSettings();
		initTypeUserSettings();

		// JS-UI-Komponente
		timeline = new TimelineComponent();

		timeline.addDTO(new TimelineDTO(campaignsDs, userPreferencesDs, GroupedBy.Site));
		timeline.addDTO(new TimelineDTO(servicePeriodsDs, userPreferencesDs, GroupedBy.Site));
		timeline.refresh();
		// new TimelineDTO(GroupedBy.Site);
		// timeline.setTimelineGroups(campaignDTO.getGroupList());
		// (timeline.setTimelineItems(campaignDTO.getTimelineItemList());
		timeline.setStart("2017-03-01");
		timeline.setEnd("2017-05-01");

		com.vaadin.ui.Layout box = (Layout) WebComponentsHelper.unwrap(timelineBox);

		box.addComponent(timeline);

	}

	private void initTypeUserSettings() {
		typeUserSettings.addGeneratedColumn("selected", entity -> {

			CheckBox checkBox = typeSelectedComponentsFactory.createComponent(CheckBox.class);

			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContext().equals("CampaignBrowse")) {
					checkBox.setValue(true);
				}
			}
			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {

					if (checkBox.isChecked()) {
						final DataSupplier dataservice = userPreferencesDs.getDataSupplier();

						final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
						newItem.setContext("CampaignBrowse");
						newItem.setEntityUuid((UUID) typeUserSettings.getSingleSelected().getId());
						userPreferencesDs.addItem((UserPreference) newItem);
						userPreferencesDs.commit();
					} else {
						UserPreference tmp = null;
						Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
						while (iter.hasNext()) {
							UserPreference userPreference = (UserPreference) iter.next();
							if (userPreference.getEntityUuid().equals(typeUserSettings.getSingleSelected().getId())) {
								tmp = userPreference;
							}
						}
						userPreferencesDs.removeItem(tmp);
						userPreferencesDs.commit();
					}
					timeline.refresh();
				}
			});
			return checkBox;
		});

	}

	private void initSiteUserSettings() {
		siteUserSettings.addGeneratedColumn("selected", entity -> {
			CheckBox checkBox = siteSelectedComponentsFactory.createComponent(CheckBox.class);
			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContext().equals("CampaignBrowse")) {
					checkBox.setValue(true);
				}
			}

			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {

					if (checkBox.isChecked()) {
						final DataSupplier dataservice = userPreferencesDs.getDataSupplier();
						final UserPreference newItem = dataservice.newInstance(userPreferencesDs.getMetaClass());
						newItem.setContext("CampaignBrowse");
						newItem.setEntityUuid((UUID) siteUserSettings.getSingleSelected().getId());
						userPreferencesDs.addItem((UserPreference) newItem);
						userPreferencesDs.commit();
					} else {
						UserPreference tmp = null;
						Iterator<UserPreference> iter = userPreferencesDs.getItems().iterator();
						while (iter.hasNext()) {
							UserPreference userPreference = (UserPreference) iter.next();
							if (userPreference.getEntityUuid().equals(siteUserSettings.getSingleSelected().getId())) {
								tmp = userPreference;
							}
						}
						userPreferencesDs.removeItem(tmp);
						userPreferencesDs.commit();
					}
					timeline.refresh();
				}

			});
			return checkBox;
		});
	}

	// public Component generateSelectedCell(Site entity) {
	// return null; // PlainTextCell(entity.getInstanceName());
	// }
}