package Back_end;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private Connection database;
    private Formatter builder;
    public  DatabaseHandler(){
        this.builder = new Formatter();
    }

    public String encodePassword(String password) {
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        String encodedPassword = Base64.getEncoder().encodeToString(passwordBytes);
        return encodedPassword;
    }

    // To decode a Base64 password (if you need to)
    public String decodePassword(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        String decodedPassword = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedPassword;
    }

    public boolean addEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO employee (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id.toUpperCase());
            pstmt.setString(2, builder.capitalizeLetters(firstName));
            pstmt.setString(3, builder.capitalizeLetters(lastName));
            pstmt.setString(4, builder.capitalizeLetters(middleInitial));
            pstmt.setString(5, role.toUpperCase());
            pstmt.setString(6, builder.phone_formatter(phoneNumber));
            pstmt.setString(7, builder.isValidEmail(email));
            pstmt.setString(8, password);
            int rowsAffected = pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<EmployeeViews> searchEmployeeView(String id, String value){
        List<EmployeeViews> employees = new ArrayList<>();
        String sql = switch (value) {
            case "Employee ID" -> "SELECT * FROM employee_view WHERE employeeID LIKE ?";
            case "EmployeeName" -> "SELECT * FROM employee_view WHERE employeeName LIKE ?";
            case "Role" -> "SELECT * FROM employee_view WHERE JobRoleID LIKE ?";
            default -> "";
        };
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            switch (value) {
                case "Employee ID" -> stmt.setString(1, "%" + id + "%");
                case "EmployeeName", "Role" -> stmt.setString(1, id + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                EmployeeViews employee = new EmployeeViews(
                        rs.getString("EmployeeID"),
                        rs.getString("EmployeeName"),
                        rs.getString("JobRoleID"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        encodePassword(rs.getString("password"))
                );
                employees.add(employee);
            }
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public boolean updateEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "UPDATE employee SET first_name = ?, last_name = ?, middle_initial = ?, JobRoleID = ?, phone_number = ?, email = ?, password = ? WHERE EmployeeID = ?";

        try{Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, builder.capitalizeLetters(firstName));
            pstmt.setString(2, builder.capitalizeLetters(lastName));
            pstmt.setString(3, builder.capitalizeLetters(middleInitial));
            pstmt.setString(4, role);
            pstmt.setString(5, builder.isValidEmail(email));
            pstmt.setString(6, builder.phone_formatter(phoneNumber));
            pstmt.setString(7, password);
            pstmt.setString(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(String id, String value) {
        String sql = switch (value) {
            case "Employee ID" -> "DELETE FROM employee WHERE employeeID = ?";
            case "EmployeeName" -> "DELETE FROM employee WHERE employeeName = ?";
            case "Role" -> "DELETE FROM employee WHERE JobRoleID = ?";
            default -> "";
        };

        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //This will add all from the employeeView notice we are calling EmployeeViews on our sql it is to get the columns
    //rs.getString(matchColumNames) then storing them to the EmployeeViews now once it is stored
    //We add it to our List and since we are using <EmployeeViews> it will only get data from EmployeeViews
    //Go to table manager
    public List<EmployeeViews> getEmployeeView(){
        List<EmployeeViews> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee_view";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                EmployeeViews employee = new EmployeeViews(
                rs.getString("EmployeeID"),
                rs.getString("EmployeeName"),
                rs.getString("JobRoleID"),
                rs.getString("email"),
                rs.getString("phone_number"),
                encodePassword(rs.getString("password"))
                );
                employees.add(employee);
            }
            conn.close();
        }catch (Exception e){
            e.getMessage();
        }
        return employees;
    }

    public List<InventoryViews> getInventoryView(){
        List<InventoryViews> inventory = new ArrayList<>();
        String sql = "SELECT * FROM inventory_view";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                InventoryViews inventories = new InventoryViews(
                rs.getString("InventoryID"),
                rs.getString("ProductName"),
                rs.getString("SupplierName"),
                rs.getString("stock_quantity"),
                rs.getString("stock_date"),
                rs.getString("availability")
                );
                inventory.add(inventories);
            }
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return inventory;
    }

    public List<MenuViews> getMenuView(){
        List<MenuViews> menu = new ArrayList<>();
        String sql = "SELECT * FROM menu_view";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                MenuViews menus = new MenuViews(
                    rs.getString("productID"),
                    rs.getString("productName"),
                    rs.getDouble("price")
                );
                menu.add(menus);
            }
            conn.close();
        }catch (Exception e){

            System.out.println(e.getMessage());
        }
        return menu;
    }

    public List<RoleView> getRoleView(){
        List<RoleView> roles = new ArrayList<>();
        String sql = "SELECT * FROM job_role";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                RoleView role = new RoleView(
                rs.getString("JobRoleID"),
                rs.getString("role_name"),
                rs.getString("role_description"),
                rs.getString("role_shift")
                );
                roles.add(role);
            }
            conn.close();
        }catch (Exception e){
            e.getMessage();
        }
        return roles;
    }

    public List<ProductView> getProductView(){
        List<ProductView> products = new ArrayList<>();
        String sql = "SELECT * FROM product_view";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                ProductView product = new ProductView(
                rs.getString("productID"),
                rs.getString("productName"),
                rs.getDouble("price"),
                rs.getDouble("cost"),
                rs.getString("supplierID")
                );
                products.add(product);
            }
            conn.close();
        }catch (Exception e){
            e.getMessage();
        }
        return products;
    }

    public List<SupplierView> getSupplierView(){
        List<SupplierView> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM supplier_view";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                SupplierView supplier = new SupplierView(
                rs.getString("supplierID"),
                rs.getString("supplierName"),
                rs.getString("contact_person"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getString("phone_number")
                );
                suppliers.add(supplier);
            }
            conn.close();
        }catch (Exception e){
            e.getMessage();
        }
        return suppliers;
    }


    public boolean addRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, builder.capitalizeLetters(role));
            pstmt.setString(3, builder.capitalizeLetters(description));
            pstmt.setString(4, builder.isValidShift(builder.capitalizeHyphenLetters(shift)));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<RoleView> searchRolesView(String id, String value){
        List<RoleView> roles = new ArrayList<>();
        String sql = switch (value) {
            case "Role ID" -> "SELECT * FROM job_role WHERE JobRoleID LIKE ?";
            case "Role" -> "SELECT * FROM job_role WHERE role_name LIKE ?";
            case "Shift" -> "SELECT * FROM job_role WHERE shift LIKE ?";
            default -> "";
        };
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            switch (value) {
                case "Role ID" -> stmt.setString(1, "%" + id + "%");
                case "Role", "Shift" -> stmt.setString(1, id + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                RoleView roleView = new RoleView(
                        rs.getString("JobRoleID"),
                        rs.getString("role_name"),
                        rs.getString("role_description"),
                        rs.getString("role_shift")
                );
                roles.add(roleView);
            }
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return roles;
    }
    public boolean editRoles(String id, String role, String description, String shift) {
        String sql = "UPDATE job_role SET role_name = ?, role_description = ?, role_shift = ? WHERE JobRoleID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, builder.capitalizeLetters(role));
            pstmt.setString(2, builder.capitalizeLetters(description));
            pstmt.setString(3, builder.isValidShift(builder.capitalizeHyphenLetters(shift)));
            pstmt.setString(4, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteRoles(String id, String value) {
        String sql = switch (value) {
            case "Role ID" -> "DELETE FROM job_role WHERE JobRoleID = ?";
            case "Role" -> "DELETE FROM job_role WHERE role_name = ?";
            case "Shift" -> "DELETE FROM job_role WHERE role_shift = ?";
            default -> "";
        };

        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO products (ProductID, ProductName, Price, Cost, SupplierID) VALUES  (?, ?, ?, ?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, builder.capitalizeLetters(name));
            pstmt.setString(3, price);
            pstmt.setString(4, cost);
            pstmt.setString(5, supplier);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<ProductView> searchProductView(String id, String value){
        List<ProductView> roles = new ArrayList<>();
        String sql = switch (value) {
            case "Product ID" -> "SELECT * FROM product_view WHERE ProductID LIKE ?";
            case "Product Name" -> "SELECT * FROM product_view WHERE ProductName LIKE ?";
            case "Supplier" -> "SELECT * FROM product_view WHERE SupplierName LIKE ?";
            default -> "";
        };
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            switch (value) {
                case "Product ID" -> stmt.setString(1, "%" + id + "%");
                case "Product Name", "Supplier ID" -> stmt.setString(1, id + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                ProductView prodView = new ProductView(
                        rs.getString("productID"),
                        rs.getString("productName"),
                        rs.getDouble("price"),
                        rs.getDouble("cost"),
                        rs.getString("supplierID")
                );
                roles.add(prodView);
            }
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return roles;
    }

    public boolean updateProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "UPDATE products SET productName = ?, price = ?, cost = ?, supplierID = ? WHERE productID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, builder.capitalizeLetters(name));
            pstmt.setString(2, price);
            pstmt.setString(3, cost);
            pstmt.setString(4, supplier);
            pstmt.setString(5, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteProducts(String id, String value) {
        String sql = switch (value) {
            case "Product ID" -> "DELETE FROM products WHERE productID = ?";
            case "Product Name" -> "DELETE FROM products WHERE productName = ?";
            case "Supplier" -> "DELETE FROM products WHERE supplierID = ?";
            default -> "";
        };

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, builder.capitalizeLetters(fname));
            pstmt.setString(3, builder.capitalizeLetters(lname));
            pstmt.setString(4, builder.capitalizeLetters(mi));
            pstmt.setString(5, builder.capitalizeLetters(person));
            pstmt.setString(6, builder.isValidEmail(mail));
            pstmt.setString(7, builder.phone_formatter(num));
            pstmt.setString(8, builder.capitalizeLetters(add));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<SupplierView> searchSupplierView(String id, String value){
        List<SupplierView> suppliers = new ArrayList<>();
        String sql = switch (value) {
            case "Supplier ID" -> "SELECT * FROM supplier_view WHERE supplierID LIKE ?";
            case "Supplier Name" -> "SELECT * FROM supplier_view WHERE supplierName LIKE ?";
            case "Person" -> "SELECT * FROM supplier_view WHERE contact_person LIKE ?";
            default -> "";
        };
        try{
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            switch (value) {
                case "Supplier ID" -> stmt.setString(1, "%" + id + "%");
                case "Supplier Name", "Person" -> stmt.setString(1, id + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                SupplierView supplier = new SupplierView(
                        rs.getString("supplierID"),
                        rs.getString("supplierName"),
                        rs.getString("contact_person"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                suppliers.add(supplier);
            }
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return suppliers;
    }

    public boolean updateSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = " UPDATE supplier SET first_name = ?, last_name = ?, middle_initial = ?, contact_person = ?, phone_number = ?, email = ?, address = ? WHERE supplierID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, builder.capitalizeLetters(fname));
            pstmt.setString(2, builder.capitalizeLetters(lname));
            pstmt.setString(3, builder.capitalizeLetters(mi));
            pstmt.setString(4, builder.capitalizeLetters(person));
            pstmt.setString(5, builder.capitalizeLetters(add));
            pstmt.setString(6, builder.isValidEmail(mail));
            pstmt.setString(7, builder.phone_formatter(num));
            pstmt.setString(8, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteSupplier(String id, String value) {
        String sql = switch (value) {
            case "Supplier ID" -> "DELETE FROM supplier WHERE supplierID = ?";
            case "Supplier Name" -> "DELETE FROM supplier WHERE CONCAT(last_name,', ', first_name,' ',middle_initial,'.') = ?";
            case "Person" -> "DELETE FROM supplier WHERE contact_person = ?";
            default -> "";
        };

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean updateInventory(String id, String quantity){
        String sql = "UPDATE inventory SET stock_quantity = stock_quantity + ? WHERE inventoryID = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int getQuantity = Integer.parseInt(quantity);
            if(getQuantity < 0){
                JOptionPane.showMessageDialog(null,"BOBO KABA!? Negative STOCK IN?");
            }else {
                pstmt.setInt(1, getQuantity);
                pstmt.setString(2, id);
            }
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String loginKey(String username, String password, String value){
        try{
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM accounts WHERE username = ? AND password = ? AND JobRoleID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            stmt.setString(2,password);
            stmt.setString(3,value);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                // If a match is found, return the role
                return value;
            } else {
                // No match found, return empty string or some error indicator
                return "";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    // Add other methods for updating, deleting, and retrieving employees
}