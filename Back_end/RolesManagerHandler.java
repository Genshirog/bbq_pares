package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class RolesManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;;
    private final Manager manager;
    private final Refreshable refreshable;
    private final DatabaseHandler database;
    public RolesManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.manager = manager;
        this.refreshable = refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "AddRoles":
                Map<String,String> data = refreshable.getFormData();
                boolean success = database.addRoles(
                        data.get("id"),
                        data.get("role"),
                        data.get("description"),
                        data.get("shift")
                );

                if(success){
                    refreshable.clearForm();
                }
                break;
            case "SearchRoles":
                manager.updateRoleTable(database.searchRolesView(manager.getInput(),refreshable.getValue()));
                break;
            case "EditRoles":
                Map<String,String> Editdata = refreshable.getFormData();
                boolean Editsuccess = database.editRoles(
                        Editdata.get("id"),
                        Editdata.get("role"),
                        Editdata.get("description"),
                        Editdata.get("shift")
                );

                if(Editsuccess){
                    refreshable.clearForm();
                }
                break;
            case "RemRoles":
                boolean deleteSuccess = database.deleteRoles(manager.getInput(),refreshable.getValue());
                if (deleteSuccess){
                    manager.showRolesTable();
                }
                break;
            case "ViewRoles":
                refreshable.view_btn();
                manager.updateComboHolder(refreshable.getCombo());
                manager.showRolesTable();
                break;
            case "FormRoles":
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
