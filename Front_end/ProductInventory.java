package Front_end;

import Back_end.ProductInventoryHandler;
import Back_end.Refreshable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.util.Map;

public class ProductInventory implements Refreshable {
    private final VBox buttonContainer;
    private final Inventory inventory;
    private final VBox logoutContainer;
    public ProductInventory(VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.inventory = inventory;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new ProductInventoryHandler("SearchProd",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new ProductInventoryHandler("ViewProd",buttonContainer,inventory,logoutContainer));
        formbtn.setOnAction(new ProductInventoryHandler("FormProd",buttonContainer,inventory,logoutContainer));


        Button[] employeebtns = {searchbtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,viewbtn,formbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button updatebtn = new Button("Stock In");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        updatebtn.setOnAction(new ProductInventoryHandler("EditProd",buttonContainer,inventory,logoutContainer));
        viewbtn.setOnAction(new ProductInventoryHandler("ViewProd",buttonContainer,inventory,logoutContainer));
        formbtn.setOnAction(new ProductInventoryHandler("FormProd",buttonContainer,inventory,logoutContainer));


        Button[] employeebtns = {updatebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(updatebtn,viewbtn,formbtn);
    }

    private GridPane formHolder(){
        GridPane form = new GridPane();
        form.getStyleClass().add("form");
        form.setVgap(50);
        form.setHgap(20);

        Label id = new Label("Product ID");
        TextField empID = new TextField();
        form.add(id,0,0);
        form.add(empID,1,0);

        Label quantity = new Label("Quantity");
        TextField quantext = new TextField();
        form.add(quantity,0,1);
        form.add(quantext,1,1);

        TextField[] texts = {empID,quantext};
        Label[] labels = {id,quantity};
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
        views.getItems().addAll("Product ID", "Product Name", "Supplier");
        views.setValue("Product ID");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        return views;
    }

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
