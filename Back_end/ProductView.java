package Back_end;

public class ProductView {
    private  String ProductID;
    private  String ProductName;
    private  String Price;
    private  String Cost;
    private  String SupplierID;
    public ProductView(String ProductID, String ProductName, String Price, String Cost, String SupplierID){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Cost = Cost;
        this.SupplierID = SupplierID;
    }

    public String getProductID(){return  ProductID;}
    public String getProductName(){return  ProductName;}
    public String getPrice(){return  Price;}
    public String getCost(){return  Cost;}
    public String getSupplierID(){return  SupplierID;}
}
