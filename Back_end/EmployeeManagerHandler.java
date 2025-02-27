package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class EmployeeManagerHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    private final DatabaseHandler database;
    public EmployeeManagerHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer, Refreshable refreshable){
        this.btn = btn;
        this.manager = manager;
        this.refreshable= refreshable;
        this.database = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "AddEmp":
                Map<String,String> data = refreshable.getFormData();
                boolean success = database.addEmployee(
                        data.get("id"),
                        data.get("firstName"),
                        data.get("lastName"),
                        data.get("middleInitial"),
                        data.get("role"),
                        data.get("email"),
                        data.get("phoneNumber"),
                        data.get("password")
                );

                if(success){
                    refreshable.clearForm();
                }
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
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
