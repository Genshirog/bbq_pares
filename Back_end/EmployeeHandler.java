package Back_end;

import Front_end.Employee;
import Front_end.Manager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class EmployeeHandler implements EventHandler<ActionEvent> {
    private final String btn;;
    private VBox buttonContainer;
    private Manager manager;
    private VBox logoutContainer;
    public EmployeeHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.btn = btn;
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "employee":
                Employee employee = new Employee(buttonContainer, manager, logoutContainer);
                employee.loadEmpButtons();
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
                System.out.println("Nigga5");
                break;
            case "FormEmp":
                System.out.println("Nigga6");
                break;
            case "roles":
                break;
            case "products":
                break;
            case "suppliers":
                break;
            case "Back":
                manager.buttonContainer();
                manager.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
