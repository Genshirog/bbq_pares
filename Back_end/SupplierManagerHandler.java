package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SupplierManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    private DatabaseHandler database;
    public SupplierManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.manager = manager;
        this.refreshable= refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "AddSup":
                Map<String,String> data = refreshable.getFormData();
                boolean success = database.addSupplier(
                        data.get("id"),
                        data.get("fname"),
                        data.get("lname"),
                        data.get("mi"),
                        data.get("person"),
                        data.get("mail"),
                        data.get("num"),
                        data.get("address")
                );
                if(success){
                    refreshable.clearForm();
                }
                break;
            case "SearchSup":
                manager.updateSupplierTable(database.searchSupplierView(manager.getInput(),refreshable.getValue()));
                break;
            case "EditSup":
                Map<String,String> editdata = refreshable.getFormData();
                boolean editsuccess = database.updateSupplier(
                        editdata.get("id"),
                        editdata.get("fname"),
                        editdata.get("lname"),
                        editdata.get("mi"),
                        editdata.get("person"),
                        editdata.get("mail"),
                        editdata.get("num"),
                        editdata.get("address")
                );
                if(editsuccess){
                    refreshable.clearForm();
                }
                break;
            case "RemSup":
                boolean deleteSuccess = database.deleteSupplier(manager.getInput(),refreshable.getValue());
                if (deleteSuccess){
                    manager.showProductTable();
                }
                break;
            case "ViewSup":
                refreshable.view_btn();
                manager.updateComboHolder(refreshable.getCombo());
                manager.showSupplierTable();
                break;
            case "FormSup":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
