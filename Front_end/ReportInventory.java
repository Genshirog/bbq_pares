package Front_end;

import Back_end.ReportInventoryHandler;
import Back_end.Refreshable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ReportInventory implements Refreshable {
    private final VBox buttonContainer;
    private final Inventory inventory;
    private final VBox logoutContainer;
    public ReportInventory(VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.inventory = inventory;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Report");
        Button viewbtn = new Button("View Report");

        searchbtn.setOnAction(new ReportInventoryHandler("SearchRepo",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new ReportInventoryHandler("ViewRepo",buttonContainer,inventory,logoutContainer));


        Button[] reportbtns = {searchbtn,viewbtn};
        for(Button button:reportbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,viewbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Report");
        Button updatebtn = new Button("Edit Report");
        Button viewbtn = new Button("View Report");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new ReportInventoryHandler("AddRepo",buttonContainer,inventory,logoutContainer));
        updatebtn.setOnAction(new ReportInventoryHandler("EditRepo",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new ReportInventoryHandler("ViewRepo",buttonContainer,inventory,logoutContainer));
        formbtn.setOnAction(new ReportInventoryHandler("FormRepo",buttonContainer,inventory,logoutContainer));


        Button[] reportbtns = {createbtn,updatebtn,viewbtn,formbtn};
        for(Button button:reportbtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(createbtn,updatebtn,viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Report ID");
        TextField empID = new TextField();
        form.add(id,0,0);
        form.add(empID,1,0);

        Label pname = new Label("Report Name");
        TextField prodName = new TextField();
        form.add(pname,2,0);
        form.add(prodName,3,0);

        Label price = new Label("Price");
        TextField pricetext = new TextField();
        form.add(price,0,1);
        form.add(pricetext,1,1);

        Label cost = new Label("Cost");
        TextField cost_text = new TextField();
        form.add(cost,2,1);
        form.add(cost_text,3,1);

        Label supplier = new Label("Supplier");
        TextField supplierText = new TextField();
        form.add(supplier,0,2);
        form.add(supplierText,1,2);

        TextField[] texts = {empID,prodName,cost_text,supplierText,pricetext};
        Label[] labels = {id,pname,price,cost, supplier};
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

    private ComboBox<String> reportCombo(){
        ComboBox<String> views = new ComboBox<>();
        views.getItems().addAll("Inventory ID", "Product Name", "Supplier Name");
        views.setValue("Inventory ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

    @Override
    public ComboBox<String> getCombo(){
        return reportCombo();
    }

    @Override
    public Map<String,String> getFormData(){
        return null;
    }

    @Override
    public void clearForm() {
    }
}
