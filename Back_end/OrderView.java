package Back_end;

public class OrderView {
    private String orderId;
    private String orderIdRef;
    private String itemName;
    private int quantity;
    private double price;
    private double totalPrice;

    public OrderView(String orderId, String itemName, int quantity, double price, double totalPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return totalPrice; }
    public String getOrderIdRef() { return orderIdRef; }
}