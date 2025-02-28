package Back_end;

public class RoleView {
    private  String roleID;
    private  String role;
    private  String description;
    private  String shift;
    public RoleView(String roleID, String role, String description, String shift){
        this.roleID = roleID;
        this.role = role;
        this.description = description;
        this.shift = shift;
    }

    public String getRoleID(){return  roleID;}
    public String getRoleName(){return  role;}
    public String getDescription(){return  description;}
    public String getShift(){return  shift;}
}
