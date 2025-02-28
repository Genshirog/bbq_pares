package Front_end;

import Back_end.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.management.relation.Role;
import javax.xml.crypto.Data;
import java.util.List;

public class Manager {
    private StackPane root;
    private SceneManager scene;
    private VBox left_panel;
    private VBox btnContainer;
    private VBox logoutContainer;
    private HBox comboHolder;
    private TableManager tableManager;
    private TextField input;
    public Manager(SceneManager scene){
        this.scene = scene;
        this.btnContainer = new VBox(10);
        this.tableManager = new TableManager();
        root = new StackPane();
        root.getStyleClass().add("root_form");
        //root.setOpacity(0);
        root.getChildren().add(overlay());
        root.getChildren().add(contents());  // Add VBox to the root
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot(){
        return root;
    }

    private Pane overlay(){
        Pane overlay = new Pane();
        overlay.getStyleClass().add("overlay");
        return overlay;
    }

    private BorderPane contents(){
        BorderPane contents = new BorderPane();
        Region left_panel = left_panel(contents);
        HBox main_layout = new HBox(left_panel, right_panel(contents));
        HBox.setHgrow(left_panel,Priority.ALWAYS);

        contents.setCenter(main_layout);
        return contents;
    }

    private VBox left_panel(BorderPane contents){
        left_panel = new VBox();
        left_panel.getStyleClass().add("left_panel");
        left_panel.prefWidthProperty().bind(contents.widthProperty().multiply(0.75));
        left_panel.setAlignment(Pos.CENTER);

        left_panel.getChildren().addAll(comboHolder(),tableHolder());

        updateContent("Employee View");
        return left_panel;
    }

    private VBox right_panel(BorderPane contents){
        VBox right_panel = new VBox();
        right_panel.getStyleClass().add("right_panel");
        right_panel.prefWidthProperty().bind(contents.widthProperty().multiply(0.25));
        logoutContainer = logout();

        buttonContainer();

        right_panel.getChildren().addAll(logo_container(),btnContainer,logoutContainer);
        return right_panel;
    }

    private HBox comboHolder(){
        comboHolder = new HBox(20);
        comboHolder.getStyleClass().addAll("holder","p-2");
        comboHolder.getChildren().add(views());
        return comboHolder;
    }

    public void originalComboHolder(){
        comboHolder.getChildren().clear();
        comboHolder.getChildren().add(views());
    }

    public void updateComboHolder(ComboBox<String> combo){
        comboHolder.getChildren().clear();
        input = new TextField();
        input.getStyleClass().addAll("textfield-2","border-radius","background-radius");
        comboHolder.getChildren().addAll(combo,input);
    }

    public String getInput(){return input.getText();}

    public void clearComboHolder(){
        comboHolder.getChildren().clear();
    }

    private VBox tableHolder(){
        VBox tableHolder = new VBox();
        tableHolder.getStyleClass().add("table");
        return tableHolder;
    }

    private ComboBox<String> views(){
        ComboBox<String> views = new ComboBox<>();
        views.getItems().addAll("Employee View", "Inventory View", "Menu");
        views.setValue("Employee View");
        views.getStyleClass().addAll("border-radius", "background-radius", "manager-combo", "fs-1");
        views.setOnAction(event -> updateContent(views.getValue()));
        return views;
    }

    private void updateContent(String selectedView){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            DatabaseHandler db = new DatabaseHandler();
            if("Employee View".equals(selectedView)){
                List<EmployeeViews> employees = db.getEmployeeView();
                tableHolder.getChildren().add(tableManager.createEmployeeTable(employees));
            }else if("Inventory View".equals(selectedView)){
                List<InventoryViews> inventory = db.getInventoryView();
                tableHolder.getChildren().add(tableManager.createInventoryTable(inventory));
            }else if("Menu".equals(selectedView)){
                tableHolder.getChildren().add(tableManager.createMenuTable());
            }else{
                System.out.println("Does not exist");
            }
        }
    }

