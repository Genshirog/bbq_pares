package Back_end;

public class MenuViews {
    private  String ProductID;
    private  String ProductName;
    private  String Price;
    public MenuViews(String ProductID, String ProductName, String Price){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
    }

    public String getProductID(){return  ProductID;}
    public String getProductName(){return  ProductName;}
    public String getPrice(){return  Price;}
}
