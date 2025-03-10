package Back_end;

import Front_end.CashierOrder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.*;

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
                cashierOrder.showOrderCancellation();
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

                String orderDescription = null;
                boolean descriptionProvided = false;
                while (!descriptionProvided) {
                    TextInputDialog dialog = new TextInputDialog("Customer Order");
                    dialog.setTitle("Order Description");
                    dialog.setHeaderText("Please enter a description for this order:");
                    dialog.setContentText("Description:");
                    Optional<String> descriptionResult = dialog.showAndWait();
                    if (descriptionResult.isPresent() && !descriptionResult.get().trim().isEmpty()) {
                        orderDescription = descriptionResult.get().trim();
                        descriptionProvided = true;
                        System.out.println("Order Description set to: " + orderDescription); // Debug
                    } else {
                        cashierOrder.showAlert("Order description cannot be empty! Please provide a description.");
                    }
                }

                List<String> paymentOptions = Arrays.asList("Cash", "GCash");
                ChoiceDialog<String> paymentDialog = new ChoiceDialog<>("Cash", paymentOptions);
                paymentDialog.setTitle("Payment Method");
                paymentDialog.setHeaderText("Select a payment method:");
                paymentDialog.setContentText("Payment:");
                Optional<String> paymentResult = paymentDialog.showAndWait();

                if (paymentResult.isEmpty()) {
                    cashierOrder.showAlert("You must select a payment method!");
                    return;
                }
                String paymentMethod = paymentResult.get();

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Order");
                confirmAlert.setHeaderText("Are you sure you want to confirm this order?");
                confirmAlert.setContentText("Description: " + orderDescription);
                Optional<ButtonType> result = confirmAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        String totalText = cashierOrder.getTotalLabel() != null ? cashierOrder.getTotalLabel().getText() : "0";
                        String cleanedTotal = totalText.replace("Total: ₱", "").trim();
                        double total = cleanedTotal.isEmpty() ? 0.0 : Double.parseDouble(cleanedTotal);

                        String orderId = dbHandler.getNextOrderId();
                        System.out.println("Generated Order ID: " + orderId); // Debug
                        System.out.println("Order Description before saveOrder: " + orderDescription); // Debug

                        // Final null check
                        if (orderDescription == null) {
                            orderDescription = "No Description";
                            System.out.println("Order Description was null, set to fallback: " + orderDescription); // Debug
                        }

                        boolean saved = dbHandler.saveOrder(orderId,
                                new ArrayList<>(orderItems.values()),
                                total,
                                orderDescription,
                                paymentMethod);

                        if (saved) {
                            cashierOrder.showAlert("Order " + orderId + " saved successfully!");
                            orderItems.clear();
                            orderItemsBox.getChildren().clear();
                            cashierOrder.updateTotal();
                        } else {
                            cashierOrder.showAlert("Failed to save order! Check the console for details.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error in confirming order: " + e.getMessage());
                        e.printStackTrace();
                        cashierOrder.showAlert("Failed to save order! Error: " + e.getMessage());
                    }
                }
                break;
        }
    }

}
