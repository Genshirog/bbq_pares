package Front_end;

import Back_end.EmployeeHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Employee {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    public Employee(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    public void loadEmpButtons(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Employee");
        Button searchbtn = new Button("Search Employee");
        Button updatebtn = new Button("Edit Employee");
        Button deletebtn = new Button("Remove Employee");
        Button viewbtn = new Button("View Employee");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new EmployeeHandler("AddEmp",buttonContainer,manager,logoutContainer));
        searchbtn.setOnAction(new EmployeeHandler("SearchEmp",buttonContainer,manager,logoutContainer));
        updatebtn.setOnAction(new EmployeeHandler("EditEmp",buttonContainer,manager,logoutContainer));
        deletebtn.setOnAction(new EmployeeHandler("RemEmp",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new EmployeeHandler("ViewEmp",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new EmployeeHandler("FormEmp",buttonContainer,manager,logoutContainer));


        Button[] employeebtns = {createbtn,searchbtn,updatebtn,deletebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,searchbtn,updatebtn,deletebtn,viewbtn,formbtn);
    }
}
