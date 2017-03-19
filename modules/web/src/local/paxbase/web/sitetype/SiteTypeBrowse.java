package local.paxbase.web.sitetype;

import local.paxbase.entity.coredata.SiteType;
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

public class SiteTypeBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link SiteType} records
     * to be displayed in {@link SiteTypeBrowse#siteTypesTable} on the left
     */
    @Inject
    private CollectionDatasource<SiteType, UUID> siteTypesDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link SiteTypeBrowse#siteTypesDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link SiteTypeBrowse#init(Map)} method
     */
    @Inject
    private Datasource<SiteType> siteTypeDs;

    /**
     * The {@link Table} instance, containing a list of {@link SiteType} records,
     * loaded via {@link SiteTypeBrowse#siteTypesDs}
     */
    @Inject
    private Table<SiteType> siteTypesTable;

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
     * The {@link FieldGroup} instance that is linked to {@link SiteTypeBrowse#siteTypeDs}
     * and shows fields of the selected {@link SiteType} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link SiteTypeBrowse#siteTypesTable}
     */
    @Named("siteTypesTable.remove")
    private RemoveAction siteTypesTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link SiteType} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link siteTypesDs}
         * The listener reloads the selected record with the specified view and sets it to {@link siteTypeDs}
         */
        siteTypesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                SiteType reloadedItem = dataSupplier.reload(e.getDs().getItem(), siteTypeDs.getView());
                siteTypeDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link siteTypesTable}
         * The listener removes selection in {@link siteTypesTable}, sets a newly created item to {@link siteTypeDs}
         * and enables controls for record editing
         */
        siteTypesTable.addAction(new CreateAction(siteTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                siteTypesTable.setSelected(Collections.emptyList());
                siteTypeDs.setItem((SiteType) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link siteTypesTable}
         * The listener enables controls for record editing
         */
        siteTypesTable.addAction(new EditAction(siteTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (siteTypesTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link siteTypesTableRemove}
         * to reset record, contained in {@link siteTypeDs}
         */
        siteTypesTableRemove.setAfterRemoveHandler(removedItems -> siteTypeDs.setItem(null));

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

        SiteType editedItem = siteTypeDs.getItem();
        if (creating) {
            siteTypesDs.includeItem(editedItem);
        } else {
            siteTypesDs.updateItem(editedItem);
        }
        siteTypesTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        SiteType selectedItem = siteTypesDs.getItem();
        if (selectedItem != null) {
            SiteType reloadedItem = dataSupplier.reload(selectedItem, siteTypeDs.getView());
            siteTypesDs.setItem(reloadedItem);
        } else {
            siteTypeDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link SiteType} is being created
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
        siteTypesTable.requestFocus();
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