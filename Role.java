import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Role extends JPanel implements Refreshable{
    JPanel inputPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    Role(double screenWidth , double screenHeight){
        int panelWidth = (int) (screenWidth * 0.90);  
        int panelHeight = (int) (screenHeight * 0.50); 

        int centerX = (int) ((screenWidth - panelWidth) / 2);  
        int centerY = (int) ((screenHeight - panelHeight) / 2);

        this.setBounds(centerX, centerY, panelWidth, panelHeight + 100);
        this.setBackground(Color.GREEN);
        this.setLayout(null);
        this.add(inputPanel(panelWidth, panelHeight));
        this.add(navbar(panelWidth, panelHeight));
        this.add(tablePanel(panelWidth, panelHeight));

    }

    private JPanel navbar(int panelWidth, int panelHeight){
        JPanel navbar = new JPanel();
        navbar.setBounds(0, panelHeight + 20, panelWidth, 100);
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navbar.setBackground(Color.GREEN);
        Buttons button = new Buttons("Role");
        ButtonHandler handler = new ButtonHandler(this, "Role", inputPanel);
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

    private JPanel inputPanel(int panelWidth, int panelHeight){
        inputPanel = new JPanel();
        inputPanel.setBounds(0, 0, (int)(panelWidth * 0.30), panelHeight+20);
        inputPanel.setBackground(Color.red);
        inputPanel.setLayout(null);
        return inputPanel;
    }

    private JPanel tablePanel(int panelWidth, int panelHeight) {
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds((int) (panelWidth * 0.32), 0, (int) (panelWidth * 0.68), panelHeight);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());

        String[] col = {"RoleID", "Role", "Description", "Shift"};
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
        tablePanel.add(scroll, BorderLayout.CENTER);
        return tablePanel;
    }
    
    @Override
    public void refreshTable() throws Exception {
        tableModel.setRowCount(0); // Clear existing table data
        try {
            Connection conn = Database.getConnection();
            String sql = "SELECT * FROM job_role";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("JobRoleID"),
                    rs.getString("role_name"),
                    rs.getString("role_description"),
                    rs.getString("role_shift"),
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

    @Override
    public void updateTable(ResultSet rs) throws Exception{
        tableModel.setRowCount(0); // Clear existing data

        while (rs.next()) {
            Object[] row = {
                rs.getString("EmployeeID"),
                rs.getString("EmployeeName"),
                rs.getString("Role"),
                rs.getString("Email"),
                rs.getString("Phone_Number")
            };
            tableModel.addRow(row);
        }
    }

    //Job_RoleID, Role_Name, Role_Description
}
