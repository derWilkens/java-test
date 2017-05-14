package local.paxbase.web.userpreference;

import local.paxbase.entity.UserPreference;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class UserPreferenceSiteColors extends AbstractLookup {
    

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link UserPreference} records
     * to be displayed in {@link UserPreferenceBrowse#userPreferencesTable} on the left
     */
    @Inject
    private CollectionDatasource<UserPreference, UUID> userPreferencesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link UserPreferenceBrowse#userPreferencesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link UserPreferenceBrowse#init(Map)} method
     */
    @Inject
    private Datasource<UserPreference> userPreferenceDs;

    /**
     * The {@link Table} instance, containing a list of {@link UserPreference} records,
     * loaded via {@link UserPreferenceBrowse#userPreferencesDs}
     */
    @Inject
    private Table<UserPreference> userPreferencesTable;

    /**
     * The {@link BoxLayout} instance that contains components on the left side
     * of {@link SplitPanel}
     */
    @Inject
    private BoxLayout lookupBox;

    /**
     * The {@link BoxLayout} instance that contains buttons to invoke Save or Cancel actions in edit mode
     */
    @Inject
    private BoxLayout actionsPane;

    /**
     * The {@link FieldGroup} instance that is linked to {@link UserPreferenceBrowse#userPreferenceDs}
     * and shows fields of the selected {@link UserPreference} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link UserPreferenceBrowse#userPreferencesTable}
     */
    @Named("userPreferencesTable.remove")
    private RemoveAction userPreferencesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link UserPreference} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link userPreferencesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link userPreferenceDs}
         */
        userPreferencesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                UserPreference reloadedItem = dataSupplier.reload(e.getDs().getItem(), userPreferenceDs.getView());
                userPreferenceDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link userPreferencesTable}
         * The listener removes selection in {@link userPreferencesTable}, sets a newly created item to {@link userPreferenceDs}
         * and enables controls for record editing
         */
        userPreferencesTable.addAction(new CreateAction(userPreferencesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                userPreferencesTable.setSelected(Collections.emptyList());
                userPreferenceDs.setItem((UserPreference) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link userPreferencesTable}
         * The listener enables controls for record editing
         */
        userPreferencesTable.addAction(new EditAction(userPreferencesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (userPreferencesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link userPreferencesTableRemove}
         * to reset record, contained in {@link userPreferenceDs}
         */
        userPreferencesTableRemove.setAfterRemoveHandler(removedItems -> userPreferenceDs.setItem(null));

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
     * Method that is invoked by clicking Ok button after editing an existing or creating a new record
     */
    public void save() {
        if (!validate(Collections.singletonList(fieldGroup))) {
            return;
        }
        getDsContext().commit();

        UserPreference editedItem = userPreferenceDs.getItem();
        if (creating) {
            userPreferencesDs.includeItem(editedItem);
        } else {
            userPreferencesDs.updateItem(editedItem);
        }
        userPreferencesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        UserPreference selectedItem = userPreferencesDs.getItem();
        if (selectedItem != null) {
            UserPreference reloadedItem = dataSupplier.reload(selectedItem, userPreferenceDs.getView());
            userPreferencesDs.setItem(reloadedItem);
        } else {
            userPreferenceDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link UserPreference} is being created
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
        userPreferencesTable.requestFocus();
    }

    /**
     * Initiating edit controls, depending on if they should be enabled/disabled
     * @param enabled if true - enables editing controls and disables controls on the left side of the splitter
     *                if false - visa versa
     */
    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}