import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Supplier extends JPanel implements Refreshable{
    JPanel inputPanel;
    JTable table;
    DefaultTableModel tableModel;
    Supplier(double screenWidth , double screenHeight){
        int panelWidth = (int) (screenWidth * 0.90);  
        int panelHeight = (int) (screenHeight * 0.50); 

        int centerX = (int) ((screenWidth - panelWidth) / 2);  
        int centerY = (int) ((screenHeight - panelHeight) / 2); 

        this.setBounds(centerX, centerY, panelWidth, panelHeight + 100);
        this.setLayout(null);
        this.add(inputPanel(panelWidth, panelHeight));
        this.add(tablePanel(panelWidth, panelHeight));
        this.add(navbar(panelWidth, panelHeight));
        
        try {
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading table data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel navbar(int panelWidth, int panelHeight){
        JPanel navbar = new JPanel();
        navbar.setBounds(0, panelHeight + 20, panelWidth, 100);
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        Buttons button = new Buttons("Supplier");
        ButtonHandler handler = new ButtonHandler(this, "Supplier");
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
        inputPanel.setLayout(null);
        try {
            createFields();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return inputPanel;
    }

    private JPanel tablePanel(int panelWidth, int panelHeight) {
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds((int) (panelWidth * 0.32), 0, (int) (panelWidth * 0.68), panelHeight);
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setLayout(new BorderLayout());

        String[] col = {"SupplierID", "Supplier Name", "Contact Person", "Address" ,"Email", "Phone Number"};
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
            String sql = "SELECT * FROM supplier_view";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("SupplierID"),
                    rs.getString("SupplierName"),
                    rs.getString("Contact_Person"),
                    rs.getString("Address"),
                    rs.getString("Email"),
                    rs.getString("Phone_Number")
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
                rs.getString("SupplierID"),
                rs.getString("SupplierName"),
                rs.getString("Contact_Person"),
                rs.getString("Address"),
                rs.getString("Email"),
                rs.getString("Phone_Number")
            };
            tableModel.addRow(row);
        }
    }
    
    @Override
    public void createFields() throws Exception{
        inputPanel.removeAll(); 
        inputPanel.setLayout(new GridLayout(7, 2, 50, 50));
        
        JLabel fname = new JLabel("First Name:");
        JTextField ftext = new JTextField();
        JLabel lname = new JLabel("Last Name:");
        JTextField ltext = new JTextField();
        JLabel cp = new JLabel("Contact Person:");
        JTextField ctext = new JTextField();
        JLabel add = new JLabel("Address:");
        JTextField atext = new JTextField();
        JLabel email = new JLabel("Email:");
        JTextField etext = new JTextField();
        JLabel phone = new JLabel("Phone Number:");
        JTextField ptext = new JTextField();
        JLabel[] labels = {fname,lname,cp,add,email,phone};
        JTextField[] texts = {ftext,ltext,ctext,atext,etext,ptext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 6; i++){
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

                    String getLastID = "SELECT SupplierID FROM supplier ORDER BY SupplierID DESC LIMIT 1";
                    PreparedStatement getLastStmt = conn.prepareStatement(getLastID);
                    ResultSet rs = getLastStmt.executeQuery();

                    String newSupplierID = "S001"; // Default for first supplier
                    if (rs.next()) {
                        String lastID = rs.getString("SupplierID"); // Example: "E005"
                        int lastNum = Integer.parseInt(lastID.substring(1)); // Extract number (5)
                        newSupplierID = String.format("S%03d", lastNum + 1); // Increment and format ("E006")
                    }
                    rs.close();
                    getLastStmt.close();
                    String sql = "INSERT INTO supplier(SupplierID,first_name,last_name,contact_person,address,phone_number,email) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, newSupplierID);
                    stmt.setString(2, ftext.getText());
                    stmt.setString(3, ltext.getText());
                    stmt.setString(4, ctext.getText());
                    stmt.setString(5, atext.getText());
                    stmt.setString(6, ptext.getText());
                    stmt.setString(7, etext.getText());
                    stmt.executeUpdate();
                    for(JTextField text : texts){
                        text.setText("");
                    }
                stmt.close();
                conn.close();
                refreshTable();
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

    @Override
    public void searchFields() {
        inputPanel.removeAll();
        inputPanel.setLayout(new GridBagLayout());
    
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new BoxLayout(searchWrapper, BoxLayout.Y_AXIS));
    
        JComboBox<String> supplierDropDown = new JComboBox<>(new String[]{"supplier_ID", "supplier_Name", "contact_person"});
        JTextField input = new JTextField(15);
    
        searchWrapper.add(addPadding(EsearchHeader(supplierDropDown)));
        searchWrapper.add(addPadding(Esearchinputs(input)));
        searchWrapper.add(addPadding(Esearchbtn(supplierDropDown, input)));
    
        inputPanel.add(searchWrapper);
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    private JPanel EsearchHeader(JComboBox<String> supplierDropDown) {
        JPanel searchBox = new JPanel();
        JLabel label = new JLabel("Search By: ");
        supplierDropDown.setPreferredSize(new Dimension(200, 50));
    
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(label);
        searchBox.add(supplierDropDown);
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
    private JPanel Esearchbtn(JComboBox<String> supplier, JTextField input){
        JPanel searchBox = new JPanel();
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String searchCriteria = (String)supplier.getSelectedItem();
                String searchText = input.getText();
                try {
                    Connection conn = Database.getConnection();
                    String sql = "";
                    PreparedStatement stmt = null;
                    switch (searchCriteria) {
                        case "supplier_ID":
                            sql = "SELECT SupplierID,SupplierName,contact_person,address,email,phone_number FROM supplier_view WHERE SupplierID LIKE ? ORDER BY SupplierID ASC";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, "%"+searchText+"%");
                            break;
                        case "supplier_Name":
                            sql = "SELECT SupplierID,SupplierName,contact_person,address,email,phone_number FROM supplier_view WHERE SupplierName LIKE ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, searchText+"%");
                            break;
                        case "contact_person":
                            sql = "SELECT SupplierID,SupplierName,contact_person,address,email,phone_number FROM supplier_view WHERE contact_person LIKE ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, searchText+"%");
                            break;
                        default:
                            break;
                    }
                    ResultSet rs = stmt.executeQuery();
                        updateTable(rs);
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
                    refreshTable();
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

    @Override
    public void updateFields() {
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
                    String sql = "SELECT SupplierID FROM supplier_view WHERE SupplierID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, text.getText());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        String id = rs.getString("SupplierID");
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
        inputPanel.setLayout(new GridLayout(7, 2, 50, 50));
        
        JLabel fname = new JLabel("First Name:");
        JTextField ftext = new JTextField();
        JLabel lname = new JLabel("Last Name:");
        JTextField ltext = new JTextField();
        JLabel cp = new JLabel("Contact Person:");
        JTextField ctext = new JTextField();
        JLabel add = new JLabel("Address:");
        JTextField atext = new JTextField();
        JLabel email = new JLabel("Email:");
        JTextField etext = new JTextField();
        JLabel phone = new JLabel("Phone Number:");
        JTextField ptext = new JTextField();
        JLabel[] labels = {fname,lname,cp,add,email,phone};
        JTextField[] texts = {ftext,ltext,ctext,atext,etext,ptext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 6; i++){
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
                    String sql = "UPDATE supplier SET first_name = ?, last_name = ?, contact_person = ?, address = ?,phone_number = ?, email = ? WHERE SupplierID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, ftext.getText());
                    stmt.setString(2, ltext.getText());
                    stmt.setString(3, ctext.getText());
                    stmt.setString(4, atext.getText());
                    stmt.setString(5, ptext.getText());
                    stmt.setString(6, etext.getText());
                    stmt.setString(7, input);
                    stmt.executeUpdate();
                    for(JTextField text : texts){
                        text.setText("");
                    }
                stmt.close();
                conn.close();
                    refreshTable();  // Add this!
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

    @Override
    public void deleteFields() {
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
                    String sql = "DELETE FROM supplier WHERE SupplierID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, text.getText());
                    stmt.executeUpdate();
                    conn.close();
                        refreshTable();  // Add this!
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
    //SupplierID, SupplierName(first name, last name), address, email, phone_number, supplierItems
}
