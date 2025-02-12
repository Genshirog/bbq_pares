import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ButtonHandler implements ActionListener{
<<<<<<< HEAD
    private Employee employee;
=======
    private Refreshable refreshable;
>>>>>>> Genshirog
    private JPanel panel;
    private String name;
    private JPanel inputPanel;
    private String Searchinput;
    public ButtonHandler(JPanel panel, String name, JPanel inputPanel){
        this.panel = panel;
        this.name = name;
        this.inputPanel = inputPanel;
<<<<<<< HEAD
=======
        if(panel instanceof Refreshable){
            this.refreshable = (Refreshable) panel;
        }else{
            this.refreshable = null;
        }
>>>>>>> Genshirog
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
            inputPanel.add(labels[i]);
            inputPanel.add(texts[i]);
        }
        inputPanel.add(save);
        inputPanel.add(clear);
<<<<<<< HEAD
        try {
            Connection conn = Database.getConnection();
            String sql = "INSERT INTO employee(first_name,last_name,JobRoleID,phone_number,email) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ftext.getText());
            stmt.setString(2, ltext.getText());
            stmt.setString(3, rtext.getText());
            stmt.setString(4, ptext.getText());
            stmt.setString(5, etext.getText());
            stmt.executeUpdate();
            conn.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        

=======
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
>>>>>>> Genshirog
        inputPanel.revalidate();
        inputPanel.repaint();
        }

    private void EsearchFields() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridBagLayout()); 
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new BoxLayout(searchWrapper, BoxLayout.Y_AXIS));
        searchWrapper.add(addPadding(EsearchHeader()));
        searchWrapper.add(addPadding(Esearchinputs()));
        searchWrapper.add(addPadding(Esearchbtn()));

        inputPanel.add(searchWrapper);
        inputPanel.revalidate();
        inputPanel.repaint();
        }
    private JPanel EsearchHeader(){
        JPanel searchBox = new JPanel();
        searchBox.setBackground(Color.yellow);
        String[] searchable = {"Employee_ID", "Employee_Name", "Role"};
        JLabel label = new JLabel("Search By: ");
        JComboBox<String> employeeDropDown = new JComboBox<>(searchable);
        employeeDropDown.setPreferredSize(new Dimension(200,50));
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(label);
        searchBox.add(employeeDropDown);
        return searchBox;
    }
    private JPanel Esearchinputs(){
        JPanel searchBox = new JPanel();
        searchBox.setBackground(Color.blue);
        JLabel searchLabel = new JLabel("Search "+this.name+": ");   
        JTextField input = new JTextField(15);
        setInput(input.getText());
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(searchLabel);
        searchBox.add(input);
        return searchBox;
    }
    private JPanel Esearchbtn(){
        JPanel searchBox = new JPanel();
        searchBox.setBackground(Color.green);
        JButton search = new JButton("Search");
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(search);
        return searchBox;
    }

    private JPanel addPadding(JPanel panel) {
    JPanel paddedPanel = new JPanel(new BorderLayout());
    paddedPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    paddedPanel.add(panel, BorderLayout.CENTER);
    return paddedPanel;
}
    private void setInput(String input){
        this.Searchinput = input;
    }
    private String getInput(){
        return this.Searchinput;
    }
<<<<<<< HEAD
    
=======

>>>>>>> Genshirog
    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        String action = source.getText();
        switch (this.name) {
            case "Employee":
                switch (action) {
                    case "Create Employee":
<<<<<<< HEAD
                        try {
                            EcreateFields();
                            employee.refreshTable();
                            break;
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
=======
                            EcreateFields();
                        break;
>>>>>>> Genshirog
                    case "Search Employee":
                        EsearchFields();
                        break;
                    case "Update Employee":
                        JOptionPane.showMessageDialog(panel, "Update Employee Clicked");
                        break;
                    case "Delete Employee":
                        JOptionPane.showMessageDialog(panel, "Delete Employee Clicked");
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
<<<<<<< HEAD
=======
                break;
>>>>>>> Genshirog
                case "Role":
                    switch (action) {
                    case "Create Role":
                        JOptionPane.showMessageDialog(panel, "Create Role Clicked");
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
<<<<<<< HEAD
=======
                break;
>>>>>>> Genshirog
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
