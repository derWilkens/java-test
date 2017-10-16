package local.paxbase.web.site;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.PopupView;

public class PreferredSiteBrowse extends AbstractLookup {
	
	@Inject
	private PopupView popupView;

	public void openPopUp() {
		popupView.setPopupVisible(true);
	}
}