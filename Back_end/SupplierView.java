package Back_end;

public class SupplierView {
    private  String SupplierID;
    private  String SupplierName;
    private  String Person;
    private  String SupplierMail;
    private  String SupplierNumber;
    private  String address;
    public SupplierView(String SupplierID, String SupplierName, String Person, String SupplierMail, String SupplierNumber, String address){
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.Person = Person;
        this.address = address;
        this.SupplierMail = SupplierMail;
        this.SupplierNumber = SupplierNumber;

    }

    public String getSupplierID(){return  SupplierID;}
    public String getSupplierName(){return  SupplierName;}
    public String getPerson(){return  Person;}
    public String getAddress(){return  address;}
    public String getSupplierMail(){return  SupplierMail;}
    public String getSupplierNumber(){return  SupplierNumber;}

}
