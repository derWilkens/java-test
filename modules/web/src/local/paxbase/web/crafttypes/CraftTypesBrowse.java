package local.paxbase.web.crafttypes;

import local.paxbase.entity.CraftTypes;
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
public class CraftTypesBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<CraftTypes, UUID> craftTypesesDs;

    @Inject
    private Datasource<CraftTypes> craftTypesDs;

    @Inject
    private Table<CraftTypes> craftTypesesTable;

    @Inject
    private BoxLayout lookupBox;

    @Inject
    private BoxLayout actionsPane;

    @Inject
    private FieldGroup fieldGroup;
    
    @Named("craftTypesesTable.remove")
    private RemoveAction craftTypesesTableRemove;
    
    @Inject
    private DataSupplier dataSupplier;

    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {
        craftTypesesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                CraftTypes reloadedItem = dataSupplier.reload(e.getDs().getItem(), craftTypesDs.getView());
                craftTypesDs.setItem(reloadedItem);
            }
        });
        
        craftTypesesTable.addAction(new CreateAction(craftTypesesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                craftTypesesTable.setSelected(Collections.emptyList());
                craftTypesDs.setItem((CraftTypes) newItem);
                enableEditControls(true);
            }
        });

        craftTypesesTable.addAction(new EditAction(craftTypesesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (craftTypesesTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });
        
        craftTypesesTableRemove.setAfterRemoveHandler(removedItems -> craftTypesDs.setItem(null));
        
        disableEditControls();
    }

    public void save() {
        getDsContext().commit();

        CraftTypes editedItem = craftTypesDs.getItem();
        if (creating) {
            craftTypesesDs.includeItem(editedItem);
        } else {
            craftTypesesDs.updateItem(editedItem);
        }
        craftTypesesTable.setSelected(editedItem);

        disableEditControls();
    }

    public void cancel() {
        CraftTypes selectedItem = craftTypesesDs.getItem();
        if (selectedItem != null) {
            CraftTypes reloadedItem = dataSupplier.reload(selectedItem, craftTypesDs.getView());
            craftTypesesDs.setItem(reloadedItem);
        } else {
            craftTypesDs.setItem(null);
        }

        disableEditControls();
    }

    private void enableEditControls(boolean creating) {
        this.creating = creating;
        initEditComponents(true);
        fieldGroup.requestFocus();
    }

    private void disableEditControls() {
        initEditComponents(false);
        craftTypesesTable.requestFocus();
    }

    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}