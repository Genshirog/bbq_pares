package Back_end;

public class MenuItem {
    private String code;
    private String name;
    private double price;
    private String availability;
    private int quantityAvailable;

    public MenuItem(String code, String name, double price, Integer quantityAvailable, String availability) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.availability = availability;
    }
    public MenuItem(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getAvailability() { return availability; }
    public int getQuantityAvailable() { return quantityAvailable; }


}