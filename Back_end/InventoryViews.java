package Back_end;

public class InventoryViews {
    private  String InventoryID;
    private  String Item_Name;
    private  String SupplierName;
    private  String Stock;
    private  String Date;
    private  String Availability;
    public InventoryViews(String InventoryID, String Item_Name, String SupplierName, String Stock, String Date, String Availability){
        this.InventoryID = InventoryID;
        this.Item_Name = Item_Name;
        this.SupplierName = SupplierName;
        this.Stock = Stock;
        this.Date = Date;
        this.Availability = Availability;
    }

    public String getInventoryID(){return  InventoryID;}
    public String getItemName(){return  Item_Name;}
    public String getSupplierName(){return  SupplierName;}
    public String getStock(){return  Stock;}
    public String getDate(){return  Date;}
    public String getAvailability(){return  Availability;}
}