    public void updateEmployeeTable(List<EmployeeViews> employees){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
                tableHolder.getChildren().add(tableManager.createEmployeeTable(employees));
        }
    }

    public void updateRoleTable(List<RoleView> roles){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
                tableHolder.getChildren().add(tableManager.createRoleTable(roles));
        }
    }

    public void updateProductTable(List<ProductView> products){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
                tableHolder.getChildren().add(tableManager.createProductTable(products));
        }
    }

    public void updateSupplierTable(List<SupplierView> suppliers){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
                tableHolder.getChildren().add(tableManager.createSupplierTable(suppliers));
        }
    }

    public void displayForm(GridPane form){
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);
        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            tableHolder.getChildren().add(form);
        }
    }

    public void showEmployeeTable() {
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            DatabaseHandler dbHandler = new DatabaseHandler();
            List<EmployeeViews> employees = dbHandler.getEmployeeView();
            tableHolder.getChildren().add(tableManager.createEmployeeTable(employees));
        }
    }

    public void showRolesTable() {
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);
        DatabaseHandler db = new DatabaseHandler();
        List<RoleView> roles = db.getRoleView();
        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            tableHolder.getChildren().add(tableManager.createRoleTable(roles));
        }
    }

    public void showProductTable() {
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);

        DatabaseHandler db = new DatabaseHandler();
        List<ProductView> products = db.getProductView();
        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            tableHolder.getChildren().add(tableManager.createProductTable(products));
        }
    }

    public void showSupplierTable() {
        VBox tableHolder = left_panel.getChildren().stream()
                .filter(child -> child instanceof VBox && ((VBox) child).getStyleClass().contains("table"))
                .map(child -> (VBox) child)
                .findFirst()
                .orElse(null);
        DatabaseHandler db = new DatabaseHandler();
        List<SupplierView> suppliers = db.getSupplierView();
        if(tableHolder != null) {
            tableHolder.getChildren().clear();
            tableHolder.getChildren().add(tableManager.createSupplierTable(suppliers));
        }
    }

    private VBox logo_container(){
        VBox logo_container = new VBox();
        logo_container.getStyleClass().add("container-sm");
        logo_container.getChildren().add(logo());
        return logo_container;
    }

    private Pane logo(){
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/logo.png")));
        imageView.getStyleClass().add("imageLogo");
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true);

        Glow glow = new Glow(0.8);
        imageView.setEffect(glow);

        Timeline glowAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> glow.setLevel(0.8)),
                new KeyFrame(Duration.seconds(1), e -> glow.setLevel(1.5)),
                new KeyFrame(Duration.seconds(2), e -> glow.setLevel(0.8))
        );
        glowAnimation.setCycleCount(Timeline.INDEFINITE);
        glowAnimation.setAutoReverse(true);
        glowAnimation.play();


        Pane logo = new Pane();
        logo.getChildren().add(imageView);
        logo.getStyleClass().add("logo");
        return logo;
    }

    public void buttonContainer(){
        btnContainer.getChildren().clear();
        btnContainer.getStyleClass().add("btncontainer");

        Button Employeebtn = new Button("EMPLOYEE");
        Button Rolesbtn = new Button("ROLES");
        Button Productbtn = new Button("PRODUCTS");
        Button Supplierbtn = new Button("SUPPLIER");

        Employeebtn.setOnAction(new ManagerHandler("employee",btnContainer,this,logoutContainer));
        Rolesbtn.setOnAction(new ManagerHandler("roles",btnContainer,this,logoutContainer));
        Productbtn.setOnAction(new ManagerHandler("products",btnContainer,this,logoutContainer));
        Supplierbtn.setOnAction(new ManagerHandler("supplier",btnContainer,this,logoutContainer));

        Employeebtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Rolesbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Productbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Supplierbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        btnContainer.getChildren().addAll(Employeebtn,Rolesbtn,Productbtn,Supplierbtn);
    }

    public VBox logout(){
        logoutContainer = new VBox();
        logoutContainer.getStyleClass().add("logout");

        Button logout = new Button("LOG OUT");
        logout.setOnAction(new LogoutHandler(scene));
        logout.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        logoutContainer.getChildren().add(logout);
        return logoutContainer;
    }

    public void showLogoutButton() {
        logoutContainer.getChildren().clear();
        Button logout = new Button("LOG OUT");
        logout.setOnAction(new LogoutHandler(scene));
        logout.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        logoutContainer.getChildren().add(logout);
    }

    public void showBackButton() {
        logoutContainer.getChildren().clear();
        Button back = new Button("Back");
        back.getStyleClass().addAll("btn-1", "border-radius", "background-radius-1");
        back.setOnAction(new ManagerHandler("Back",btnContainer,this,logoutContainer));
        logoutContainer.getChildren().add(back);
    }

}
