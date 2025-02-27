package Back_end;

public class EmployeeViews {
    private  String employeeID;
    private  String employeeName;
    private  String role;
    private  String email;
    private  String number;
    private  String password;
    public EmployeeViews(String employeeID, String employeeName, String role, String email, String number, String password){
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.role = role;
        this.email = email;
        this.number = number;
        this.password = password;
    }

    public String getEmployeeID(){return  employeeID;}
    public String getEmployeeName(){return  employeeName;}
    public String getEmployeeRole(){return  role;}
    public String getEmployeeEmail(){return  email;}
    public String getNumber(){return  number;}
    public String getPassword(){return  password;}
}
