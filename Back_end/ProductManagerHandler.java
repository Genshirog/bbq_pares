package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ProductManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    private DatabaseHandler database;
    public ProductManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.manager = manager;
        this.refreshable= refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "AddProd":
                Map<String,String> data = refreshable.getFormData();
                boolean success = database.addProducts(
                    data.get("id"),
                    data.get("name"),
                    data.get("price"),
                    data.get("cost"),
                    data.get("supplier")
                );

                if(success){
                    refreshable.clearForm();
                }
                break;
            case "SearchProd":
                manager.updateProductTable(database.searchProductView(manager.getInput(),refreshable.getValue()));
                break;
            case "EditProd":
                Map<String,String> Editdata = refreshable.getFormData();
                boolean Editsuccess = database.updateProducts(
                        Editdata.get("id"),
                        Editdata.get("name"),
                        Editdata.get("price"),
                        Editdata.get("cost"),
                        Editdata.get("supplier")
                );

                if(Editsuccess){
                    refreshable.clearForm();
                }
                break;
            case "RemProd":
                boolean deleteSuccess = database.deleteProducts(manager.getInput(),refreshable.getValue());
                if (deleteSuccess){
                    manager.showProductTable();
                }
                break;
            case "ViewProd":
                refreshable.view_btn();
                manager.updateComboHolder(refreshable.getCombo());
                manager.showProductTable();
                break;
            case "FormProd":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                break;
            case "Back":
                manager.originalComboHolder();
                manager.buttonContainer();
                manager.showProductTable();
                manager.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
