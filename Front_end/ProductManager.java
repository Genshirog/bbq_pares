package Front_end;

import Back_end.ProductManagerHandler;
import Back_end.Refreshable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ProductManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    private TextField prodId;
    private TextField prodName;
    private TextField priceText;
    private TextField cost_text;
    private TextField supplierText;
    private ComboBox<String> views;
    public ProductManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
        initFormFields();
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Product");
        Button deletebtn = new Button("Remove Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new ProductManagerHandler("SearchProd",buttonContainer,manager,logoutContainer,this));
        deletebtn.setOnAction(new ProductManagerHandler("RemProd",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new ProductManagerHandler("ViewProd",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new ProductManagerHandler("FormProd",buttonContainer,manager,logoutContainer,this));


        Button[] employeebtns = {searchbtn,deletebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,deletebtn,viewbtn,formbtn);
    }

    private void initFormFields(){
        this.prodId = new TextField();
        this.prodName = new TextField();
        this.priceText = new TextField();
        this.cost_text = new TextField();
        this.supplierText = new TextField();

        // Add styles to all fields
        TextField[] texts = {prodId,prodName,priceText,cost_text,supplierText};
        for (TextField text : texts) {
            text.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
        }
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Product");
        Button updatebtn = new Button("Edit Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new ProductManagerHandler("AddProd",buttonContainer,manager,logoutContainer,this));
        updatebtn.setOnAction(new ProductManagerHandler("EditProd",buttonContainer,manager,logoutContainer,this));
        viewbtn.setOnAction(new ProductManagerHandler("ViewProd",buttonContainer,manager,logoutContainer,this));
        formbtn.setOnAction(new ProductManagerHandler("FormProd",buttonContainer,manager,logoutContainer,this));


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

        Label id = new Label("Product ID");
        form.add(id,0,0);
        form.add(prodId,1,0);

        Label pname = new Label("Product Name");
        form.add(pname,2,0);
        form.add(prodName,3,0);

        Label price = new Label("Price");
        form.add(price,0,1);
        form.add(priceText,1,1);

        Label cost = new Label("Cost");
        form.add(cost,2,1);
        form.add(cost_text,3,1);

        Label supplier = new Label("Supplier");
        form.add(supplier,0,2);
        form.add(supplierText,1,2);

        TextField[] texts = {prodId,prodName,cost_text,supplierText,priceText};
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
        data.put("name", prodName.getText());
        data.put("price", priceText.getText());
        data.put("cost", cost_text.getText());
        data.put("supplier", supplierText.getText());
        return data;
    }

    @Override
    public void clearForm() {
        prodId.clear();
        prodName.clear();
        priceText.clear();
        cost_text.clear();
        supplierText.clear();
    }
}
