package Front_end;

import Back_end.*;
import Back_end.MenuItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashierOrder implements OrderItem.OrderItemSelectionListener {
    public SceneManager scene;
    private StackPane root;
    private VBox left_panel;

    // Menu items data

    // Order items
    private VBox orderItemsBox;
    private Label totalLabel;
    private Map<String, OrderItem> orderItems = new HashMap<>();
    private TableManager tableManager;
    // Currently selected order item (for removal)
    private OrderItem selectedOrderItem = null;

    private String currentOrderId; // Added to track OrderID
    private DatabaseHandler databaseHandler; // Added to handle OrderID generation

    private TextField orderIdField;
    private boolean isCancellationMode = false;

    public CashierOrder(SceneManager scene) {
        this.scene = scene;
        this.tableManager = new TableManager();
        this.databaseHandler = new DatabaseHandler();
        root = new StackPane();
        root.getStyleClass().add("root_form");
        root.getChildren().add(overlay());
        root.getChildren().add(contents());
        try {
            this.currentOrderId = databaseHandler.getNextOrderId();
        } catch (Exception e) {
            System.err.println("Error fetching next Order ID: " + e.getMessage());
            this.currentOrderId = "ORD001"; // Fallback value
        }
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 850, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot() {
        return root;
    }

    public Label getTotalLabel() {
        return totalLabel;
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
        leftContainer.getChildren().addAll(paresLabel, tableManager.setupMenuTable(this));

        // Right container for order summary
        VBox rightContainer = createRightPanel();

        mainLayout.getChildren().addAll(leftContainer, rightContainer);
        contents.setCenter(mainLayout);

        return contents;
    }

    private VBox createRightPanel() {
        VBox rightContainer = new VBox(15);
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
        scrollPane.setPrefHeight(300);
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
        VBox buttonContainer = new VBox(10);
        buttonContainer.getStyleClass().add("btncontainer");

        // Create buttons
        Button confirmBtn = new Button("CONFIRM ORDER");
        Button resetBtn = new Button("RESET ORDER");
        Button removeBtn = new Button("REMOVE ORDER");
        Button cancelBtn = new Button("CANCEL ORDER");
        Button backBtn = new Button("BACK");
        // Add styles
        confirmBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        resetBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        removeBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        cancelBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        backBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");

        cancelBtn.setOnAction(new CashierOrderHandler(selectedOrderItem, this, orderItems, orderItemsBox, "cancel"));
        confirmBtn.setOnAction(new CashierOrderHandler(selectedOrderItem, this, orderItems, orderItemsBox, "confirm"));
        resetBtn.setOnAction(new CashierOrderHandler(selectedOrderItem, this, orderItems, orderItemsBox, "reset"));
        removeBtn.setOnAction(new CashierOrderHandler(selectedOrderItem, this, orderItems, orderItemsBox, "remove"));
        backBtn.setOnAction(new CashierOrderHandler(selectedOrderItem, this, orderItems, orderItemsBox, "back"));
        // Add button actions

        buttonContainer.getChildren().addAll(confirmBtn, resetBtn, removeBtn, cancelBtn, backBtn);
        return buttonContainer;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addToOrder(MenuItem menuItem) {
        String key = menuItem.getCode() + " - " + menuItem.getName();

        if (orderItems.containsKey(key)) {
            // Item already in order, update quantity
            OrderItem orderItem = orderItems.get(key);
            orderItem.setQuantity(orderItem.getQuantity() + 1);
            orderItem.updateTotal();
            orderItem.updateLabel();
        } else {
            // New item, add to order
            OrderItem orderItem = new OrderItem(menuItem, this);
            orderItems.put(key, orderItem);
            orderItemsBox.getChildren().add(orderItem.getContainer());
        }

        // Update total
        updateTotal();
    }

    public void updateTotal() {
        double totalAmount = resetTotal();
        for (OrderItem item : orderItems.values()) {
            totalAmount += item.getTotal();
        }
        totalLabel.setText(String.format("Total: ₱%.2f", totalAmount));
    }

    public double resetTotal() {
        return 0.0;
    }

    @Override
    public void onOrderItemSelected(OrderItem orderItem) {
        if (selectedOrderItem != null) {
            selectedOrderItem.setSelected(false);
        }

        // Select new item
        selectedOrderItem = orderItem;
        selectedOrderItem.setSelected(true);
    }

    public OrderItem getSelectedOrderItem() {
        return selectedOrderItem;
    }

    public void setSelectedOrderItem(OrderItem orderItem) {
        this.selectedOrderItem = orderItem;
    }

    // --- ADDED FOR ORDERCANCELLATIONHANDLER INTEGRATION ---
    public String getCurrentOrderId() {
        return currentOrderId; // Return the current OrderID
    }

    public void setCurrentOrderId(String currentOrderId) {
        this.currentOrderId = currentOrderId;
    }

    public VBox getOrderItemsBox() {
        return orderItemsBox;
    }

    public Map<String, OrderItem> getOrderItems() {
        return orderItems;
    }

    public void clearOrder() {
        orderItemsBox.getChildren().clear();
        orderItems.clear();
        totalLabel.setText("Total: ₱0.00");
        selectedOrderItem = null;
        try {
            currentOrderId = databaseHandler.getNextOrderId();
        } catch (Exception e) {
            System.err.println("Error fetching next Order ID: " + e.getMessage());
            currentOrderId = "ORD001"; // Fallback value
        }
    }

    public void showOrderCancellation() {
        if (!isCancellationMode) {
            isCancellationMode = true;
            root.getChildren().clear();
            root.getChildren().add(overlay());

            VBox cancellationLayout = new VBox(15);
            cancellationLayout.setAlignment(Pos.CENTER);
            cancellationLayout.setPadding(new Insets(20));
            cancellationLayout.getStyleClass().add("cancellation-layout");

            // Order ID input
            HBox orderIdBox = new HBox(10);
            orderIdBox.setAlignment(Pos.CENTER);
            orderIdBox.getStyleClass().add("cancellation-orderid-box");
            Label orderIdLabel = new Label("Order ID:");
            orderIdLabel.getStyleClass().add("cancellation-label");
            orderIdField = new TextField();
            orderIdField.setPromptText("Enter OrderID here");
            orderIdField.getStyleClass().add("cancellation-textfield");
            orderIdField.getStyleClass().addAll("textfield-1", "border-radius", "background-radius");
            Button fetchButton = new Button("FETCH ORDER");
            fetchButton.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
            fetchButton.setOnAction(e -> fetchOrder());
            orderIdBox.getChildren().addAll(orderIdLabel, orderIdField, fetchButton);

            // Order items area (reusing orderItemsBox)
            orderItemsBox.getChildren().clear();
            orderItemsBox.getStyleClass().add("cancellation-items-box");

            ScrollPane scrollPane = new ScrollPane(orderItemsBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(200);
            scrollPane.getStyleClass().add("order-scroll");

            // Total label
            totalLabel.setText("TOTAL:");
            totalLabel.getStyleClass().add("total-label");
            totalLabel.getStyleClass().add("cancellation-total-label");


            // Buttons
            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getStyleClass().add("cancellation-button-box");
            Button cancelOrderBtn = new Button("CANCEL ORDER");
            Button backBtn = new Button("BACK");
            cancelOrderBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
            backBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
            cancelOrderBtn.setOnAction(e -> cancelOrder());
            backBtn.setOnAction(e -> revertToOrderMode());
            buttonBox.getChildren().addAll(cancelOrderBtn, backBtn);

            cancellationLayout.getChildren().addAll(orderIdBox, scrollPane, totalLabel, buttonBox);
            root.getChildren().add(cancellationLayout);
        }
    }
    // MODIFY THIS IN CashierOrder.java: Handle exception within fetchOrder instead of rethrowing
    private void fetchOrder() {
        String orderId = orderIdField.getText().trim();
        if (orderId.isEmpty()) {
            showAlert("Please enter an Order ID.");
            return;
        }

        List<OrderItem> items = null;
        try {
            items = databaseHandler.getOrderItemsByOrderId(orderId);
        } catch (Exception e) {
            showAlert("Failed to fetch order: " + e.getMessage());
            System.err.println("Error fetching order items: " + e.getMessage());
            return;
        }

        if (items.isEmpty()) {
            showAlert("No order found with Order ID: " + orderId);
            return;
        }

        orderItemsBox.getChildren().clear();
        for (OrderItem item : items) {
            orderItemsBox.getChildren().add(item.getContainer()); // Use existing container
        }

        double total = items.stream().mapToDouble(OrderItem::getTotal).sum();
        totalLabel.setText(String.format("TOTAL: ₱%.2f", total));
    }

    private void cancelOrder() {
        String orderId = orderIdField.getText().trim();
        if (orderId.isEmpty()) {
            showAlert("Please enter an Order ID.");
            return;
        }

        boolean success = false;
        try {
            success = databaseHandler.cancelOrder(orderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (success) {
            showAlert("Order " + orderId + " has been successfully canceled.");
            orderItemsBox.getChildren().clear();
            totalLabel.setText("TOTAL: ₱0.00");
            orderIdField.clear();
        } else {
            showAlert("Failed to cancel order " + orderId + ".");
        }
    }

    private void revertToOrderMode() {
        isCancellationMode = false;
        root.getChildren().clear();
        root.getChildren().add(overlay());
        root.getChildren().add(contents());
    }
}