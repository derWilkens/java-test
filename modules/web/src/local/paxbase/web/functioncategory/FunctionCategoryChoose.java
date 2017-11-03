package local.paxbase.web.functioncategory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.TreeTable;
import com.haulmont.cuba.gui.data.HierarchicalDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import local.paxbase.entity.UserPreference;
import local.paxbase.entity.UserPreferencesContext;
import local.paxbase.entity.coredata.FunctionCategory;
import local.paxbase.service.UserpreferencesService;

public class FunctionCategoryChoose extends AbstractLookup {
	
	@Inject
	private ComponentsFactory functionCategorySelectedComponentsFactory;
	
	@Inject
	protected TreeTable<FunctionCategory> functionCategoriesTable;

	@Inject
	private UserpreferencesService preferencesService;

	@Inject
	protected HierarchicalDatasource<FunctionCategory, UUID> functionCategoriesDs;

	@Override
	public void init(Map<String, Object> params) {
		
		functionCategoriesDs.refresh();
		
		List<UserPreference> preferredRotaplanFunctionCategories = preferencesService
				.getPreferences(UserPreferencesContext.Rotaplan);
		functionCategoriesTable.expandAll();

	
		functionCategoriesTable.addGeneratedColumn("selected", entity -> {
		CheckBox checkBox = functionCategorySelectedComponentsFactory.createComponent(CheckBox.class);

		presetDepartementTreeCheckboxes(preferredRotaplanFunctionCategories, entity, checkBox);
		checkBox.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChanged(ValueChangeEvent e) {
				e.getPrevValue();
				e.getValue();
				
				if (checkBox.isChecked()) {
					preferencesService.createPreference(UserPreferencesContext.Rotaplan,
							functionCategoriesTable.getSingleSelected().getId(), null);
				} else {
					preferencesService.deletePreference(UserPreferencesContext.Rotaplan,
							functionCategoriesTable.getSingleSelected().getId());
				}
			}
		});
		return checkBox;
	});
	}
	private void presetDepartementTreeCheckboxes(List<UserPreference> preferredItem, FunctionCategory entity,
			CheckBox checkBox) {

		for (UserPreference userPreference : preferredItem) {
			if (entity.getId().equals(userPreference.getEntityUuid())) {
				checkBox.setValue(true);
			}
		}
	}
	public void closeWindow(){
		this.close(CLOSE_ACTION_ID,true);
	}
}