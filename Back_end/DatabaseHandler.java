package Back_end;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
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
        String sql = "CALL insert_employee(?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id.toUpperCase());
                pstmt.setString(2, builder.capitalizeLetters(firstName));
                pstmt.setString(3, builder.capitalizeLetters(lastName));
                pstmt.setString(4, builder.capitalizeLetters(middleInitial));
                pstmt.setString(5, role.toUpperCase());
                pstmt.setString(6, builder.isValidEmail(email));
                pstmt.setString(7, builder.phone_formatter(phoneNumber));
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
                        !rs.getString("password").equals("No Password") ? encodePassword(rs.getString("password")) : rs.getString("password")
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

        String sql = "CALL update_employee(?,?,?,?,?,?,?,?)";

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
            case "EmployeeName" -> "DELETE FROM employee WHERE concat(last_name,', ', first_name,' ', middle_initial,'.') = ?";
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
                !rs.getString("password").equals("No Password") ? encodePassword(rs.getString("password")) : rs.getString("password")
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
        String sql = "CALL insert_role(?,?,?,?)";

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
        String sql = "CALL update_role(?,?,?,?)";

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
        String sql = "CALL insert_product(?,?,?,?,?)";

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
        String sql = "CALL update_products(?,?,?,?,?)";

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
        String sql = "CALL insert_supplier(?, ?, ?, ?,?,?,?,?)";

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
        String sql = "CALL update_supplier(?,?,?,?,?,?,?,?)";

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
        String sql = "CALL stock_in(?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int getQuantity = Integer.parseInt(quantity);
            if(getQuantity < 0){
                JOptionPane.showMessageDialog(null,"BOBO KABA!? Negative STOCK IN?");
            }else {
                pstmt.setString(1, id);
                pstmt.setInt(2, getQuantity);
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

//CashierOrder methods
public static ObservableList<MenuItem> getMenuItemsFromDatabase() {
    ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

    try {
        Connection conn = Database.getConnection();
        if (conn == null) {
            System.out.println("Database connection is null!");
            return menuItems;
        }
        System.out.println("Database connection established.");

        // Join products and inventory tables to get all required fields
        String sql = "SELECT DISTINCT p.ProductID AS code, p.ProductName AS name, p.Price AS price, " +
                "i.availability AS availability, i.stock_quantity AS quantityAvailable " +
                "FROM products p " +
                "LEFT JOIN (SELECT ProductID, stock_quantity, availability " +
                "           FROM inventory " +
                "           WHERE (ProductID, stock_date) IN " +
                "                 (SELECT ProductID, MAX(stock_date) " +
                "                  FROM inventory " +
                "                  GROUP BY ProductID)) i " +
                "ON p.ProductID = i.ProductID";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            String code = rs.getString("code");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String availability = rs.getString("availability");
            int quantityAvailable = rs.getInt("quantityAvailable");

            MenuItem menuItem = new MenuItem(code, name, price, quantityAvailable, availability);
            menuItems.add(menuItem);
            System.out.println("Added menu item: " + name + " (Code: " + code + ")");
        }

        System.out.println("Total rows fetched: " + rowCount);
        conn.close();
    } catch (Exception e) {
        System.out.println("Error fetching menu items: " + e.getMessage());
    }

    return menuItems;
}


