package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RolesHandler implements EventHandler<ActionEvent> {
    private final String btn;;
    private final Manager manager;
    private final Refreshable refreshable;
    public RolesHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.btn = btn;
        this.manager = manager;
        this.refreshable = new Roles(buttonContainer,manager,logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "roles":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "AddRoles":
                System.out.println("Nigga");
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
            case "Back":
                manager.originalComboHolder();
                manager.buttonContainer();
                manager.showEmployeeTable();
                manager.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
