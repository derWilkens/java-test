package local.paxbase.web.crafttype;

import local.paxbase.entity.CraftType;
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
public class CraftTypeBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<CraftType, UUID> craftTypesDs;

    @Inject
    private Datasource<CraftType> craftTypeDs;

    @Inject
    private Table<CraftType> craftTypesTable;

    @Inject
    private BoxLayout lookupBox;

    @Inject
    private BoxLayout actionsPane;

    @Inject
    private FieldGroup fieldGroup;
    
    @Named("craftTypesTable.remove")
    private RemoveAction craftTypesTableRemove;
    
    @Inject
    private DataSupplier dataSupplier;

    private boolean creating;

    @Override
    public void init(Map<String, Object> params) {
        craftTypesDs.addItemChangeListener(e -> {
            if (e.getItem() != null) {
                CraftType reloadedItem = dataSupplier.reload(e.getDs().getItem(), craftTypeDs.getView());
                craftTypeDs.setItem(reloadedItem);
            }
        });
        
        craftTypesTable.addAction(new CreateAction(craftTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity newItem, Datasource parentDs, Map<String, Object> params) {
                craftTypesTable.setSelected(Collections.emptyList());
                craftTypeDs.setItem((CraftType) newItem);
                enableEditControls(true);
            }
        });

        craftTypesTable.addAction(new EditAction(craftTypesTable) {
            @Override
            protected void internalOpenEditor(CollectionDatasource datasource, Entity existingItem, Datasource parentDs, Map<String, Object> params) {
                if (craftTypesTable.getSelected().size() == 1) {
                    enableEditControls(false);
                }
            }
        });
        
        craftTypesTableRemove.setAfterRemoveHandler(removedItems -> craftTypeDs.setItem(null));
        
        disableEditControls();
    }

    public void save() {
        getDsContext().commit();

        CraftType editedItem = craftTypeDs.getItem();
        if (creating) {
            craftTypesDs.includeItem(editedItem);
        } else {
            craftTypesDs.updateItem(editedItem);
        }
        craftTypesTable.setSelected(editedItem);

        disableEditControls();
    }

    public void cancel() {
        CraftType selectedItem = craftTypesDs.getItem();
        if (selectedItem != null) {
            CraftType reloadedItem = dataSupplier.reload(selectedItem, craftTypeDs.getView());
            craftTypesDs.setItem(reloadedItem);
        } else {
            craftTypeDs.setItem(null);
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
        craftTypesTable.requestFocus();
    }

    private void initEditComponents(boolean enabled) {
        fieldGroup.setEditable(enabled);
        actionsPane.setVisible(enabled);
        lookupBox.setEnabled(!enabled);
    }
}