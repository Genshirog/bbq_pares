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
                            break;
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
            try{
                switch (action) {
                case "Create Supplier":
                    refreshable.createFields();
                    refreshable.refreshTable();
                    break;
                case "Search Supplier":
                    refreshable.searchFields();
                    break;
                case "Update Supplier":
                    refreshable.updateFields();
                    break;
                case "Delete Supplier":
                    refreshable.deleteFields();
                    break;
                default:
                    JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                    break;
            }
            }catch(Exception x){

            }
            break;
            case "Customer":
            try{
                switch (action) {
                case "Create Customer":
                    refreshable.createFields();
                    refreshable.refreshTable();
                    break;
                case "Search Customer":
                    refreshable.searchFields();
                    break;
                case "Update Customer":
                    refreshable.updateFields();
                    break;
                case "Delete Customer":
                    refreshable.deleteFields();
                    break;
                default:
                    JOptionPane.showMessageDialog(panel, "Unknown Action: " + action);
                    break;
            }
            }catch(Exception x){

            }
            break;
            case "Role":
                try {
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
                
            break;
            case "Menu":
            try {
                switch (action) {
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
