package Back_end;

import Front_end.CashierOrder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class CashierOrderHandler implements EventHandler<ActionEvent> {
    private OrderItem selectedOrderItem;
    private CashierOrder cashierOrder;
    private Map<String, OrderItem> orderItems;
    private VBox orderItemsBox;
    private String btn;
    private DatabaseHandler dbHandler;

    public CashierOrderHandler(OrderItem selectedOrderItem, CashierOrder cashierOrder, Map<String, OrderItem> orderItems, VBox orderItemsBox, String btn) {
        this.selectedOrderItem = selectedOrderItem;
        this.cashierOrder = cashierOrder;
        this.orderItems = orderItems;
        this.orderItemsBox = orderItemsBox;
        this.btn = btn;
        this.dbHandler = new DatabaseHandler();
    }

    @Override
    public void handle(ActionEvent event) {
        switch (btn){
            case "cancel":
                System.out.println("Nigga1");
                cashierOrder.scene.show_order_history();
                break;
            case "reset":
                orderItems.clear();
                orderItemsBox.getChildren().clear();
                cashierOrder.resetTotal();
                cashierOrder.updateTotal();
                selectedOrderItem = null;
                break;
            case "remove":
                if (cashierOrder.getSelectedOrderItem() != null) {
                    OrderItem selectedItem = cashierOrder.getSelectedOrderItem();
                    String key = selectedItem.getMenuItemKey();

                    if (selectedItem.getQuantity() > 1) {
                        selectedItem.setQuantity(selectedItem.getQuantity() - 1);
                        selectedItem.updateTotal();
                        selectedItem.updateLabel();
                    } else {
                        orderItems.remove(key);
                        orderItemsBox.getChildren().remove(selectedItem.getContainer());
                        cashierOrder.setSelectedOrderItem(null);
                    }

                    cashierOrder.updateTotal();
                } else {
                    cashierOrder.showAlert("Please select an item from your order to remove.");
                }
                break;
            case "back":
                System.out.println("Nigga4");
                break;
            case "confirm":
                if (orderItems.isEmpty()) {
                    cashierOrder.showAlert("Please add items to the order first!");
                    return;
                }

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Order");
                confirmAlert.setHeaderText("Are you sure you want to confirm this order?");
                Optional<ButtonType> result = confirmAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Get the next sequential order ID instead of random
                    String orderId = dbHandler.getNextOrderId();

                    // Use the getter method for totalLabel
                    boolean saved = dbHandler.saveOrder(orderId,
                            new java.util.ArrayList<>(orderItems.values()),
                            Double.parseDouble(cashierOrder.getTotalLabel().getText().replace("Total: ₱", "")));

                    if (saved) {
                        cashierOrder.showAlert("Order " + orderId + " saved successfully!");
                        orderItems.clear();
                        orderItemsBox.getChildren().clear();
                        cashierOrder.updateTotal();
                    } else {
                        cashierOrder.showAlert("Failed to save order!");
                    }
                }
                break;
        }
    }
}