//saving order
    private String getNextOrderItemIdBase() {
        String prefix = "OI";
        int nextNumber = 1;

        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT MAX(CAST(SUBSTRING(OrderItemID, 3) AS UNSIGNED)) AS maxNum FROM orderitems WHERE OrderItemID LIKE 'OI%'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getString("maxNum") != null) {
                nextNumber = rs.getInt("maxNum") + 1;
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Error getting next order item ID base: " + e.getMessage());
        }

        return prefix + String.format("%03d", nextNumber);
    }

    // Updated saveOrder method
    public boolean saveOrder(String orderId, List<OrderItem> orderItems, double total, String orderDescription, String paymentMethod) {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            if (conn == null) {
                throw new SQLException("Failed to establish database connection");
            }
            conn.setAutoCommit(false);

            String safeOrderDescription = (orderDescription != null && !orderDescription.trim().isEmpty())
                    ? orderDescription
                    : "No Description";
            System.out.println("Safe Order Description in saveOrder: " + safeOrderDescription);

            // Insert into orders table
            String orderSql = "INSERT INTO orders (OrderID, order_description) VALUES (?, ?)";
            try (PreparedStatement orderStmt = conn.prepareStatement(orderSql)) {
                orderStmt.setString(1, orderId);
                orderStmt.setString(2, safeOrderDescription);
                System.out.println("Executing SQL: " + orderSql + " with OrderID=" + orderId + ", order_description=" + safeOrderDescription);
                orderStmt.executeUpdate();
            }

            // Insert order items
            String itemSql = "INSERT INTO orderitems (OrderItemID, OrderID, item_name, item_quantity, item_price, total_price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement itemStmt = null;
            try {
                itemStmt = conn.prepareStatement(itemSql);
                String baseItemId = getNextOrderItemIdBase();
                int baseNumber = Integer.parseInt(baseItemId.substring(2));
                for (int i = 0; i < orderItems.size(); i++) {
                    OrderItem item = orderItems.get(i);
                    String itemId = "OI" + String.format("%03d", baseNumber + i);
                    itemStmt.setString(1, itemId);
                    itemStmt.setString(2, orderId);
                    itemStmt.setString(3, item.getMenuItem().getName());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.setDouble(5, item.getMenuItem().getPrice());
                    itemStmt.setDouble(6, item.getTotal());
                    itemStmt.executeUpdate();
                }
            } finally {
                if (itemStmt != null) itemStmt.close();
            }

            // Insert into receipt (remove this if the trigger should handle it)
            String receiptSql = "INSERT INTO receipt (ReceiptID, OrderID, price, payment_method, receipt_date, order_description) VALUES (?, ?, ?, ?, NOW(), ?)";
            try (PreparedStatement receiptStmt = conn.prepareStatement(receiptSql)) {
                receiptStmt.setString(1, "R" + orderId.substring(3));
                receiptStmt.setString(2, orderId);
                receiptStmt.setDouble(3, total);
                receiptStmt.setString(4, paymentMethod);
                receiptStmt.setString(5, safeOrderDescription);
                receiptStmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Order " + orderId + " saved successfully");
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Error saving order: " + e.getMessage());
            // Log table structures for debugging
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    System.out.println("Orders table structure:");
                    try (ResultSet rs = stmt.executeQuery("DESCRIBE orders")) {
                        while (rs.next()) {
                            System.out.println(rs.getString("Field") + " | " + rs.getString("Type") + " | " + rs.getString("Null") + " | " + rs.getString("Default"));
                        }
                    }
                    System.out.println("Receipt table structure:");
                    try (ResultSet rs = stmt.executeQuery("DESCRIBE receipt")) {
                        while (rs.next()) {
                            System.out.println(rs.getString("Field") + " | " + rs.getString("Type") + " | " + rs.getString("Null") + " | " + rs.getString("Default"));
                        }
                    }
                } catch (SQLException describeEx) {
                    System.err.println("Failed to describe tables: " + describeEx.getMessage());
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Failed to close connection: " + closeEx.getMessage());
                }
            }
        }
    }

    // order numbering, getting the last int then increment by 1
    public String getNextOrderId() {
        String prefix = "ORD";
        int nextNumber = 1; // Default starting value

        try {
            Connection conn = Database.getConnection();
            // Extract the numeric part after "ORD" and find the maximum value
            String sql = "SELECT MAX(CAST(SUBSTRING(OrderID, 4) AS UNSIGNED)) AS maxNum FROM orders WHERE OrderID LIKE 'ORD%'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getString("maxNum") != null) {
                nextNumber = rs.getInt("maxNum") + 1;
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("Error getting next order ID: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Create the new order ID with proper formatting
        return prefix + String.format("%03d", nextNumber);
    }
//added this to fix the number having too many or random
    public List<OrderItem> getOrderItemsByOrderId(String orderId) throws Exception {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.OrderItemID, oi.item_name, oi.item_quantity, oi.item_price, oi.total_price " +
                "FROM orderitems oi WHERE oi.OrderID = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("item_quantity");
                double price = rs.getDouble("item_price");
                double total = rs.getDouble("total_price");
                // Create a dummy MenuItem for compatibility with OrderItem constructor
                MenuItem menuItem = new MenuItem("", itemName, price); // Adjust constructor if needed
                OrderItem item = new OrderItem(menuItem, (OrderItem.OrderItemSelectionListener) null); // Pass null listener
                item.setQuantity(quantity);
                item.updateTotal(); // Ensure total matches fetched value (optional sync)
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching order items: " + e.getMessage());
        }
        return items;
    }

    // ADD THIS TO DatabaseHandler.java: Method to cancel an order
    public boolean cancelOrder(String orderId) throws Exception {
        Connection conn = null;
        try {
            conn = Database.getConnection();
            conn.setAutoCommit(false);

            // Delete from orderitems table
            String deleteItemsSql = "DELETE FROM orderitems WHERE OrderID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteItemsSql)) {
                stmt.setString(1, orderId);
                stmt.executeUpdate();
            }

            // Delete from orders table
            String deleteOrderSql = "DELETE FROM orders WHERE OrderID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteOrderSql)) {
                stmt.setString(1, orderId);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Error canceling order: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Failed to close connection: " + closeEx.getMessage());
                }
            }
        }
    }

}

