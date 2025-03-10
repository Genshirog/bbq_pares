package Back_end;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private double total;
    private HBox container;
    private Label itemLabel;
    private OrderItemSelectionListener selectionListener;

    // Interface for selection callback
    public interface OrderItemSelectionListener {
        void onOrderItemSelected(OrderItem orderItem);
    }

    public OrderItem(MenuItem menuItem, OrderItemSelectionListener listener) {
        this.menuItem = menuItem;
        this.quantity = 1;
        this.total = menuItem.getPrice();
        this.selectionListener = listener;

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
            if (selectionListener != null) {
                selectionListener.onOrderItemSelected(this);
            }
        });
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setSelected(boolean selected) {
        if (selected) {
            container.getStyleClass().remove("order-item");
            container.getStyleClass().add("selected-order-item");
        } else {
            container.getStyleClass().remove("selected-order-item");
            container.getStyleClass().add("order-item");
        }
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