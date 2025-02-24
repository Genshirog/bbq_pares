package Front_end;

import Back_end.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Cashier {
    private StackPane root;
    private SceneManager scene;
    private VBox left_panel;
    public Cashier(SceneManager scene){
        this.scene = scene;
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
        right_panel.getChildren().addAll(logo_container(),buttonContainer(),logout());
        return right_panel;
    }

    private VBox comboHolder(){
        VBox comboHolder = new VBox();
        comboHolder.getStyleClass().addAll("holder","p-2");
        comboHolder.getChildren().add(views());
        return comboHolder;
    }
    private VBox tableHolder(){
        VBox tableHolder = new VBox();
        tableHolder.getStyleClass().add("table");
        return tableHolder;
    }

    private VBox formHolder(){
        VBox tableHolder = new VBox();
        tableHolder.getStyleClass().add("form");
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

            if("Employee View".equals(selectedView)){
                tableHolder.getChildren().add(createOrderTable());
            }else if("Inventory View".equals(selectedView)){
                tableHolder.getChildren().add(createRecieptTable());
            }else if("Menu".equals(selectedView)){
                tableHolder.getChildren().add(createMenuTable());
            }
        }
    }

    private VBox createOrderTable() {
        // Create Order Table
        TableView<EmployeeViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<EmployeeViews, String> idColumn = new TableColumn<>("EmployeeID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        idColumn.setMaxWidth(250);
        idColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> nameColumn = new TableColumn<>("Employee Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        nameColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setMaxWidth(100);
        roleColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMaxWidth(100);
        emailColumn.setReorderable(false);

        TableColumn<EmployeeViews, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneColumn.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, roleColumn, emailColumn, phoneColumn);
        table.setTableMenuButtonVisible(false);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
    }

    private VBox createRecieptTable(){
        TableView<InventoryViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<InventoryViews, String> idColumn = new TableColumn<>("InventoryID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryID"));
        idColumn.setReorderable(false);

        TableColumn<InventoryViews, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameColumn.setReorderable(false);

        TableColumn<InventoryViews, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockInColumn = new TableColumn<>("Date");
        stockInColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        stockInColumn.setReorderable(false);

        TableColumn<InventoryViews, String> stockDetails = new TableColumn<>("Details");
        stockDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        stockDetails.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, stockColumn, stockInColumn,stockDetails);

        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
    }

    private VBox createMenuTable(){
        TableView<MenuViews> table = new TableView<>();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        TableColumn<MenuViews, String> idColumn = new TableColumn<>("MenuID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("menuID"));
        idColumn.setReorderable(false);

        TableColumn<MenuViews, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameColumn.setReorderable(false);

        TableColumn<MenuViews, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setReorderable(false);

        TableColumn<MenuViews, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setReorderable(false);

        TableColumn<MenuViews, String> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        availabilityColumn.setReorderable(false);

        TableColumn<MenuViews, String> stockDetails = new TableColumn<>("Details");
        stockDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        stockDetails.setReorderable(false);

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, priceColumn, availabilityColumn,stockDetails);
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox.setVgrow(table,Priority.ALWAYS);
        table.setMaxHeight(Double.MAX_VALUE);

        return new VBox(scrollPane);
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

    private VBox buttonContainer(){
        VBox buttonContainer = new VBox(10);
        buttonContainer.getStyleClass().add("btncontainer");

        Button Employeebtn = new Button("EMPLOYEE");
        Button Rolesbtn = new Button("ROLES");
        Button Productbtn = new Button("PRODUCTS");
        Button Supplierbtn = new Button("SUPPLIER");

        Employeebtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Rolesbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Productbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        Supplierbtn.getStyleClass().addAll("btn-1","background-radius-1","border-radius");
        buttonContainer.getChildren().addAll(Employeebtn,Rolesbtn,Productbtn,Supplierbtn);
        return buttonContainer;
    }

    private VBox logout(){
        VBox logoutContainer = new VBox();
        logoutContainer.getStyleClass().add("logout");

        Button logout = new Button("LOG OUT");
        logout.getStyleClass().addAll("btn-1","background-radius","border-radius");
        logoutContainer.getChildren().add(logout);
        return logoutContainer;
    }
}
