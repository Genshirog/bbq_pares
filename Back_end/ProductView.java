package Back_end;

public class ProductView {
    private  String ProductID;
    private  String ProductName;
    private  Double Price;
    private  Double Cost;
    private  String SupplierID;
    public ProductView(String ProductID, String ProductName, Double Price, Double Cost, String SupplierID){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Cost = Cost;
        this.SupplierID = SupplierID;
    }

    public String getProductID(){return  ProductID;}
    public String getProductName(){return  ProductName;}
    public Double getPrice(){return  Price;}
    public Double getCost(){return  Cost;}
    public String getSupplierID(){return  SupplierID;}
}
