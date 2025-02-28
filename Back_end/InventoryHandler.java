package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.management.relation.Role;
import java.util.Map;

public class InventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    private final VBox buttonContainer;
    private final VBox logoutContainer;
    public InventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.btn = btn;
        this.inventory = inventory;
        this.buttonContainer = buttonContainer;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "report":
                this.refreshable = new ReportInventory(buttonContainer, inventory, logoutContainer);
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showReportTable();
                inventory.showBackButton();
                break;
            case "products":
                this.refreshable= new ProductInventory(buttonContainer, inventory, logoutContainer);
                refreshable.form_btn();
                inventory.clearComboHolder();
                inventory.displayForm(refreshable.getForm());
                inventory.showBackButton();
                break;
            case "supplier":
                this.refreshable= new SupplierInventory(buttonContainer, inventory, logoutContainer);
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showSupplierTable();
                inventory.showBackButton();
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
