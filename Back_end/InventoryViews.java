package Back_end;

public class InventoryViews {
    private  String inventoryID;
    private  String itemName;
    private  String supplierName;
    private  String stock;
    private  String date;
    private  String availability;
    public InventoryViews(String inventoryID, String itemName, String supplierName, String stock, String date, String availability){
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.supplierName = supplierName;
        this.stock = stock;
        this.date = date;
        this.availability = availability;
    }

    public String getInventoryID(){return  inventoryID;}
    public String getItemName(){return  itemName;}
    public String getSupplierName(){return  supplierName;}
    public String getStock(){return  stock;}
    public String getDate(){return  date;}
    public String getAvailability(){return  availability;}
}
