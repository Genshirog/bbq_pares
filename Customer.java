import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;

public class Customer extends JPanel{
    JPanel inputPanel;
    Customer(double screenWidth , double screenHeight){
        int panelWidth = (int) (screenWidth * 0.90);  
        int panelHeight = (int) (screenHeight * 0.50); 

        int centerX = (int) ((screenWidth - panelWidth) / 2);  
        int centerY = (int) ((screenHeight - panelHeight) / 2);

        this.setBounds(centerX, centerY, panelWidth, panelHeight + 100);
        this.setBackground(Color.GRAY);
        this.setLayout(null);
        this.add(inputPanel(panelWidth, panelHeight));
        this.add(navbar(panelWidth, panelHeight));
    }

    private JPanel navbar(int panelWidth, int panelHeight){
        JPanel navbar = new JPanel();
        navbar.setBounds(0, panelHeight + 20, panelWidth, 100);
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navbar.setBackground(Color.GRAY);
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
        inputPanel.setBackground(Color.red);
        inputPanel.setLayout(null);
        return inputPanel;
    }

    //CustomerID, first_name, last_name, middle_name
}
