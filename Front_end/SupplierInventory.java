package Front_end;

import Back_end.Refreshable;
import Back_end.SupplierInventoryHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SupplierInventory implements Refreshable {
    private final VBox buttonContainer;
    private final Inventory inventory;
    private final VBox logoutContainer;
    private ComboBox<String> views;
    public SupplierInventory(VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.inventory = inventory;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Supplier");
        Button viewbtn = new Button("View Supplier");

        searchbtn.setOnAction(new SupplierInventoryHandler("SearchSup",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new SupplierInventoryHandler("ViewSup",buttonContainer,inventory,logoutContainer));


        Button[] supplierbtns = {searchbtn,viewbtn};
        for(Button button:supplierbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,viewbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Supplier");
        Button updatebtn = new Button("Edit Supplier");
        Button viewbtn = new Button("View Supplier");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new SupplierInventoryHandler("AddSup",buttonContainer,inventory,logoutContainer));
        updatebtn.setOnAction(new SupplierInventoryHandler("EditSup",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new SupplierInventoryHandler("ViewSup",buttonContainer,inventory,logoutContainer));
        formbtn.setOnAction(new SupplierInventoryHandler("FormSup",buttonContainer,inventory,logoutContainer));


        Button[] supplierbtns = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:supplierbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Supplier ID");
        TextField supID = new TextField();
        form.add(id,0,0);
        form.add(supID,1,0);

        Label fname = new Label("First Name");
        TextField supFname = new TextField();
        form.add(fname,2,0);
        form.add(supFname,3,0);

        Label lname = new Label("Last Name");
        TextField supLname = new TextField();
        form.add(lname,0,1);
        form.add(supLname,1,1);

        Label Minitial = new Label("Middle Initial");
        TextField supMI = new TextField();
        form.add(Minitial,2,1);
        form.add(supMI,3,1);

        Label contactperson = new Label("Contact Person");
        TextField supPerson = new TextField();
        form.add(contactperson,0,2);
        form.add(supPerson,1,2);

        Label email = new Label("Email");
        TextField supMail = new TextField();
        form.add(email,2,2);
        form.add(supMail,3,2);

        Label phoneNum = new Label("Phone Number");
        TextField supNum = new TextField();
        form.add(phoneNum,0,3);
        form.add(supNum,1,3);

        TextField[] texts = {supID,supFname,supMI,supPerson,supLname,supNum,supMail};
        Label[] labels = {id,fname,lname,Minitial, contactperson, email,phoneNum};
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

    private ComboBox<String> supplierCombo(){
        views = new ComboBox<>();
        views.getItems().addAll("Supplier ID", "Supplier Name", "Contact");
        views.setValue("Supplier ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public String getValue(){return views.getValue();}

    @Override
    public ComboBox<String> getCombo(){
        return supplierCombo();
    }

    @Override
    public Map<String,String> getFormData(){
        return null;
    }

    @Override
    public void clearForm() {
    }
}
