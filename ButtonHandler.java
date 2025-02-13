import java.awt.event.*;

import javax.swing.*;


/*There is a lot of redundancy on this code thinking of inserting this fields to an interface for easy manipulation issue is CRUD buttons how work
 * if those methods not inside the buttonHandler
 */
public class ButtonHandler implements ActionListener{
    private Refreshable refreshable;
    private JPanel panel;
    private String name;
    public ButtonHandler(JPanel panel, String name){
        this.panel = panel;
        this.name = name;
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
                try {
                    switch (action) {
                        case "Create Employee":
                                refreshable.createFields();
                                refreshable.refreshTable();
                            break;
                        case "Search Employee":
                                refreshable.searchFields();
                        case "Update Employee":
                                refreshable.updateFields();
                            
                            break;
                        case "Delete Employee":
                                refreshable.deleteFields();
                            break;
                        default:
                            JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                            break;
                    }
                } catch (Exception x) {
                    // TODO: handle exception
                }
                
                break;
            case "Supplier":
                switch (action) {
<<<<<<< HEAD
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
=======
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
                try {
>>>>>>> Genshirog
                    switch (action) {
                        case "Create Role":
                            refreshable.createFields();
                            refreshable.refreshTable();
                            break;
                        case "Search Role":
                            refreshable.searchFields();
                            break;
                        case "Update Role":
                            refreshable.updateFields();
                            break;
                        case "Delete Role":
                            refreshable.deleteFields();
                            break;
                        default:
                            JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                            break;
                    }
                } catch (Exception x) {
                    // TODO: handle exception
                }
<<<<<<< HEAD
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
=======
                
            break;
            case "Menu":
            try {
                switch (action) {
>>>>>>> Genshirog
                    case "Create Menu":
                        refreshable.createFields();
                        refreshable.refreshTable();
                        break;
                    case "Search Menu":
                        refreshable.searchFields();
                        break;
                    case "Update Menu":
                        refreshable.updateFields();
                        break;
                    case "Delete Menu":
                        refreshable.deleteFields();
                        break;
                    default:
                        JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                        break;
                
                    }
                } catch (Exception x) {
                    // TODO: handle exception
                }
        default:
            break;
        }
        
    }

}
