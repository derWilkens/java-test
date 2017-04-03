package local.paxbase.web.periodtype;

import local.paxbase.entity.coredata.PeriodType;
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

public class PeriodTypeBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link PeriodType} records
     * to be displayed in {@link PeriodTypeBrowse#periodTypesTable} on the left
     */
    @Inject
    private CollectionDatasource<PeriodType, UUID> periodTypesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link PeriodTypeBrowse#periodTypesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link PeriodTypeBrowse#init(Map)} method
     */
    @Inject
    private Datasource<PeriodType> periodTypeDs;

    /**
     * The {@link Table} instance, containing a list of {@link PeriodType} records,
     * loaded via {@link PeriodTypeBrowse#periodTypesDs}
     */
    @Inject
    private Table<PeriodType> periodTypesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link PeriodTypeBrowse#periodTypeDs}
     * and shows fields of the selected {@link PeriodType} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link PeriodTypeBrowse#periodTypesTable}
     */
    @Named("periodTypesTable.remove")
    private RemoveAction periodTypesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link PeriodType} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link periodTypesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link periodTypeDs}
         */
        periodTypesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                PeriodType reloadedItem = dataSupplier.reload(e.getDs().getItem(), periodTypeDs.getView());
                periodTypeDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link periodTypesTable}
         * The listener removes selection in {@link periodTypesTable}, sets a newly created item to {@link periodTypeDs}
         * and enables controls for record editing
         */
        periodTypesTable.addAction(new CreateAction(periodTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                periodTypesTable.setSelected(Collections.emptyList());
                periodTypeDs.setItem((PeriodType) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link periodTypesTable}
         * The listener enables controls for record editing
         */
        periodTypesTable.addAction(new EditAction(periodTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (periodTypesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link periodTypesTableRemove}
         * to reset record, contained in {@link periodTypeDs}
         */
        periodTypesTableRemove.setAfterRemoveHandler(removedItems -> periodTypeDs.setItem(null));

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

        PeriodType editedItem = periodTypeDs.getItem();
        if (creating) {
            periodTypesDs.includeItem(editedItem);
        } else {
            periodTypesDs.updateItem(editedItem);
        }
        periodTypesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        PeriodType selectedItem = periodTypesDs.getItem();
        if (selectedItem != null) {
            PeriodType reloadedItem = dataSupplier.reload(selectedItem, periodTypeDs.getView());
            periodTypesDs.setItem(reloadedItem);
        } else {
            periodTypeDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link PeriodType} is being created
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
        periodTypesTable.requestFocus();
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