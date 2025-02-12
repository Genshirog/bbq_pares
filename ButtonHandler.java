import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


/*There is a lot of redundancy on this code thinking of inserting this fields to an interface for easy manipulation issue is CRUD buttons how work
 * if those methods not inside the buttonHandler
 */
public class ButtonHandler implements ActionListener{
    private Refreshable refreshable;
    private JPanel panel;
    private String name;
    private JPanel inputPanel;
    public ButtonHandler(JPanel panel, String name, JPanel inputPanel){
        this.panel = panel;
        this.name = name;
        this.inputPanel = inputPanel;
        if(panel instanceof Refreshable){
            this.refreshable = (Refreshable) panel;
        }else{
            this.refreshable = null;
        }
    }

    private void EcreateFields() {
        inputPanel.removeAll(); 
        inputPanel.setLayout(new GridLayout(6, 2, 50, 50));
        
        JLabel fname = new JLabel("First Name:");
        JTextField ftext = new JTextField();
        JLabel lname = new JLabel("Last Name:");
        JTextField ltext = new JTextField();
        JLabel role = new JLabel("Role:");
        JTextField rtext = new JTextField();
        JLabel email = new JLabel("Email:");
        JTextField etext = new JTextField();
        JLabel phone = new JLabel("Phone Number:");
        JTextField ptext = new JTextField();
        JLabel[] labels = {fname,lname,role,email,phone};
        JTextField[] texts = {ftext,ltext,rtext,etext,ptext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 5; i++){
            labels[i].setFont(new Font("Arial", Font.BOLD, 20));
            inputPanel.add(labels[i]);
            inputPanel.add(texts[i]);
        }
        inputPanel.add(save);
        inputPanel.add(clear);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection conn = Database.getConnection();

                    String getLastID = "SELECT EmployeeID FROM employee ORDER BY EmployeeID DESC LIMIT 1";
                    PreparedStatement getLastStmt = conn.prepareStatement(getLastID);
                    ResultSet rs = getLastStmt.executeQuery();

                    String newEmployeeID = "E001"; // Default for first employee
                    if (rs.next()) {
                        String lastID = rs.getString("EmployeeID"); // Example: "E005"
                        int lastNum = Integer.parseInt(lastID.substring(1)); // Extract number (5)
                        newEmployeeID = String.format("E%03d", lastNum + 1); // Increment and format ("E006")
                    }
                    rs.close();
                    getLastStmt.close();
                    String sql = "INSERT INTO employee(EmployeeID,first_name,last_name,JobRoleID,phone_number,email) VALUES(?,?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, newEmployeeID);
                    stmt.setString(2, ftext.getText());
                    stmt.setString(3, ltext.getText());
                    stmt.setString(4, rtext.getText());
                    stmt.setString(5, ptext.getText());
                    stmt.setString(6, etext.getText());
                    stmt.executeUpdate();
                    for(JTextField text : texts){
                        text.setText("");
                    }
                stmt.close();
                conn.close();
                if (refreshable != null) {
                    refreshable.refreshTable();  // Add this!
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(inputPanel, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for(JTextField text : texts){
                    text.setText("");
                }
            }
        });
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void EsearchFields() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridBagLayout());
    
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new BoxLayout(searchWrapper, BoxLayout.Y_AXIS));
    
        JComboBox<String> employeeDropDown = new JComboBox<>(new String[]{"Employee_ID", "Employee_Name", "Role"});
        JTextField input = new JTextField(15);
    
        searchWrapper.add(addPadding(EsearchHeader(employeeDropDown)));
        searchWrapper.add(addPadding(Esearchinputs(input)));
        searchWrapper.add(addPadding(Esearchbtn(employeeDropDown, input)));
    
        inputPanel.add(searchWrapper);
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    private JPanel EsearchHeader(JComboBox<String> employeeDropDown) {
        JPanel searchBox = new JPanel();
        JLabel label = new JLabel("Search By: ");
        employeeDropDown.setPreferredSize(new Dimension(200, 50));
    
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(label);
        searchBox.add(employeeDropDown);
        return searchBox;
    }
    
    private JPanel Esearchinputs(JTextField input) {
        JPanel searchBox = new JPanel();
        JLabel searchLabel = new JLabel("Search: ");
    
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(searchLabel);
        searchBox.add(input);
        return searchBox;
    }
    private JPanel Esearchbtn(JComboBox<String> employee, JTextField input){
        JPanel searchBox = new JPanel();
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String searchCriteria = (String)employee.getSelectedItem();
                String searchText = input.getText();
                try {
                    Connection conn = Database.getConnection();
                    String sql = "";
                    PreparedStatement stmt = null;
                    switch (searchCriteria) {
                        case "Employee_ID":
                            sql = "SELECT employeeID,employeeName,role,email,phone_number FROM employee_view WHERE employeeID LIKE ? ORDER BY employeeID ASC";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, "%"+searchText+"%");
                            break;
                        case "Employee_Name":
                            sql = "SELECT employeeID,employeeName,role,email,phone_number FROM employee_view WHERE employeeName LIKE ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, searchText+"%");
                            break;
                        case "Role":
                            sql = "SELECT employeeID,employeeName,role,email,phone_number FROM employee_view WHERE role LIKE ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, searchText+"%");
                            break;
                        default:
                            break;
                    }
                    ResultSet rs = stmt.executeQuery();
                    if (refreshable != null) {
                        refreshable.updateTable(rs);
                    }
                    conn.close();
                    input.setText("");
                } catch (Exception x) {
                    // TODO: handle exception
                }
            }
        });
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    refreshable.refreshTable();
                } catch (Exception x) {
                    // TODO: handle exception
                }
            }
        });
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(search);
        searchBox.add(reset);
        return searchBox;
    }

    private JPanel addPadding(JPanel panel) {
    JPanel paddedPanel = new JPanel(new BorderLayout());
    paddedPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    paddedPanel.add(panel, BorderLayout.CENTER);
    return paddedPanel;
    }   
    private void EupdateFields() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Add padding (top, left, bottom, right)
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel label = new JLabel("Enter the ID to Update: ");
        inputPanel.add(label,gbc);

        gbc.gridx =1;
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(100, 20));
        inputPanel.add(text,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Make button span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        JButton update = new JButton("Update");
        update.setPreferredSize(new Dimension(120, 20));
        inputPanel.add(update,gbc);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection conn = Database.getConnection();
                    String sql = "SELECT employeeID FROM employee_view WHERE employeeID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, text.getText());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        String id = rs.getString("employeeID");
                        if(id.equals(text.getText())){
                            EupdateRecord(text.getText());
                            break;
                        }
                    }
                    text.setText("");
                    conn.close();
                } catch (Exception x) {
                    // TODO: handle exception
                }

            }
        });
        gbc.gridx = 1;
        JButton clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(120, 20));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                text.setText("");
            }
        });
        inputPanel.add(clear,gbc);

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    public void EupdateRecord(String input){
        inputPanel.removeAll(); 
        inputPanel.setLayout(new GridLayout(6, 2, 50, 50));
        
        JLabel fname = new JLabel("First Name:");
        JTextField ftext = new JTextField();
        JLabel lname = new JLabel("Last Name:");
        JTextField ltext = new JTextField();
        JLabel role = new JLabel("Role:");
        JTextField rtext = new JTextField();
        JLabel email = new JLabel("Email:");
        JTextField etext = new JTextField();
        JLabel phone = new JLabel("Phone Number:");
        JTextField ptext = new JTextField();
        JLabel[] labels = {fname,lname,role,email,phone};
        JTextField[] texts = {ftext,ltext,rtext,etext,ptext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 5; i++){
            inputPanel.add(labels[i]);
            inputPanel.add(texts[i]);
        }
        inputPanel.add(save);
        inputPanel.add(clear);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection conn = Database.getConnection();
                    String sql = "UPDATE employee SET first_name = ?, last_name = ?, JobRoleID = ?, phone_number = ?, email = ? WHERE employeeID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, ftext.getText());
                    stmt.setString(2, ltext.getText());
                    stmt.setString(3, rtext.getText());
                    stmt.setString(4, ptext.getText());
                    stmt.setString(5, etext.getText());
                    stmt.setString(6, input);
                    stmt.executeUpdate();
                    for(JTextField text : texts){
                        text.setText("");
                    }
                stmt.close();
                conn.close();
                if (refreshable != null) {
                    refreshable.refreshTable();  // Add this!
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(inputPanel, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for(JTextField text : texts){
                    text.setText("");
                }
            }
        });
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void EdeleteFields() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10); // Add padding (top, left, bottom, right)
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel label = new JLabel("Enter the ID to Delete: ");
        inputPanel.add(label,gbc);

        gbc.gridx =1;
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(100, 20));
        inputPanel.add(text,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Make button span two columns
        gbc.anchor = GridBagConstraints.CENTER;
        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(120, 20));
        inputPanel.add(delete,gbc);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection conn = Database.getConnection();
                    String sql = "DELETE FROM employee WHERE employeeID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, text.getText());
                    stmt.executeUpdate();
                    conn.close();
                    if (refreshable != null) {
                        refreshable.refreshTable();  // Add this!
                    }
                } catch (Exception x) {
                    // TODO: handle exception
                }
            }
        });
        gbc.gridx = 1;
        JButton clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(120, 20));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                text.setText("");
            }
        });
        inputPanel.add(clear,gbc);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    //End of Employee
    //Start of Role
    private void RcreateFields() {
        inputPanel.removeAll(); 
        inputPanel.setLayout(new GridLayout(4, 2, 50, 50));
        
        JLabel rname = new JLabel("Role Name:");
        JTextField rtext = new JTextField();
        JLabel descript = new JLabel("Description:");
        JTextArea descriptArea = new JTextArea();
        descriptArea.setLineWrap(true);
        descriptArea.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(descriptArea);
        scrollDesc.setPreferredSize(new Dimension(80,100));
        scrollDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollDesc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDesc.setPreferredSize(new Dimension(200, 100));
        JLabel shift = new JLabel("Shift(Day/Night):");
        JTextField stext = new JTextField();
        JLabel[] labels = {rname,descript,shift};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 3; i++){
            labels[i].setFont(new Font("Arial", Font.BOLD, 20));
        }
        JTextField[] texts = {rtext,stext};
        inputPanel.add(rname);
        inputPanel.add(rtext);
        inputPanel.add(descript);
        inputPanel.add(scrollDesc);
        inputPanel.add(shift);
        inputPanel.add(stext);
        inputPanel.add(save);
        inputPanel.add(clear);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    Connection conn = Database.getConnection();

                    String getLastID = "SELECT JobRoleID FROM job_role ORDER BY JobRoleID DESC LIMIT 1";
                    PreparedStatement getLastStmt = conn.prepareStatement(getLastID);
                    ResultSet rs = getLastStmt.executeQuery();

                    String newJobRoleID = "R001";
                    if (rs.next()) {
                        String lastID = rs.getString("JobRoleID");
                        int lastNum = Integer.parseInt(lastID.substring(1));
                        newJobRoleID = String.format("R%03d", lastNum + 1);
                    }
                    rs.close();
                    getLastStmt.close();
                    String sql = "INSERT INTO job_role(JobRoleID,role_name,role_description,role_shift) VALUES(?,?,?,?,)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, newJobRoleID);
                    stmt.setString(2, rtext.getText());
                    stmt.setString(3, descriptArea.getText());
                    stmt.setString(4, shift.getText());
                    stmt.executeUpdate();
                    for(JTextField text : texts){
                        text.setText("");
                    }
                    descriptArea.setText("");
                stmt.close();
                conn.close();
                if (refreshable != null) {
                    refreshable.refreshTable();  // Add this!
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(inputPanel, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for(JTextField text : texts){
                    text.setText("");
                }
                descriptArea.setText("");
            }
        });
        inputPanel.revalidate();
        inputPanel.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        String action = source.getText();
        switch (this.name) {
            case "Employee":
                switch (action) {
                    case "Create Employee":
                            EcreateFields();
                        break;
                    case "Search Employee":
                            EsearchFields();
                        break;
                    case "Update Employee":
                            EupdateFields();
                        break;
                    case "Delete Employee":
                            EdeleteFields();
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                }
                break;
                case "Supplier":
                    switch (action) {
                    case "Create Supplier":
                        JOptionPane.showMessageDialog(panel, "Create Supplier Clicked");
                        break;
                    case "Search Supplier":
                        JOptionPane.showMessageDialog(panel, "Search Supplier Clicked");
                        break;
                    case "Update Supplier":
                        JOptionPane.showMessageDialog(panel, "Update Supplier Clicked");
                        break;
                    case "Delete Supplier":
                        JOptionPane.showMessageDialog(panel, "Delete Supplier Clicked");
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                }
                break;
                case "Customer":
                    switch (action) {
                    case "Create Customer":
                        JOptionPane.showMessageDialog(panel, "Create Customer Clicked");
                        break;
                    case "Search Customer":
                        JOptionPane.showMessageDialog(panel, "Search Customer Clicked");
                        break;
                    case "Update Customer":
                        JOptionPane.showMessageDialog(panel, "Update Customer Clicked");
                        break;
                    case "Delete Customer":
                        JOptionPane.showMessageDialog(panel, "Delete Customer Clicked");
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                }
                break;
                case "Role":
                    switch (action) {
                    case "Create Role":
                        RcreateFields();
                        break;
                    case "Search Role":
                        JOptionPane.showMessageDialog(panel, "Search Role Clicked");
                        break;
                    case "Update Role":
                        JOptionPane.showMessageDialog(panel, "Update Role Clicked");
                        break;
                    case "Delete Role":
                        JOptionPane.showMessageDialog(panel, "Delete Role Clicked");
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                }
                break;
                case "Menu":
                    switch (action) {
                    case "Create Menu":
                        JOptionPane.showMessageDialog(panel, "Create Menu Clicked");
                        break;
                    case "Search Menu":
                        JOptionPane.showMessageDialog(panel, "Search Menu Clicked");
                        break;
                    case "Update Menu":
                        JOptionPane.showMessageDialog(panel, "Update Menu Clicked");
                        break;
                    case "Delete Menu":
                        JOptionPane.showMessageDialog(panel, "Delete Menu Clicked");
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                }
            default:
                break;
        }
        
    }

}
