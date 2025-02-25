package Front_end;

import Back_end.Refreshable;
import Back_end.SupplierManagerHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SupplierManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    public SupplierManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Supplier");
        Button deletebtn = new Button("Remove Supplier");
        Button viewbtn = new Button("View Supplier");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new SupplierManagerHandler("SearchSup",buttonContainer,manager,logoutContainer));
        deletebtn.setOnAction(new SupplierManagerHandler("RemSup",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new SupplierManagerHandler("ViewSup",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new SupplierManagerHandler("FormSup",buttonContainer,manager,logoutContainer));


        Button[] supplierbtns = {searchbtn,deletebtn,viewbtn,formbtn};
        for(Button button:supplierbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,deletebtn,viewbtn,formbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Supplier");
        Button updatebtn = new Button("Edit Supplier");
        Button viewbtn = new Button("View Supplier");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new SupplierManagerHandler("AddSup",buttonContainer,manager,logoutContainer));
        updatebtn.setOnAction(new SupplierManagerHandler("EditSup",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new SupplierManagerHandler("ViewSup",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new SupplierManagerHandler("FormSup",buttonContainer,manager,logoutContainer));


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
        ComboBox<String> views = new ComboBox<>();
        views.getItems().addAll("Supplier ID", "Supplier Name", "Contact");
        views.setValue("Supplier ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public ComboBox<String> getCombo(){
        return supplierCombo();
    }
}
