package local.paxbase.web.userpreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.PopupView;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.Site;
import local.paxbase.service.UserpreferencesService;

public class Preferredsites extends AbstractFrame {

	@Inject
	private PopupView popupView;

	@Inject
	private OptionsGroup optionsGroup;

	@Inject
	private UserpreferencesService preferencesService;
	
	@Inject
	private CollectionDatasource<Site, UUID> sitesDs;

	@Override
	public void init(Map<String, Object> params) {
		super.init(params);
		sitesDs.refresh();
		List<Site> preferredSiteList = new ArrayList<Site>();
		List<UserPreference> preferredSitesRotaplan = preferencesService
				.getPreferences(UserPreferencesContext.SiteRotaplan);

		for (UserPreference userPreference : preferredSitesRotaplan) {
			for (Iterator iterator = sitesDs.getItemIds().iterator(); iterator.hasNext();) {
				UUID uuid = (UUID) iterator.next();
				if (userPreference.getEntityUuid().equals(uuid)) {
					preferredSiteList.add((Site) optionsGroup.getOptionsDatasource().getItem(uuid));
				}
			}
		}
		optionsGroup.setValue(preferredSiteList);
		
		optionsGroup.addValueChangeListener(e -> {
			@SuppressWarnings("rawtypes")
			String selectedValue = e.getValue() == null ? "0" : String.valueOf(((Collection) e.getValue()).size());
			showNotification("selected: " + selectedValue, NotificationType.HUMANIZED);
			LinkedHashSet<Site> currentVal = null;
			LinkedHashSet<Site> prevVal = null;

			currentVal = (LinkedHashSet<Site>) e.getValue();

			if (e.getPrevValue() instanceof LinkedHashSet) {
				prevVal = (LinkedHashSet<Site>) e.getPrevValue();
			}
			if (prevVal != null) {
				Site removedSite = getRemovedItem(prevVal, currentVal);
				if(removedSite != null){
					preferencesService.deletePreference(UserPreferencesContext.SiteRotaplan, removedSite.getUuid());
				}
			}
			Site addedSite = getAddedItem(prevVal, currentVal);

			if (addedSite != null) {
				preferencesService.createPreference(UserPreferencesContext.SiteRotaplan, addedSite.getUuid(), null);
			}
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

	public void openPopup() {
		popupView.setPopupVisible(true);
	}
}