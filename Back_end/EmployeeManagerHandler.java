package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

public class EmployeeManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    public EmployeeManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.btn = btn;
        this.manager = manager;
        this.refreshable= new EmployeeManager(buttonContainer, manager, logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "employee":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "AddEmp":
                System.out.println("Nigga");
                break;
            case "SearchEmp":
                System.out.println("Nigga2");
                break;
            case "EditEmp":
                System.out.println("Nigga3");
                break;
            case "RemEmp":
                System.out.println("Nigga4");
                break;
            case "ViewEmp":
                refreshable.view_btn();
                manager.updateComboHolder(refreshable.getCombo());
                manager.showEmployeeTable();
                break;
            case "FormEmp":
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
