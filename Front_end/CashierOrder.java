package Front_end;

import Back_end.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class CashierOrder {
    private StackPane root;
    private SceneManager scene;
    private VBox left_panel;

    // Menu items data
    private TableView<MenuItem> menuTable;

    // Order items
    private VBox orderItemsBox;
    private Label totalLabel;
    private Map<String, OrderItem> orderItems = new HashMap<>();
    private double totalAmount = 0.0;

    // Currently selected order item (for removal)
    private OrderItem selectedOrderItem = null;

    public CashierOrder(SceneManager scene) {
        this.scene = scene;
        root = new StackPane();
        root.getStyleClass().add("root_form");
        root.getChildren().add(overlay());
        root.getChildren().add(contents());
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 850, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot() {
        return root;
    }

    private Pane overlay() {
        Pane overlay = new Pane();
        overlay.getStyleClass().add("overlay");
        return overlay;
    }

    private BorderPane contents() {
        BorderPane contents = new BorderPane();

        // Main layout with left panel (menu) and right panel (order summary)
        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(50));

        // Left container for menu
        VBox leftContainer = new VBox(20);
        leftContainer.setPrefWidth(900);
        leftContainer.getStyleClass().add("menu-container");

        // Section labels
        Label paresLabel = new Label("BBQ-LAGAO & BEEF PARES");
        paresLabel.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));
        paresLabel.setAlignment(Pos.CENTER);
        paresLabel.getStyleClass().add("section-label");

        // Create menu table
        setupMenuTable();
        leftContainer.getChildren().addAll(paresLabel, menuTable);

        // Right container for order summary
        VBox rightContainer = createRightPanel();

        mainLayout.getChildren().addAll(leftContainer, rightContainer);
        contents.setCenter(mainLayout);

        return contents;
    }

    private VBox createRightPanel() {
        VBox rightContainer = new VBox(10);
        rightContainer.getStyleClass().add("order-container");

        // Order summary title
        Label orderTitle = new Label("Order Summary:");
        orderTitle.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 16));
        orderTitle.getStyleClass().add("order-title");

        // Order items container
        orderItemsBox = new VBox(10);
        orderItemsBox.setPadding(new Insets(10, 0, 10, 0));
        ScrollPane scrollPane = new ScrollPane(orderItemsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
        scrollPane.getStyleClass().add("order-scroll");

        // Total amount
        totalLabel = new Label("Total: ₱0.00");
        totalLabel.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 14));
        totalLabel.getStyleClass().add("total-label");

        // Logo at the top
        VBox logoContainer = logo_container();

        // Buttons at the bottom
        VBox buttonContainer = buttonContainer();

        rightContainer.getChildren().addAll(logoContainer, orderTitle, scrollPane, totalLabel, buttonContainer);

        return rightContainer;
    }

    private void setupMenuTable() {
        menuTable = new TableView<>();
        menuTable.getStyleClass().add("menu-table");
        menuTable.setPrefSize(750, 900);

        // Columns
        TableColumn<MenuItem, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setPrefWidth(180);
        codeCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        codeCol.setReorderable(false);
        codeCol.setResizable(false);
        codeCol.setSortable(false);

        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(280);
        nameCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        nameCol.setReorderable(false);
        nameCol.setResizable(false);
        nameCol.setSortable(false);

        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(300);
        priceCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");
        priceCol.setReorderable(false);
        priceCol.setResizable(false);
        priceCol.setSortable(false);
        priceCol.setCellFactory(col -> new TableCell<MenuItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText("₱" + String.format("%.2f", price));
                }
            }
        });

        menuTable.getColumns().addAll(codeCol, nameCol, priceCol);

        // Data
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

        // Add menu data
        // PARES MEALS
        menuItems.add(new MenuItem("A1", "PARES", 80.0));
        menuItems.add(new MenuItem("A2", "Beef PARES", 80.0));
        menuItems.add(new MenuItem("A3", "OVERLOAD PARES", 100.0));

        // FRIED & GRILLED MEALS
        menuItems.add(new MenuItem("B1", "KAWALI", 90.0));
        menuItems.add(new MenuItem("B2", "BULAK-BULAK", 90.0));
        menuItems.add(new MenuItem("B3", "LIEMPO", 95.0));

        // CHICKEN MEALS
        menuItems.add(new MenuItem("C1", "PAA", 85.0));
        menuItems.add(new MenuItem("C2", "PETCHO", 85.0));
        menuItems.add(new MenuItem("C3", "CHOT", 85.0));

        // PORK INNARDS & SPECIALTY
        menuItems.add(new MenuItem("D1", "POUL", 15.0));
        menuItems.add(new MenuItem("D2", "BAT", 15.0));
        menuItems.add(new MenuItem("D3", "ATAY", 15.0));
        menuItems.add(new MenuItem("D4", "ISAW", 10.0));
        menuItems.add(new MenuItem("D5", "ISOL", 25.0));
        menuItems.add(new MenuItem("D6", "LIOG", 20.0));
        menuItems.add(new MenuItem("D7", "CS", 20.0));
        menuItems.add(new MenuItem("D8", "CM", 20.0));
        menuItems.add(new MenuItem("D9", "PICHO", 20.0));

        // HOTDOG MEALS
        menuItems.add(new MenuItem("E1", "HOTDOG", 15.0));
        menuItems.add(new MenuItem("E2", "HOTDOG TJ", 20.0));

        // SEAFOOD MEALS
        menuItems.add(new MenuItem("F1", "PANGA BANGUS", 150.0));
        menuItems.add(new MenuItem("F2", "PANGAS", 150.0));
        menuItems.add(new MenuItem("F3", "PUSIT", 150.0));
        menuItems.add(new MenuItem("F4", "BELLY", 120.0));

        // SIDES & ADD-ONS
        menuItems.add(new MenuItem("G1", "RICE", 15.0));
        menuItems.add(new MenuItem("G2", "KASALO", 40.0));

        menuTable.setItems(menuItems);

        // Setup actions for menu table
        menuTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                MenuItem selectedItem = menuTable.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    addToOrder(selectedItem);
                }
            }
        });
    }

    private VBox logo_container() {
        VBox logo_container = new VBox();
        logo_container.getStyleClass().add("container-order-logo");

        logo_container.getChildren().add(logo());
        return logo_container;
    }

    private Pane logo() {
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

    private VBox buttonContainer() {
        VBox buttonContainer = new VBox(20);
        buttonContainer.getStyleClass().add("btncontainer");

        // Create buttons
        Button confirmBtn = new Button("CONFIRM ORDER");
        Button resetBtn = new Button("RESET ORDER");
        Button removeBtn = new Button("REMOVE ORDER");
        Button cancelBtn = new Button("CANCEL ORDER");

        // Add styles
        confirmBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        resetBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        removeBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        cancelBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");

        // Add button actionS
        removeBtn.setOnAction(e -> {
            if (selectedOrderItem != null) {
                String key = selectedOrderItem.getMenuItemKey();

                if (selectedOrderItem.getQuantity() > 1) {
                    selectedOrderItem.setQuantity(selectedOrderItem.getQuantity() - 1);
                    selectedOrderItem.updateTotal();
                    selectedOrderItem.updateLabel();
                } else {
                    orderItems.remove(key);
                    orderItemsBox.getChildren().remove(selectedOrderItem.getContainer());

                    // Auto-select the next available item
                    if (!orderItems.isEmpty()) {
                        selectedOrderItem = orderItems.values().iterator().next(); // Select first available item
                    } else {
                        selectedOrderItem = null; // No items left
                    }
                }

                updateTotal();
            } else {
                showAlert("Please select an item from your order to remove.");
            }
        });


        resetBtn.setOnAction(e -> {
            orderItems.clear();
            orderItemsBox.getChildren().clear();
            totalAmount = 0.0;
            updateTotal();
            selectedOrderItem = null;
        });

        buttonContainer.getChildren().addAll(confirmBtn, resetBtn, removeBtn, cancelBtn);
        return buttonContainer;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addToOrder(MenuItem menuItem) {
        String key = menuItem.getCode() + " - " + menuItem.getName();

        if (orderItems.containsKey(key)) {
            // Item already in order, update quantity
            OrderItem orderItem = orderItems.get(key);
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            orderItem.updateTotal();
            orderItem.updateLabel();
        } else {
            // New item, add to order
            OrderItem orderItem = new OrderItem(menuItem);
            orderItems.put(key, orderItem);
            orderItemsBox.getChildren().add(orderItem.getContainer());
        }

        // Update total
        updateTotal();
    }

    private void updateTotal() {
        totalAmount = 0.0;
        for (OrderItem item : orderItems.values()) {
            totalAmount += item.getTotal();
        }
        totalLabel.setText(String.format("Total: ₱%.2f", totalAmount));
    }

    // Menu Item class
    public static class MenuItem {
        private String code;
        private String name;
        private double price;

        public MenuItem(String code, String name, double price) {
            this.code = code;
            this.name = name;
            this.price = price;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public double getPrice() { return price; }
    }

    // Order Item class
    private class OrderItem {
        private MenuItem menuItem;
        private int quantity;
        private double total;
        private HBox container;
        private Label itemLabel;

        public OrderItem(MenuItem menuItem) {
            this.menuItem = menuItem;
            this.quantity = 1;
            this.total = menuItem.getPrice();

            createContainer();
        }

        private void createContainer() {
            container = new HBox(10);
            container.setPadding(new Insets(5));
            container.getStyleClass().add("order-item");

            itemLabel = new Label(String.format("%s %dx ₱%.2f",
                    menuItem.getName(), quantity, total));
            itemLabel.getStyleClass().add("order-item");

            container.getChildren().add(itemLabel);

            // Add click handler to the container for selecting items
            container.setOnMouseClicked(event -> {
                // Deselect previously selected item if any
                if (selectedOrderItem != null) {
                    selectedOrderItem.getContainer().getStyleClass().remove("selected-order-item");
                    selectedOrderItem.getContainer().getStyleClass().add("order-item");
                }

                // Select this item
                selectedOrderItem = this;
                container.getStyleClass().remove("order-item");
                container.getStyleClass().add("selected-order-item");
            });
        }

        public void updateTotal() {
            this.total = menuItem.getPrice() * quantity;
        }

        public void updateLabel() {
            itemLabel.setText(String.format("%s %dx ₱%.2f",
                    menuItem.getName(), quantity, total));
        }

        public String getMenuItemKey() {
            return menuItem.getCode() + " - " + menuItem.getName();
        }

        public HBox getContainer() { return container; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getTotal() { return total; }
    }
}