package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

public class SupplierInventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    public SupplierInventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.btn = btn;
        this.inventory = inventory;
        this.refreshable= new SupplierInventory(buttonContainer, inventory, logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "supplier":
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showSupplierTable();
                inventory.showBackButton();
                break;
            case "SearchSup":
                System.out.println("Nigga2");
                break;
            case "ViewSup":
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showSupplierTable();
                break;
            case "FormSup":
                refreshable.form_btn();
                inventory.clearComboHolder();
                inventory.displayForm(refreshable.getForm());
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
