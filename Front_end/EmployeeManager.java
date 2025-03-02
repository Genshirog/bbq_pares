package Front_end;

import Back_end.EmployeeManagerHandler;
import Back_end.Refreshable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class EmployeeManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    private TextField empID;
    private TextField empFname;
    private TextField empLname;
    private TextField empMI;
    private TextField empRole;
    private TextField empMail;
    private TextField empNum;
    private PasswordField empPass;
    private ComboBox<String> views;
    public EmployeeManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
        initFormFields();
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Employee");
        Button deletebtn = new Button("Remove Employee");
        Button viewbtn = new Button("View Employee");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new EmployeeManagerHandler("SearchEmp",buttonContainer,manager,logoutContainer,this));
        deletebtn.setOnAction(new EmployeeManagerHandler("RemEmp",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new EmployeeManagerHandler("ViewEmp",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new EmployeeManagerHandler("FormEmp",buttonContainer,manager,logoutContainer,this));


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

        createbtn.setOnAction(new EmployeeManagerHandler("AddEmp",buttonContainer,manager,logoutContainer,this));
        updatebtn.setOnAction(new EmployeeManagerHandler("EditEmp",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new EmployeeManagerHandler("ViewEmp",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new EmployeeManagerHandler("FormEmp",buttonContainer,manager,logoutContainer,this));


        Button[] employeebtns = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private void initFormFields(){
        this.empID = new TextField();
        this.empFname = new TextField();
        this.empLname = new TextField();
        this.empMI = new TextField();
        UnaryOperator<TextFormatter.Change> filter = change -> {
          if(change.getControlNewText().length() > 1){
              return null;
          }
          return change;
        };
        empMI.setTextFormatter(new TextFormatter<>(filter));
        this.empRole = new TextField();
        this.empMail = new TextField();
        this.empNum = new TextField();
        this.empPass = new PasswordField();

        // Add styles to all fields
        TextField[] texts = {empID, empFname, empLname, empMI, empRole, empMail, empNum};
        for (TextField text : texts) {
            text.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
        }
        empPass.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
    }
    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Employee ID");
        form.add(id,0,0);
        form.add(empID,1,0);

        Label fname = new Label("First Name");
        form.add(fname,2,0);
        form.add(empFname,3,0);

        Label lname = new Label("Last Name");
        form.add(lname,0,1);
        form.add(empLname,1,1);

        Label Minitial = new Label("Middle Initial");
        form.add(Minitial,2,1);
        form.add(empMI,3,1);

        Label role = new Label("Role");
        form.add(role,0,2);
        form.add(empRole,1,2);

        Label email = new Label("Email");
        form.add(email,2,2);
        form.add(empMail,3,2);

        Label phoneNum = new Label("Phone Number");
        form.add(phoneNum,0,3);
        form.add(empNum,1,3);

        Label pass = new Label("Password");
        form.add(pass,2,3);
        form.add(empPass,3,3);
        Label[] labels = {id,fname,lname,Minitial, role, email,phoneNum,pass};
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
        views = new ComboBox<>();
        views.getItems().addAll("Employee ID", "EmployeeName", "Role");
        views.setValue("Employee ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public String getValue(){
        return  views.getValue();
    }

    @Override
    public ComboBox<String> getCombo(){
        return employeeCombo();
    }

    @Override
    public Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("id", empID.getText());
        data.put("firstName", empFname.getText());
        data.put("lastName", empLname.getText());
        data.put("middleInitial", empMI.getText());
        data.put("role", empRole.getText());
        data.put("email", empMail.getText());
        data.put("phoneNumber", empNum.getText());
        data.put("password", empPass.getText());
        return data;
    }

    public void clearForm() {
        empID.clear();
        empFname.clear();
        empLname.clear();
        empMI.clear();
        empRole.clear();
        empMail.clear();
        empNum.clear();
        empPass.clear();
    }
}
