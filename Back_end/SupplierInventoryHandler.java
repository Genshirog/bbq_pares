package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

public class SupplierInventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    private DatabaseHandler database;
    public SupplierInventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.inventory = inventory;
        this.refreshable= refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "SearchSup":
                inventory.updateSupplierTable(database.searchSupplierView(inventory.getInput(),refreshable.getValue()));
                break;
            case "ViewSup":
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showSupplierTable();
                break;
            case "Back":
                inventory.originalComboHolder();
                inventory.buttonContainer();
                inventory.showInventoryTable();
                inventory.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
