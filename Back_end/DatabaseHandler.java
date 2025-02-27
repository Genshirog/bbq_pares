package Back_end;

import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private Connection database;

    public  DatabaseHandler(){
    }

    public boolean addEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO employee (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, middleInitial);
            pstmt.setString(5, role);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
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
                        rs.getString("password")
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
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, middleInitial);
            pstmt.setString(4, role);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, password);
            pstmt.setString(7, email);
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
                rs.getString("password")
                );
                employees.add(employee);
            }
            conn.close();
        }catch (Exception e){
            e.getMessage();
        }
        return employees;
    }
    public boolean addRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean searchRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean editRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
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

    public boolean searchProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
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

    public boolean updateProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
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

    public boolean deleteProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
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

    public boolean addSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, mi);
            pstmt.setString(5, person);
            pstmt.setString(6, mail);
            pstmt.setString(7, num);
            pstmt.setString(8, add);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean searchSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, mi);
            pstmt.setString(5, person);
            pstmt.setString(6, mail);
            pstmt.setString(7, num);
            pstmt.setString(8, add);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, mi);
            pstmt.setString(5, person);
            pstmt.setString(6, mail);
            pstmt.setString(7, num);
            pstmt.setString(8, add);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, mi);
            pstmt.setString(5, person);
            pstmt.setString(6, mail);
            pstmt.setString(7, num);
            pstmt.setString(8, add);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Add other methods for updating, deleting, and retrieving employees
}