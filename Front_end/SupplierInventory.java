package Front_end;

import Back_end.Refreshable;
import Back_end.SupplierInventoryHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SupplierInventory implements Refreshable {
    private final VBox buttonContainer;
    private final Inventory inventory;
    private final VBox logoutContainer;
    private TextField supID;
    private TextField supFname;
    private TextField supLname;
    private TextField supMI;
    private TextField supPerson;
    private TextField supMail;
    private TextField supNum;
    private TextField supAdd;
    private ComboBox<String> views;
    public SupplierInventory(VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.inventory = inventory;
        this.logoutContainer = logoutContainer;
        initFormFields();
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Supplier");
        Button viewbtn = new Button("View Supplier");

        searchbtn.setOnAction(new SupplierInventoryHandler("SearchSup",buttonContainer,inventory,logoutContainer,this));
        viewbtn.setOnAction(new SupplierInventoryHandler("ViewSup",buttonContainer,inventory,logoutContainer,this));


        Button[] supplierbtns = {searchbtn,viewbtn};
        for(Button button:supplierbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,viewbtn);
    }

    private void initFormFields(){
        this.supID = new TextField();
        this.supFname = new TextField();
        this.supLname = new TextField();
        this.supMI = new TextField();
        this.supPerson = new TextField();
        this.supMail = new TextField();
        this.supNum = new TextField();
        this.supAdd = new TextField();

        // Add styles to all fields
        TextField[] texts = {supID,supFname,supLname,supMI,supPerson,supMail,supNum,supAdd};
        for (TextField text : texts) {
            text.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
        }
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button viewbtn = new Button("View Supplier");
        Button formbtn = new Button("Reset Form");

        viewbtn.setOnAction(new SupplierInventoryHandler("ViewSup",buttonContainer,inventory,logoutContainer,this));
        formbtn.setOnAction(new SupplierInventoryHandler("FormSup",buttonContainer,inventory,logoutContainer,this));


        Button[] supplierbtns = {viewbtn,formbtn};
        for(Button button:supplierbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Supplier ID");
        form.add(id,0,0);
        form.add(supID,1,0);

        Label fname = new Label("First Name");
        form.add(fname,2,0);
        form.add(supFname,3,0);

        Label lname = new Label("Last Name");
        form.add(lname,0,1);
        form.add(supLname,1,1);

        Label Minitial = new Label("Middle Initial");
        form.add(Minitial,2,1);
        form.add(supMI,3,1);

        Label contactperson = new Label("Contact Person");
        form.add(contactperson,0,2);
        form.add(supPerson,1,2);

        Label email = new Label("Email");
        form.add(email,2,2);
        form.add(supMail,3,2);

        Label phoneNum = new Label("Phone Number");
        form.add(phoneNum,0,3);
        form.add(supNum,1,3);

        Label address = new Label("Address");
        form.add(address,2,3);
        form.add(supAdd,3,3);

        TextField[] texts = {supID,supFname,supMI,supPerson,supLname,supNum,supMail,supAdd};
        Label[] labels = {id,fname,lname,Minitial, contactperson, email,phoneNum,address};
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
        Map<String,String> data = new HashMap<>();
        data.put("id",supID.getText());
        data.put("fname",supFname.getText());
        data.put("lname",supLname.getText());
        data.put("mi",supMI.getText());
        data.put("person",supPerson.getText());
        data.put("mail",supMail.getText());
        data.put("num",supNum.getText());
        data.put("address",supAdd.getText());
        return data;
    }

    @Override
    public void clearForm() {
        supID.clear();
        supFname.clear();
        supLname.clear();
        supMI.clear();
        supPerson.clear();
        supMail.clear();
        supNum.clear();
        supAdd.clear();
    }
}
