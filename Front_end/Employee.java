package Front_end;

import Back_end.EmployeeHandler;
import Back_end.Refreshable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Employee implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    public Employee(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Employee");
        Button deletebtn = new Button("Remove Employee");
        Button viewbtn = new Button("View Employee");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new EmployeeHandler("SearchEmp",buttonContainer,manager,logoutContainer));
        deletebtn.setOnAction(new EmployeeHandler("RemEmp",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new EmployeeHandler("ViewEmp",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new EmployeeHandler("FormEmp",buttonContainer,manager,logoutContainer));


        Button[] employeebtns = {searchbtn,deletebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,deletebtn,viewbtn,formbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Employee");
        Button updatebtn = new Button("Edit Employee");
        Button viewbtn = new Button("View Employee");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new EmployeeHandler("AddEmp",buttonContainer,manager,logoutContainer));
        updatebtn.setOnAction(new EmployeeHandler("EditEmp",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new EmployeeHandler("ViewEmp",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new EmployeeHandler("FormEmp",buttonContainer,manager,logoutContainer));


        Button[] employeebtns = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Employee ID");
        TextField empID = new TextField();
        form.add(id,0,0);
        form.add(empID,1,0);

        Label fname = new Label("First Name");
        TextField empFname = new TextField();
        form.add(fname,2,0);
        form.add(empFname,3,0);

        Label lname = new Label("Last Name");
        TextField empLname = new TextField();
        form.add(lname,0,1);
        form.add(empLname,1,1);

        Label Minitial = new Label("Middle Initial");
        TextField empMI = new TextField();
        form.add(Minitial,2,1);
        form.add(empMI,3,1);

        Label role = new Label("Role");
        TextField empRole = new TextField();
        form.add(role,0,2);
        form.add(empRole,1,2);

        Label email = new Label("Email");
        TextField empMail = new TextField();
        form.add(email,2,2);
        form.add(empMail,3,2);

        Label phoneNum = new Label("Phone Number");
        TextField empNum = new TextField();
        form.add(phoneNum,0,3);
        form.add(empNum,1,3);

        Label pass = new Label("Password");
        PasswordField empPass = new PasswordField();
        empPass.getStyleClass().addAll("textfield-1","border-radius","background-radius");
        form.add(pass,2,3);
        form.add(empPass,3,3);
        TextField[] texts = {empID,empFname,empMI,empRole,empLname,empNum,empMail};
        Label[] labels = {id,fname,lname,Minitial, role, email,phoneNum,pass};
        for(TextField text : texts){
            text.getStyleClass().addAll("textfield-1","border-radius","background-radius");
        }

        for(Label label : labels){
            label.getStyleClass().add("label-1");
        }
        return form;
    }

    @Override
    public GridPane getForm(){
        return formHolder();
    }

    private ComboBox<String> employeeCombo(){
        ComboBox<String> views = new ComboBox<>();
        views.getItems().addAll("Employee ID", "EmployeeName", "Role");
        views.setValue("Employee ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public ComboBox<String> getCombo(){
        return employeeCombo();
    }
}
