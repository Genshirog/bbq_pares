package Back_end;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DatabaseHandler {
    private Connection database;

    public  DatabaseHandler(){
    }

    public boolean addEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO test (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";

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

    public boolean searchEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO test (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, middleInitial);
            pstmt.setString(5, role);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
            pstmt.setString(8, password);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO test (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, middleInitial);
            pstmt.setString(5, role);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
            pstmt.setString(8, password);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(String id, String firstName, String lastName, String middleInitial,
                               String role, String email, String phoneNumber, String password) {

        String sql = "INSERT INTO test (EmployeeID, first_name, last_name, middle_initial, JobRoleID, phone_number, email, password) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, middleInitial);
            pstmt.setString(5, role);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, email);
            pstmt.setString(8, password);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean searchRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean editRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteRoles(String id, String role, String description, String shift) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, role);
            pstmt.setString(3, description);
            pstmt.setString(4, shift);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, price);
            pstmt.setString(4, cost);
            pstmt.setString(5, supplier);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean searchProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, price);
            pstmt.setString(4, cost);
            pstmt.setString(5, supplier);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, price);
            pstmt.setString(4, cost);
            pstmt.setString(5, supplier);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteProducts(String id, String name, String price, String cost, String supplier) {
        String sql = "INSERT INTO job_role (JobRoleID, role_name, role_description, role_shift) VALUES  (?, ?, ?, ?)";

        try (Connection conn = database;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, price);
            pstmt.setString(4, cost);
            pstmt.setString(5, supplier);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean addSupplier(String id, String fname, String lname, String mi, String person, String mail, String num, String add) {
        String sql = "INSERT INTO supplier (supplierID, first_name, last_name, middle_initial,contact_person,phone_number,email,address) VALUES  (?, ?, ?, ?,?,?,?,?)";

        try (Connection conn = database;
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Add other methods for updating, deleting, and retrieving employees
}