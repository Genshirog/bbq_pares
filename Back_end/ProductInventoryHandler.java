package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.Map;
//This is the product-inventory-handler this is the sub-backend of the system when a button is press it goes here to be process this is the request server
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
                    JOptionPane.showMessageDialog(null,"A Product was successfully stocked");
                    refreshable.clearForm();
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
