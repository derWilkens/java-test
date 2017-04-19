package local.paxbase.web.contractordutyperiod;

import local.paxbase.entity.ContractorDutyPeriod;
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

public class ContractorDutyPeriodBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link ContractorDutyPeriod} records
     * to be displayed in {@link ContractorDutyPeriodBrowse#contractorDutyPeriodsTable} on the left
     */
    @Inject
    private CollectionDatasource<ContractorDutyPeriod, UUID> contractorDutyPeriodsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link ContractorDutyPeriodBrowse#contractorDutyPeriodsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link ContractorDutyPeriodBrowse#init(Map)} method
     */
    @Inject
    private Datasource<ContractorDutyPeriod> contractorDutyPeriodDs;

    /**
     * The {@link Table} instance, containing a list of {@link ContractorDutyPeriod} records,
     * loaded via {@link ContractorDutyPeriodBrowse#contractorDutyPeriodsDs}
     */
    @Inject
    private Table<ContractorDutyPeriod> contractorDutyPeriodsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link ContractorDutyPeriodBrowse#contractorDutyPeriodDs}
     * and shows fields of the selected {@link ContractorDutyPeriod} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link ContractorDutyPeriodBrowse#contractorDutyPeriodsTable}
     */
    @Named("contractorDutyPeriodsTable.remove")
    private RemoveAction contractorDutyPeriodsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link ContractorDutyPeriod} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link contractorDutyPeriodsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link contractorDutyPeriodDs}
         */
        contractorDutyPeriodsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                ContractorDutyPeriod reloadedItem = dataSupplier.reload(e.getDs().getItem(), contractorDutyPeriodDs.getView());
                contractorDutyPeriodDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link contractorDutyPeriodsTable}
         * The listener removes selection in {@link contractorDutyPeriodsTable}, sets a newly created item to {@link contractorDutyPeriodDs}
         * and enables controls for record editing
         */
        contractorDutyPeriodsTable.addAction(new CreateAction(contractorDutyPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                contractorDutyPeriodsTable.setSelected(Collections.emptyList());
                contractorDutyPeriodDs.setItem((ContractorDutyPeriod) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link contractorDutyPeriodsTable}
         * The listener enables controls for record editing
         */
        contractorDutyPeriodsTable.addAction(new EditAction(contractorDutyPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (contractorDutyPeriodsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link contractorDutyPeriodsTableRemove}
         * to reset record, contained in {@link contractorDutyPeriodDs}
         */
        contractorDutyPeriodsTableRemove.setAfterRemoveHandler(removedItems -> contractorDutyPeriodDs.setItem(null));

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

        ContractorDutyPeriod editedItem = contractorDutyPeriodDs.getItem();
        if (creating) {
            contractorDutyPeriodsDs.includeItem(editedItem);
        } else {
            contractorDutyPeriodsDs.updateItem(editedItem);
        }
        contractorDutyPeriodsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        ContractorDutyPeriod selectedItem = contractorDutyPeriodsDs.getItem();
        if (selectedItem != null) {
            ContractorDutyPeriod reloadedItem = dataSupplier.reload(selectedItem, contractorDutyPeriodDs.getView());
            contractorDutyPeriodsDs.setItem(reloadedItem);
        } else {
            contractorDutyPeriodDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link ContractorDutyPeriod} is being created
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
        contractorDutyPeriodsTable.requestFocus();
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