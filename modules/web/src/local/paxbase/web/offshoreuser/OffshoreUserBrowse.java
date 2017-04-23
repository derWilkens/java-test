package local.paxbase.web.offshoreuser;

import local.paxbase.entity.OffshoreUser;
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

public class OffshoreUserBrowse extends AbstractLookup {

    /**
     * The {@link CollectionDatasource} instance that loads a list of {@link OffshoreUser} records
     * to be displayed in {@link OffshoreUserBrowse#offshoreUsersTable} on the left
     */
    @Inject
    private CollectionDatasource<OffshoreUser, UUID> offshoreUsersDs;

    /**
     * The {@link Datasource} instance that contains an instance of the selected entity
     * in {@link OffshoreUserBrowse#offshoreUsersDs}
     * <p/> Containing instance is loaded in {@link CollectionDatasource#addItemChangeListener}
     * with the view, specified in the XML screen descriptor.
     * The listener is set in the {@link OffshoreUserBrowse#init(Map)} method
     */
    @Inject
    private Datasource<OffshoreUser> offshoreUserDs;

    /**
     * The {@link Table} instance, containing a list of {@link OffshoreUser} records,
     * loaded via {@link OffshoreUserBrowse#offshoreUsersDs}
     */
    @Inject
    private Table<OffshoreUser> offshoreUsersTable;

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
     * The {@link FieldGroup} instance that is linked to {@link OffshoreUserBrowse#offshoreUserDs}
     * and shows fields of the selected {@link OffshoreUser} record
     */
    @Inject
    private FieldGroup fieldGroup;

    /**
     * The {@link RemoveAction} instance, related to {@link OffshoreUserBrowse#offshoreUsersTable}
     */
    @Named("offshoreUsersTable.remove")
    private RemoveAction offshoreUsersTableRemove;

    @Inject
    private DataSupplier dataSupplier;

    /**
     * {@link Boolean} value, indicating if a new instance of {@link OffshoreUser} is being created
     */
    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {

        /*
         * Adding {@link com.haulmont.cuba.gui.data.Datasource.ItemChangeListener} to {@link offshoreUsersDs}
         * The listener reloads the selected record with the specified view and sets it to {@link offshoreUserDs}
         */
        offshoreUsersDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                OffshoreUser reloadedItem = dataSupplier.reload(e.getDs().getItem(), offshoreUserDs.getView());
                offshoreUserDs.setItem(reloadedItem);
            }
        });

        /*
         * Adding {@link CreateAction} to {@link offshoreUsersTable}
         * The listener removes selection in {@link offshoreUsersTable}, sets a newly created item to {@link offshoreUserDs}
         * and enables controls for record editing
         */
        offshoreUsersTable.addAction(new CreateAction(offshoreUsersTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                offshoreUsersTable.setSelected(Collections.emptyList());
                offshoreUserDs.setItem((OffshoreUser) newItem);
                refreshOptionsForLookupFields();
                enableEditControls(true);
            }
        });

        /*
         * Adding {@link EditAction} to {@link offshoreUsersTable}
         * The listener enables controls for record editing
         */
        offshoreUsersTable.addAction(new EditAction(offshoreUsersTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (offshoreUsersTable.getSelected().size() == 1) {
                    refreshOptionsForLookupFields();
                    enableEditControls(false);
                }
            }
        });

        /*
         * Setting {@link RemoveAction#afterRemoveHandler} for {@link offshoreUsersTableRemove}
         * to reset record, contained in {@link offshoreUserDs}
         */
        offshoreUsersTableRemove.setAfterRemoveHandler(removedItems -> offshoreUserDs.setItem(null));

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

        OffshoreUser editedItem = offshoreUserDs.getItem();
        if (creating) {
            offshoreUsersDs.includeItem(editedItem);
        } else {
            offshoreUsersDs.updateItem(editedItem);
        }
        offshoreUsersTable.setSelected(editedItem);

        disableEditControls();
    }

    /**
     * Method that is invoked by clicking Cancel button, discards changes and disables controls for record editing
     */
    public void cancel() {
        OffshoreUser selectedItem = offshoreUsersDs.getItem();
        if (selectedItem != null) {
            OffshoreUser reloadedItem = dataSupplier.reload(selectedItem, offshoreUserDs.getView());
            offshoreUsersDs.setItem(reloadedItem);
        } else {
            offshoreUserDs.setItem(null);
        }

        disableEditControls();
    }

    /**
     * Enabling controls for record editing
     * @param creating indicates if a new instance of {@link OffshoreUser} is being created
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
        offshoreUsersTable.requestFocus();
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