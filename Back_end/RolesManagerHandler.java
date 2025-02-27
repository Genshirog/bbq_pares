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
                System.out.println("Nigga2");
                break;
            case "EditRoles":
                System.out.println("Nigga3");
                break;
            case "RemRoles":
                System.out.println("Nigga4");
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
