package local.paxbase.web.administrationperiod;

import local.paxbase.entity.AdministrationPeriod;
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

public class AdministrationPeriodBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link AdministrationPeriod} records
     * to be displayed in {@link AdministrationPeriodBrowse#administrationPeriodsTable} on the left
     */
    @Inject
    private CollectionDatasource<AdministrationPeriod, UUID> administrationPeriodsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link AdministrationPeriodBrowse#administrationPeriodsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link AdministrationPeriodBrowse#init(Map)} method
     */
    @Inject
    private Datasource<AdministrationPeriod> administrationPeriodDs;

    /**
     * The {@link Table} instance, containing a list of {@link AdministrationPeriod} records,
     * loaded via {@link AdministrationPeriodBrowse#administrationPeriodsDs}
     */
    @Inject
    private Table<AdministrationPeriod> administrationPeriodsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link AdministrationPeriodBrowse#administrationPeriodDs}
     * and shows fields of the selected {@link AdministrationPeriod} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link AdministrationPeriodBrowse#administrationPeriodsTable}
     */
    @Named("administrationPeriodsTable.remove")
    private RemoveAction administrationPeriodsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link AdministrationPeriod} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link administrationPeriodsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link administrationPeriodDs}
         */
        administrationPeriodsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                AdministrationPeriod reloadedItem = dataSupplier.reload(e.getDs().getItem(), administrationPeriodDs.getView());
                administrationPeriodDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link administrationPeriodsTable}
         * The listener removes selection in {@link administrationPeriodsTable}, sets a newly created item to {@link administrationPeriodDs}
         * and enables controls for record editing
         */
        administrationPeriodsTable.addAction(new CreateAction(administrationPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                administrationPeriodsTable.setSelected(Collections.emptyList());
                administrationPeriodDs.setItem((AdministrationPeriod) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link administrationPeriodsTable}
         * The listener enables controls for record editing
         */
        administrationPeriodsTable.addAction(new EditAction(administrationPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (administrationPeriodsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link administrationPeriodsTableRemove}
         * to reset record, contained in {@link administrationPeriodDs}
         */
        administrationPeriodsTableRemove.setAfterRemoveHandler(removedItems -> administrationPeriodDs.setItem(null));

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

        AdministrationPeriod editedItem = administrationPeriodDs.getItem();
        if (creating) {
            administrationPeriodsDs.includeItem(editedItem);
        } else {
            administrationPeriodsDs.updateItem(editedItem);
        }
        administrationPeriodsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        AdministrationPeriod selectedItem = administrationPeriodsDs.getItem();
        if (selectedItem != null) {
            AdministrationPeriod reloadedItem = dataSupplier.reload(selectedItem, administrationPeriodDs.getView());
            administrationPeriodsDs.setItem(reloadedItem);
        } else {
            administrationPeriodDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link AdministrationPeriod} is being created
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
        administrationPeriodsTable.requestFocus();
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