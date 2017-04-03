package local.paxbase.web.serviceperiod;

import local.paxbase.entity.ServicePeriod;
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

public class ServicePeriodBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link ServicePeriod} records
     * to be displayed in {@link ServicePeriodBrowse#servicePeriodsTable} on the left
     */
    @Inject
    private CollectionDatasource<ServicePeriod, UUID> servicePeriodsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link ServicePeriodBrowse#servicePeriodsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link ServicePeriodBrowse#init(Map)} method
     */
    @Inject
    private Datasource<ServicePeriod> servicePeriodDs;

    /**
     * The {@link Table} instance, containing a list of {@link ServicePeriod} records,
     * loaded via {@link ServicePeriodBrowse#servicePeriodsDs}
     */
    @Inject
    private Table<ServicePeriod> servicePeriodsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link ServicePeriodBrowse#servicePeriodDs}
     * and shows fields of the selected {@link ServicePeriod} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link ServicePeriodBrowse#servicePeriodsTable}
     */
    @Named("servicePeriodsTable.remove")
    private RemoveAction servicePeriodsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link ServicePeriod} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link servicePeriodsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link servicePeriodDs}
         */
        servicePeriodsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                ServicePeriod reloadedItem = dataSupplier.reload(e.getDs().getItem(), servicePeriodDs.getView());
                servicePeriodDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link servicePeriodsTable}
         * The listener removes selection in {@link servicePeriodsTable}, sets a newly created item to {@link servicePeriodDs}
         * and enables controls for record editing
         */
        servicePeriodsTable.addAction(new CreateAction(servicePeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                servicePeriodsTable.setSelected(Collections.emptyList());
                servicePeriodDs.setItem((ServicePeriod) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link servicePeriodsTable}
         * The listener enables controls for record editing
         */
        servicePeriodsTable.addAction(new EditAction(servicePeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (servicePeriodsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link servicePeriodsTableRemove}
         * to reset record, contained in {@link servicePeriodDs}
         */
        servicePeriodsTableRemove.setAfterRemoveHandler(removedItems -> servicePeriodDs.setItem(null));

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

        ServicePeriod editedItem = servicePeriodDs.getItem();
        if (creating) {
            servicePeriodsDs.includeItem(editedItem);
        } else {
            servicePeriodsDs.updateItem(editedItem);
        }
        servicePeriodsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        ServicePeriod selectedItem = servicePeriodsDs.getItem();
        if (selectedItem != null) {
            ServicePeriod reloadedItem = dataSupplier.reload(selectedItem, servicePeriodDs.getView());
            servicePeriodsDs.setItem(reloadedItem);
        } else {
            servicePeriodDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link ServicePeriod} is being created
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
        servicePeriodsTable.requestFocus();
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