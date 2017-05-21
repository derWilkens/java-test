package local.paxbase.web.userpreference;

import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.ColorPicker;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.vaadin.shared.ui.colorpicker.Color;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.coredata.Site;
import local.paxbase.service.UserpreferencesService;

public class UserpreferenceSitecolors extends AbstractWindow {
	@Inject
	private UserpreferencesService userpreferenceService;

	@Inject
	private ComponentsFactory siteColorsComponentsFactory;

	@Inject
	private Table<Site> siteColors;
	@Inject
	private CollectionDatasource<UserPreference, UUID> userPreferencesDs;

	@Override
	public void init(Map<String, Object> params) {
		initSiteUserSettings();
	}

	private void initSiteUserSettings() {
		siteColors.addGeneratedColumn("selected", entity -> {
			// com.vaadin.ui.ColorPicker
			ColorPicker colorPicker = siteColorsComponentsFactory.createComponent(ColorPicker.class);
			userPreferencesDs.refresh();
			for (UserPreference userPreference : userPreferencesDs.getItems()) {
				if (entity.getId().equals(userPreference.getEntityUuid())
						&& userPreference.getContext().equals("SiteColors")) {
					Color siteColor = new Color(Integer.parseInt(userPreference.getUserValue(), 16));
					colorPicker.setValue(siteColor);
				}
			}

			colorPicker.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChanged(ValueChangeEvent e) {

					if (colorPicker.getValue() != null) {
						UserPreference preference = userpreferenceService.getPreference("SiteColors",
								siteColors.getSingleSelected().getId());
						if (preference != null) {

						} else {
							final DataSupplier dataservice = userPreferencesDs.getDataSupplier();
							final UserPreference userPreference = dataservice
									.newInstance(userPreferencesDs.getMetaClass());
							userPreference.setContext("SiteColors");
							userPreference.setEntityUuid((UUID) siteColors.getSingleSelected().getId());
							userPreference.setUserValue(colorPicker.getValue());
							userPreferencesDs.addItem((UserPreference) userPreference);
							userPreferencesDs.commit();
						}
					}
					// else {
					// UserPreference tmp = null;
					// Iterator<UserPreference> iter =
					// userPreferencesDs.getItems().iterator();
					// while (iter.hasNext()) {
					// UserPreference userPreference = (UserPreference)
					// iter.next();
					// try {
					// Log.info(userPreference.getUuid().toString());
					// if
					// (userPreference.getEntityUuid().equals(siteUserSettings.getSingleSelected().getId()))
					// {
					// tmp = userPreference;
					// }
					// } catch (Exception e1) {
					// // TODO Auto-generated catch block
					// e1.printStackTrace();
					// }
					// }
					// userPreferencesDs.removeItem(tmp);
					// userPreferencesDs.commit();
					// }
				}

			});
			return colorPicker;
		});
	}
}