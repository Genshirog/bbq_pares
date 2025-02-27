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
