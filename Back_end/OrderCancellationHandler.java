package Back_end;

import Front_end.CashierOrder;
import Front_end.TableManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; // Added import for Button
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class OrderCancellationHandler implements EventHandler<ActionEvent> {
    private final CashierOrder cashierOrder;
    private final TableManager tableManager;
    private final DatabaseHandler databaseHandler;
    private final VBox orderContainer;

    public OrderCancellationHandler(CashierOrder cashierOrder, TableManager tableManager, DatabaseHandler databaseHandler, VBox orderContainer) {
        this.cashierOrder = cashierOrder;
        this.tableManager = tableManager;
        this.databaseHandler = databaseHandler;
        this.orderContainer = orderContainer;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof Button) { // Fixed by ensuring Button is imported
            String buttonText = ((Button) event.getSource()).getText();
            if ("CANCEL ORDER".equals(buttonText)) {
                cancelOrder();
            } else if ("BACK".equals(buttonText)) {
                goBackToOrderingTable();
            }
        }
    }

    private void cancelOrder() {
        String orderId = cashierOrder.getCurrentOrderId();
        if (orderId == null || orderId.isEmpty()) {
            showAlert("No order selected to cancel.");
            return;
        }

        // Check if the order has items
        Map<String, OrderItem> orderItems = cashierOrder.getOrderItems();
        if (orderItems.isEmpty()) {
            showAlert("No items in the order to cancel.");
            return;
        }

        Connection conn = null;
        try {
            conn = Database.getConnection();
            if (conn == null) {
                throw new SQLException("Failed to establish database connection.");
            }

            conn.setAutoCommit(false);

            // Delete from orderitems
            String deleteItemsSql = "DELETE FROM orderitems WHERE OrderID = ?";
            try (PreparedStatement itemStmt = conn.prepareStatement(deleteItemsSql)) {
                itemStmt.setString(1, orderId);
                itemStmt.executeUpdate();
            }

            // Delete from orders
            String deleteOrderSql = "DELETE FROM orders WHERE OrderID = ?";
            try (PreparedStatement orderStmt = conn.prepareStatement(deleteOrderSql)) {
                orderStmt.setString(1, orderId);
                orderStmt.executeUpdate(); // Fixed: Use orderStmt, not itemStmt
            }

            // Delete from receipt (if applicable)
            String deleteReceiptSql = "DELETE FROM receipt WHERE OrderID = ?";
            try (PreparedStatement receiptStmt = conn.prepareStatement(deleteReceiptSql)) {
                receiptStmt.setString(1, orderId);
                receiptStmt.executeUpdate();
            }

            // Update stock quantities in inventory (restore stock after cancellation)
            restoreStock(orderItems);

            conn.commit();
            cashierOrder.clearOrder(); // Reset the UI and order data
            showAlert("Order " + orderId + " cancelled successfully.");
        } catch (SQLException e) {
            System.err.println("Error cancelling order: " + e.getMessage());
            showAlert("Failed to cancel order due to database error: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            showAlert("An unexpected error occurred: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close connection: " + e.getMessage());
                }
            }
        }
    }

    private void goBackToOrderingTable() {
        // Clear the current order items and reset UI before returning to the menu table
        cashierOrder.clearOrder();
        orderContainer.getChildren().clear();
        orderContainer.getChildren().add(tableManager.setupMenuTable(cashierOrder));
    }

    private void restoreStock(Map<String, OrderItem> orderItems) throws SQLException {
        // Restore stock for each item in the order
        String updateStockSql = "UPDATE inventory SET stock_quantity = stock_quantity + ? WHERE ProductID = ? AND stock_date = (SELECT MAX(stock_date) FROM inventory WHERE ProductID = ?)";
        try (Connection conn = Database.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to connect to the database for stock update.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateStockSql)) {
                for (OrderItem item : orderItems.values()) {
                    String productId = item.getMenuItem().getCode();
                    int quantity = item.getQuantity();

                    stmt.setInt(1, quantity);
                    stmt.setString(2, productId);
                    stmt.setString(3, productId);
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected == 0) {
                        System.err.println("No inventory record found for ProductID: " + productId);
                    }
                }
            }

            // Update availability status after restoring stock
            updateInventoryAvailability();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateInventoryAvailability() throws SQLException {
        // Update the availability status based on stock quantity
        String updateAvailabilitySql = "UPDATE inventory SET availability = CASE WHEN stock_quantity > 0 THEN 'Available' ELSE 'Not Available' END WHERE ProductID = ? AND stock_date = (SELECT MAX(stock_date) FROM inventory WHERE ProductID = ?)";
        try (Connection conn = Database.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to connect to the database for availability update.");
            }

            for (OrderItem item : cashierOrder.getOrderItems().values()) {
                String productId = item.getMenuItem().getCode();
                try (PreparedStatement stmt = conn.prepareStatement(updateAvailabilitySql)) {
                    stmt.setString(1, productId);
                    stmt.setString(2, productId);
                    stmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}