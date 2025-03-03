package Back_end;

import Front_end.CashierOrder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import java.util.Map;

public class CashierOrderHandler implements EventHandler<ActionEvent> {
    private OrderItem selectedOrderItem;
    private CashierOrder cashierOrder;
    private Map<String, OrderItem> orderItems;
    private VBox orderItemsBox;
    private String btn;
    public CashierOrderHandler(OrderItem selectedOrderItem, CashierOrder cashierOrder, Map<String, OrderItem> orderItems, VBox orderItemsBox, String btn) {
        this.selectedOrderItem = selectedOrderItem;
        this.cashierOrder = cashierOrder;
        this.orderItems = orderItems;
        this.orderItemsBox = orderItemsBox;
        this.btn = btn;
    }

    @Override
    public void handle(ActionEvent event) {
        switch (btn){
            case "cancel":
                System.out.println("Nigga1");
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
                System.out.println("Nigga5");
                break;
        }
    }
}
