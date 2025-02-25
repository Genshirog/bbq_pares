package Front_end;

import Back_end.ProductManagerHandler;
import Back_end.Refreshable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ProductManager implements Refreshable {
    private final VBox buttonContainer;
    private final Manager manager;
    private final VBox logoutContainer;
    public ProductManager(VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.buttonContainer = buttonContainer;
        this.manager = manager;
        this.logoutContainer = logoutContainer;
    }

    @Override
    public void view_btn(){
        buttonContainer.getChildren().clear();
        Button searchbtn = new Button("Search Product");
        Button deletebtn = new Button("Remove Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        searchbtn.setOnAction(new ProductManagerHandler("SearchProd",buttonContainer,manager,logoutContainer));
        deletebtn.setOnAction(new ProductManagerHandler("RemProd",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new ProductManagerHandler("ViewProd",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new ProductManagerHandler("FormProd",buttonContainer,manager,logoutContainer));


        Button[] employeebtns = {searchbtn,deletebtn,viewbtn,formbtn};
        for(Button button:employeebtns){
            button.getStyleClass().addAll("btn-1","border-radius","background-radius-1");
        }

        buttonContainer.getChildren().addAll(searchbtn,deletebtn,viewbtn,formbtn);
    }

    @Override
    public void form_btn(){
        buttonContainer.getChildren().clear();
        Button createbtn = new Button("Add Product");
        Button updatebtn = new Button("Edit Product");
        Button viewbtn = new Button("View Product");
        Button formbtn = new Button("Reset Form");

        createbtn.setOnAction(new ProductManagerHandler("AddProd",buttonContainer,manager,logoutContainer));
        updatebtn.setOnAction(new ProductManagerHandler("EditProd",buttonContainer,manager,logoutContainer));
        viewbtn.setOnAction(new ProductManagerHandler("ViewProd",buttonContainer,manager,logoutContainer));
        formbtn.setOnAction(new ProductManagerHandler("FormProd",buttonContainer,manager,logoutContainer));


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
        TextField empID = new TextField();
        form.add(id,0,0);
        form.add(empID,1,0);

        Label pname = new Label("Product Name");
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
}
