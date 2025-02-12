import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.*;

<<<<<<< HEAD
public class Employee extends JPanel{
=======
public class Employee extends JPanel implements Refreshable{
>>>>>>> Genshirog
    JPanel inputPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    Employee(double screenWidth , double screenHeight){
        int panelWidth = (int) (screenWidth * 0.90);  
        int panelHeight = (int) (screenHeight * 0.50); 

        int centerX = (int) ((screenWidth - panelWidth) / 2);  
        int centerY = (int) ((screenHeight - panelHeight) / 2);

        this.setBounds(centerX, centerY, panelWidth, panelHeight + 100);
        this.setBackground(Color.PINK);
        this.setLayout(null);
        this.add(inputPanels(panelWidth, panelHeight));
        this.add(tablePanel(panelWidth, panelHeight));
<<<<<<< HEAD
        this.add(navbar(panelWidth, panelHeight));
=======
        this.add(navbar(panelWidth, panelHeight));  

        try {
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading table data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
>>>>>>> Genshirog
    }

    private JPanel navbar(int panelWidth, int panelHeight){
        JPanel navbar = new JPanel();
        navbar.setBounds(0, panelHeight + 20, panelWidth, 100);
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navbar.setBackground(Color.pink);
        Buttons button = new Buttons("Employee");
        ButtonHandler handler = new ButtonHandler(this, "Employee", this.inputPanel);
        JButton createbtn = button.createBtn();
        JButton searchbtn = button.searchBtn();
        JButton updatebtn = button.updateBtn();
        JButton deletebtn = button.deleteBtn();
        
        createbtn.addActionListener(handler);
        searchbtn.addActionListener(handler);
        updatebtn.addActionListener(handler);
        deletebtn.addActionListener(handler);

        navbar.add(createbtn);
        navbar.add(searchbtn);
        navbar.add(updatebtn);
        navbar.add(deletebtn);
        return navbar;
    }

    public JPanel inputPanels(int panelWidth, int panelHeight){
        inputPanel = new JPanel();
        inputPanel.setBounds(0, 0, (int)(panelWidth * 0.30), panelHeight+20);
        inputPanel.setBackground(Color.red);
        return inputPanel;
    }

    private JPanel tablePanel(int panelWidth, int panelHeight) {
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds((int) (panelWidth * 0.32), 0, (int) (panelWidth * 0.68), panelHeight);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());

        String[] col = {"EmployeeID", "Employee Name", "Role", "Email", "Phone Number"};
<<<<<<< HEAD
        table = new JTable(new DefaultTableModel(new Object[][] {}, col));
        JScrollPane scroll = new JScrollPane(table);

=======
        tableModel = new DefaultTableModel(col,0){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
>>>>>>> Genshirog
        tablePanel.add(scroll, BorderLayout.CENTER);
        return tablePanel;
    }
    
<<<<<<< HEAD
=======
    @Override
>>>>>>> Genshirog
    public void refreshTable() throws Exception {
        tableModel.setRowCount(0); // Clear existing table data
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM employee_view";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
<<<<<<< HEAD
                    rs.getInt("EmployeeID"),
                    rs.getString("Employee Name"),
                    rs.getInt("Role"),
                    rs.getString("Email"),
                    rs.getString("Phone Number")
=======
                    rs.getString("EmployeeID"),
                    rs.getString("EmployeeName"),
                    rs.getString("Role"),
                    rs.getString("Email"),
                    rs.getString("Phone_Number")
>>>>>>> Genshirog
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //EmployeeID, first_name, last_name, email, phone_number, job_roleID
}
