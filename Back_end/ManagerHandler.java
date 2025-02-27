package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.management.relation.Role;
import java.util.Map;

public class ManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    private final VBox buttonContainer;
    private final VBox logoutContainer;
    public ManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.btn = btn;
        this.manager = manager;
        this.buttonContainer = buttonContainer;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "employee":
                this.refreshable= new EmployeeManager(buttonContainer, manager, logoutContainer);
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "roles":
                this.refreshable= new RolesManager(buttonContainer, manager, logoutContainer);
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "products":
                this.refreshable= new ProductManager(buttonContainer, manager, logoutContainer);
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "supplier":
                this.refreshable= new SupplierManager(buttonContainer, manager, logoutContainer);
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
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
