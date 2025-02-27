package Front_end;

import Back_end.Refreshable;
import Back_end.RolesManagerHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class RolesManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    private TextField roleID;
    private TextField roleName;
    private TextField descriptionText;
    private TextField shiftText;
    public RolesManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
        initFormFields();
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Roles");
        Button deletebtn = new Button("Remove Roles");
        Button viewbtn = new Button("View Roles");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new RolesManagerHandler("SearchRoles",buttonContainer,manager,logoutContainer,this));
        deletebtn.setOnAction(new RolesManagerHandler("RemRoles",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new RolesManagerHandler("ViewRoles",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new RolesManagerHandler("FormRoles",buttonContainer,manager,logoutContainer,this));


        Button[] rolesbtn = {searchbtn,deletebtn,viewbtn,formbtn};
        for(Button button:rolesbtn){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,deletebtn,viewbtn,formbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Roles");
        Button updatebtn = new Button("Edit Roles");
        Button viewbtn = new Button("View Roles");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new RolesManagerHandler("AddRoles",buttonContainer,manager,logoutContainer,this));
        updatebtn.setOnAction(new RolesManagerHandler("EditRoles",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new RolesManagerHandler("ViewRoles",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new RolesManagerHandler("FormRoles",buttonContainer,manager,logoutContainer,this));


        Button[] rolesbtn = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:rolesbtn){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private void initFormFields(){
        this.roleID = new TextField();
        this.roleName = new TextField();
        this.descriptionText = new TextField();
        this.shiftText = new TextField();

        // Add styles to all fields
        TextField[] texts = {roleID,roleName,descriptionText,shiftText};
        for (TextField text : texts) {
            text.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
        }
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Role ID");
        form.add(id,0,0);
        form.add(roleID,1,0);

        Label role = new Label("Role Name");
        form.add(role,2,0);
        form.add(roleName,3,0);

        Label description = new Label("Description");
        form.add(description,0,1);
        form.add(descriptionText,1,1);

        Label shift = new Label("Shift(Day/Night/Full-Time)");
        form.add(shift,2,1);
        form.add(shiftText,3,1);

        TextField[] texts = {roleID,roleName,shiftText,descriptionText};
        Label[] labels = {id,role,description,shift};
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

    private ComboBox<String> rolesCombo(){
        ComboBox<String> views = new ComboBox<>();
        views.getItems().addAll("Role ID", "Role", "Shift");
        views.setValue("Role ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public ComboBox<String> getCombo(){
        return rolesCombo();
    }

    @Override
    public Map<String,String> getFormData(){
        Map<String, String> data = new HashMap<>();
        data.put("id", roleID.getText());
        data.put("role", roleName.getText());
        data.put("description", descriptionText.getText());
        data.put("shift", shiftText.getText());
        return data;
    }

    @Override
    public void clearForm() {
        roleID.clear();
        roleName.clear();
        descriptionText.clear();
        shiftText.clear();
    }
}
