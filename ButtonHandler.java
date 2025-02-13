import java.awt.event.*;

import javax.swing.*;


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

    //End of Employee
    //Start of Role
    


    @Override
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        String action = source.getText();
        switch (this.name) {
            case "Employee":
                switch (action) {
                    case "Create Employee":
                        try {
                            refreshable.createFields();
                            refreshable.refreshTable();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        break;
                    case "Search Employee":
                        try {
                            refreshable.searchFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        break;
                    case "Update Employee":
                        try {
                            refreshable.updateFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        
                        break;
                    case "Delete Employee":
                        try {
                            refreshable.deleteFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
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
                        try {
                            refreshable.createFields();
                            refreshable.refreshTable();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        break;
                    case "Search Role":
                        try {
                            refreshable.searchFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        break;
                    case "Update Role":
                        try {
                            refreshable.updateFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
                        break;
                    case "Delete Role":
                        try {
                            refreshable.deleteFields();
                        } catch (Exception x) {
                            // TODO: handle exception
                        }
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
