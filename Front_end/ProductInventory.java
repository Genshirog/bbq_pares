package Front_end;

import Back_end.ProductInventoryHandler;
import Back_end.ProductManagerHandler;
import Back_end.Refreshable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ProductInventory implements Refreshable {
    private final VBox buttonContainer;
    private final Inventory inventory;
    private final VBox logoutContainer;
    private TextField prodId;
    private TextField prodQuantity;
    private ComboBox<String> views;
    public ProductInventory(VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.inventory = inventory;
        this.logoutContainer = logoutContainer;
        initFormFields();
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new ProductInventoryHandler("SearchProd",buttonContainer,inventory,logoutContainer,this));
        viewbtn.setOnAction(new ProductInventoryHandler("ViewProd",buttonContainer,inventory,logoutContainer,this));
        formbtn.setOnAction(new ProductInventoryHandler("FormProd",buttonContainer,inventory,logoutContainer,this));

        Button[] employeebtns = {searchbtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,viewbtn,formbtn);
    }

    private void initFormFields(){
        this.prodId = new TextField();
        this.prodQuantity = new TextField();

        // Add styles to all fields
        TextField[] texts = {prodId,prodQuantity};
        for (TextField text : texts) {
            text.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
        }
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button updatebtn = new Button("Stock-In");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        updatebtn.setOnAction(new ProductInventoryHandler("EditProd",buttonContainer,inventory,logoutContainer,this));
        viewbtn.setOnAction(new ProductInventoryHandler("ViewProd",buttonContainer,inventory,logoutContainer,this));
        formbtn.setOnAction(new ProductInventoryHandler("FormProd",buttonContainer,inventory,logoutContainer,this));


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

        Label id = new Label("Inventory ID");
        form.add(id,0,0);
        form.add(prodId,1,0);

        Label pname = new Label("Quantity");
        form.add(pname,0,1);
        form.add(prodQuantity,1,1);

        TextField[] texts = {prodId,prodQuantity};
        Label[] labels = {id,pname};
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
        views.getItems().addAll("Product ID", "Product Name", "Supplier");
        views.setValue("Product ID");
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
        data.put("id", prodId.getText());
        data.put("stock_quantity", prodQuantity.getText());
        return data;
    }

    @Override
    public void clearForm() {
        prodId.clear();
        prodQuantity.clear();
    }
}
