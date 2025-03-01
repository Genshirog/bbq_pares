package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ProductInventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    private DatabaseHandler database;
    public ProductInventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.inventory = inventory;
        this.refreshable= refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "SearchProd":
                inventory.updateProductTable(database.searchProductView(inventory.getInput(),refreshable.getValue()));
                break;
            case "EditProd":
                Map<String,String> Editdata = refreshable.getFormData();
                boolean Editsuccess = database.updateInventory(
                        Editdata.get("id"),
                        Editdata.get("stock_quantity")
                );

                if(Editsuccess){
                    refreshable.clearForm();
                }
                break;
            case "RemProd":
                boolean deleteSuccess = database.deleteProducts(inventory.getInput(),refreshable.getValue());
                if (deleteSuccess){
                    inventory.showProductTable();
                }
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
                inventory.showProductTable();
                inventory.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
