package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.swing.*;
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
                    JOptionPane.showMessageDialog(null,"An Employee was successfully added");
                }
                break;
            case "SearchEmp":
                manager.updateEmployeeTable(database.searchEmployeeView(manager.getInput(),refreshable.getValue()));
                break;
            case "EditEmp":
                Map<String,String> Editdata = refreshable.getFormData();
                boolean Editsuccess = database.updateEmployee(
                        Editdata.get("id"),
                        Editdata.get("firstName"),
                        Editdata.get("lastName"),
                        Editdata.get("middleInitial"),
                        Editdata.get("role"),
                        Editdata.get("email"),
                        Editdata.get("phoneNumber"),
                        Editdata.get("password")
                );
                if(Editsuccess){
                    refreshable.clearForm();
                    JOptionPane.showMessageDialog(null,"An Employee was successfully updated");
                }
                break;
            case "RemEmp":
                boolean deleteSuccess = database.deleteEmployee(manager.getInput(),refreshable.getValue());
                if (deleteSuccess){
                    manager.showEmployeeTable();
                    JOptionPane.showMessageDialog(null,"An Employee was successfully removed");
                }
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
