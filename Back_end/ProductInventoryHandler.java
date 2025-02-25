package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

public class ProductInventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    public ProductInventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.btn = btn;
        this.inventory = inventory;
        this.refreshable= new ProductInventory(buttonContainer, inventory, logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "products":
                refreshable.form_btn();
                inventory.clearComboHolder();
                inventory.displayForm(refreshable.getForm());
                inventory.showBackButton();
                break;
            case "AddProd":
                System.out.println("Nigga");
                break;
            case "SearchProd":
                System.out.println("Nigga2");
                break;
            case "EditProd":
                System.out.println("Nigga3");
                break;
            case "RemProd":
                System.out.println("Nigga4");
                break;
            case "ViewProd":
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showProductTable();
                break;
            case "FormProd":
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
