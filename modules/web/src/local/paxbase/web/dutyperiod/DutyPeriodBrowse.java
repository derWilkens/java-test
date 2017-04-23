package local.paxbase.web.dutyperiod;

import local.paxbase.entity.DutyPeriod;
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

public class DutyPeriodBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link DutyPeriod} records
     * to be displayed in {@link DutyPeriodBrowse#dutyPeriodsTable} on the left
     */
    @Inject
    private CollectionDatasource<DutyPeriod, UUID> dutyPeriodsDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link DutyPeriodBrowse#dutyPeriodsDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link DutyPeriodBrowse#init(Map)} method
     */
    @Inject
    private Datasource<DutyPeriod> dutyPeriodDs;

    /**
     * The {@link Table} instance, containing a list of {@link DutyPeriod} records,
     * loaded via {@link DutyPeriodBrowse#dutyPeriodsDs}
     */
    @Inject
    private Table<DutyPeriod> dutyPeriodsTable;

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
     * The {@link FieldGroup} instance that is linked to {@link DutyPeriodBrowse#dutyPeriodDs}
     * and shows fields of the selected {@link DutyPeriod} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link DutyPeriodBrowse#dutyPeriodsTable}
     */
    @Named("dutyPeriodsTable.remove")
    private RemoveAction dutyPeriodsTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link DutyPeriod} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link dutyPeriodsDs}
         * The listener reloads the selected record with the specified view and sets it to {@link dutyPeriodDs}
         */
        dutyPeriodsDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                DutyPeriod reloadedItem = dataSupplier.reload(e.getDs().getItem(), dutyPeriodDs.getView());
                dutyPeriodDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link dutyPeriodsTable}
         * The listener removes selection in {@link dutyPeriodsTable}, sets a newly created item to {@link dutyPeriodDs}
         * and enables controls for record editing
         */
        dutyPeriodsTable.addAction(new CreateAction(dutyPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                dutyPeriodsTable.setSelected(Collections.emptyList());
                dutyPeriodDs.setItem((DutyPeriod) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link dutyPeriodsTable}
         * The listener enables controls for record editing
         */
        dutyPeriodsTable.addAction(new EditAction(dutyPeriodsTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (dutyPeriodsTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link dutyPeriodsTableRemove}
         * to reset record, contained in {@link dutyPeriodDs}
         */
        dutyPeriodsTableRemove.setAfterRemoveHandler(removedItems -> dutyPeriodDs.setItem(null));

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

        DutyPeriod editedItem = dutyPeriodDs.getItem();
        if (creating) {
            dutyPeriodsDs.includeItem(editedItem);
        } else {
            dutyPeriodsDs.updateItem(editedItem);
        }
        dutyPeriodsTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        DutyPeriod selectedItem = dutyPeriodsDs.getItem();
        if (selectedItem != null) {
            DutyPeriod reloadedItem = dataSupplier.reload(selectedItem, dutyPeriodDs.getView());
            dutyPeriodsDs.setItem(reloadedItem);
        } else {
            dutyPeriodDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link DutyPeriod} is being created
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
        dutyPeriodsTable.requestFocus();
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