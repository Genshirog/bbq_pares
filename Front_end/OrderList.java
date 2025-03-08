package Front_end;

import Back_end.DatabaseHandler;
import Back_end.OrderView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderList {
    private StackPane root;
    private SceneManager sceneManager;
    private DatabaseHandler dbHandler;

    public OrderList(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.dbHandler = new DatabaseHandler();
        root = new StackPane();
        root.getStyleClass().add("root_form");
        root.getChildren().add(overlay());
        root.getChildren().add(contents());
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 850, 600); // Matches prototype window size
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
        contents.setPadding(new Insets(50));

        // Create table labeled as OrderList
        TableView<OrderView> table = new TableView<>();
        table.setId("OrderList");
        table.getStyleClass().add("menu-table");
        table.setPrefSize(600, 400); // Matches prototype table size

        // Define columns
        TableColumn<OrderView, String> orderIdCol = new TableColumn<>("OrderID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdCol.setPrefWidth(120);
        orderIdCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");

        TableColumn<OrderView, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameCol.setPrefWidth(150);
        nameCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");

        TableColumn<OrderView, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        qtyCol.setPrefWidth(100);
        qtyCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");

        TableColumn<OrderView, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(100);
        priceCol.setCellFactory(col -> new TableCell<OrderView, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : "₱" + String.format("%.2f", price));
            }
        });
        priceCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");

        TableColumn<OrderView, Double> totalCol = new TableColumn<>("Total Price");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        totalCol.setPrefWidth(120);
        totalCol.setCellFactory(col -> new TableCell<OrderView, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                setText(empty || total == null ? null : "₱" + String.format("%.2f", total));
            }
        });
        totalCol.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER;");

        table.getColumns().addAll(orderIdCol, nameCol, qtyCol, priceCol, totalCol);

        // Populate table with data
        table.getItems().addAll(dbHandler.getOrders());
        System.out.println("Orders fetched: " + dbHandler.getOrders().size()); // Debug line

        // Buttons
        Button cancelBtn = new Button("CANCEL ORDER");
        Button backBtn = new Button("BACK TO ORDERING"); // Updated label to match prototype
        cancelBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");
        backBtn.getStyleClass().addAll("btn-1", "background-radius-1", "border-radius");

        HBox buttonBox = new HBox(20, cancelBtn, backBtn);
        buttonBox.setPadding(new Insets(20));

        // Button actions
        cancelBtn.setOnAction(e -> {
            OrderView selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancel Order");
                alert.setHeaderText("Are you sure you want to cancel order " + selected.getOrderId() + "?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if (dbHandler.deleteOrder(selected.getOrderId())) {
                            table.getItems().remove(selected);
                            showAlert("Order cancelled successfully!");
                        } else {
                            showAlert("Failed to cancel order!");
                        }
                    }
                });
            } else {
                showAlert("Please select an order to cancel!");
            }
        });

        backBtn.setOnAction(e -> sceneManager.show_main_cashierorder());

        // Layout: Table in center, buttons below
        VBox mainContainer = new VBox(20, table, buttonBox);
        contents.setCenter(mainContainer);

        return contents;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}