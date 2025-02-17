import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Customer extends JPanel implements Refreshable{
    JPanel inputPanel;
    JTable table;
    DefaultTableModel tableModel;
    Customer(double screenWidth , double screenHeight){
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
        Buttons button = new Buttons("Customer");
        ButtonHandler handler = new ButtonHandler(this, "Customer");
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

        String[] col = {"CustomerID", "Name", "Service"};
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
            String sql = "SELECT * FROM customer_view";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("CustomerID"),
                    rs.getString("Name"),
                    rs.getString("Service"),
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
                rs.getString("CustomerID"),
                rs.getString("Name"),
                rs.getString("Service"),
            };
            tableModel.addRow(row);
        }
    }
    
    @Override
    public void createFields() throws Exception{
        inputPanel.removeAll(); 
        inputPanel.setLayout(new GridLayout(6, 2, 50, 50));
        
        JLabel fname = new JLabel("First Name:");
        JTextField ftext = new JTextField();
        JLabel lname = new JLabel("Last Name:");
        JTextField ltext = new JTextField();
        JLabel mname = new JLabel("Middle Name:");
        JTextField mtext = new JTextField();
        JLabel service = new JLabel("Service Type:");
        JTextField stext = new JTextField();
        JLabel[] labels = {fname,lname,mname,service};
        JTextField[] texts = {ftext,ltext,mtext,stext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 4; i++){
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

                    String getLastID = "SELECT CustomerID FROM customer ORDER BY CustomerID DESC LIMIT 1";
                    PreparedStatement getLastStmt = conn.prepareStatement(getLastID);
                    ResultSet rs = getLastStmt.executeQuery();

                    String newCustomerID = "C001";
                    if (rs.next()) {
                        String lastID = rs.getString("CustomerID");
                        int lastNum = Integer.parseInt(lastID.substring(1));
                        newCustomerID = String.format("C%03d", lastNum + 1);
                    }
                    rs.close();
                    getLastStmt.close();
                    String sql = "INSERT INTO customer(CustomerID,first_name,last_name,middle_name,service_type) VALUES(?,?,?,?,?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, newCustomerID);
                    stmt.setString(2, ftext.getText());
                    stmt.setString(3, ltext.getText());
                    stmt.setString(4, mtext.getText());
                    stmt.setString(5, stext.getText());
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
    
        JComboBox<String> customerDropDown = new JComboBox<>(new String[]{"customer_ID", "customer_Name", "service_type"});
        JTextField input = new JTextField(15);
    
        searchWrapper.add(addPadding(EsearchHeader(customerDropDown)));
        searchWrapper.add(addPadding(Esearchinputs(input)));
        searchWrapper.add(addPadding(Esearchbtn(customerDropDown, input)));
    
        inputPanel.add(searchWrapper);
        inputPanel.revalidate();
        inputPanel.repaint();
    }
    
    private JPanel EsearchHeader(JComboBox<String> customerDropDown) {
        JPanel searchBox = new JPanel();
        JLabel label = new JLabel("Search By: ");
        customerDropDown.setPreferredSize(new Dimension(200, 50));
    
        searchBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        searchBox.add(label);
        searchBox.add(customerDropDown);
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
    private JPanel Esearchbtn(JComboBox<String> customer, JTextField input){
        JPanel searchBox = new JPanel();
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String searchCriteria = (String)customer.getSelectedItem();
                String searchText = input.getText();
                try {
                    Connection conn = Database.getConnection();
                    String sql = "";
                    PreparedStatement stmt = null;
                    switch (searchCriteria) {
                        case "customer_ID":
                            sql = "SELECT CustomerID,Name,Service FROM customer_view WHERE CustomerID LIKE ? ORDER BY CustomerID ASC";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, "%"+searchText+"%");
                            break;
                        case "customer_Name":
                            sql = "SELECT CustomerID,Name,Service FROM customer_view WHERE Name LIKE ?";
                            stmt = conn.prepareStatement(sql);
                            stmt.setString(1, searchText+"%");
                            break;
                        case "service_type":
                            sql = "SELECT CustomerID,Name,Service FROM customer_view WHERE LIKE ?";
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
                    String sql = "SELECT CustomerID FROM customer_view WHERE CustomerID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, text.getText());
                    ResultSet rs = stmt.executeQuery();

                    while(rs.next()){
                        String id = rs.getString("CustomerID");
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
        JLabel mname= new JLabel("Middle Name");
        JTextField mtext = new JTextField();
        JLabel service = new JLabel("Service:");
        JTextField stext = new JTextField();
        JLabel[] labels = {fname,lname,mname,service};
        JTextField[] texts = {ftext,ltext,mtext,stext};
        JButton save = new JButton("Save");
        JButton clear = new JButton("Clear");
        for(int i = 0; i < 4; i++){
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
                    String sql = "UPDATE customer SET first_name = ?, last_name = ?, middle_name = ?, service_type = ? WHERE CustomerID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, ftext.getText());
                    stmt.setString(2, ltext.getText());
                    stmt.setString(3, mtext.getText());
                    stmt.setString(4, stext.getText());
                    stmt.setString(5, input);
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
                    String sql = "DELETE FROM customer WHERE CustomerID = ?";
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
    //CustomerID, first_name, last_name, middle_name
}
