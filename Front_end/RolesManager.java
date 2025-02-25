package Front_end;

import Back_end.Refreshable;
import Back_end.RolesManagerHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RolesManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    public RolesManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Roles");
        Button deletebtn = new Button("Remove Roles");
        Button viewbtn = new Button("View Roles");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new RolesManagerHandler("SearchRoles",buttonContainer,manager,logoutContainer));
        deletebtn.setOnAction(new RolesManagerHandler("RemRoles",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new RolesManagerHandler("ViewRoles",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new RolesManagerHandler("FormRoles",buttonContainer,manager,logoutContainer));


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

        createbtn.setOnAction(new RolesManagerHandler("AddRoles",buttonContainer,manager,logoutContainer));
        updatebtn.setOnAction(new RolesManagerHandler("EditRoles",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new RolesManagerHandler("ViewRoles",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new RolesManagerHandler("FormRoles",buttonContainer,manager,logoutContainer));


        Button[] rolesbtn = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:rolesbtn){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Role ID");
        TextField empID = new TextField();
        form.add(id,0,0);
        form.add(empID,1,0);

        Label role = new Label("Role Name");
        TextField roleName = new TextField();
        form.add(role,2,0);
        form.add(roleName,3,0);

        Label description = new Label("Description");
        TextField descriptionText = new TextField();
        form.add(description,0,1);
        form.add(descriptionText,1,1);

        Label shift = new Label("Shift(Day/Night/Full-Time)");
        TextField shiftText = new TextField();
        form.add(shift,2,1);
        form.add(shiftText,3,1);

        TextField[] texts = {empID,roleName,shiftText,descriptionText};
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
}
