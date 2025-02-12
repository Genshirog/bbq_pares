import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Display extends JFrame implements ActionListener{
    private double width;
    private double height;
    private double percentageHeight;
    private double percentageWidth;
    private JButton employee, supplier, customer, menu, role;
    private final int fixedX = 0;
    private final int fixedY = 0;


    //Welcome to inventorySystem version 3
    //Type the hours you put on this before crying: 5hrs

    Display(){
        screenSize();
        this.setSize((int)getScreenWidth(),(int)getScreenHeight());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Prototype.v1");
        this.setResizable(true);
        this.setLayout(null);
        this.setLocation(fixedX,fixedY);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e){
                setLocation(fixedX,fixedY);
            }
        });
        
        employee = new JButton("Employee");
        employee.addActionListener(this);

        supplier = new JButton("Supplier");
        supplier.addActionListener(this);

        customer = new JButton("Customer");
        customer.addActionListener(this);

        menu = new JButton("Menu");
        menu.addActionListener(this);

        role = new JButton("Role");
        role.addActionListener(this);

        this.add(titleBox());
        this.add(navbar());
    }

    private void screenSize(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.getWidth();
        this.height = screenSize.getHeight();
    }

    private double getScreenWidth(){
        return this.width;
    }
    private double getScreenHeight(){
        return this.height;
    }

    private void setPercentScreenHeight(double x){
        this.percentageHeight = getScreenHeight() * (x/100);
    }

    private double getPercentScreenHeight(){
        return this.percentageHeight;
    }
    private void setPercentScreenWidth(double x){
        this.percentageWidth = getScreenWidth() * (x/100);
    }

    private double getPercentScreenWidth(){
        return this.percentageWidth;
    }
    
    private JPanel titleBox(){
        setPercentScreenHeight(10);
        int titleBoxHeight = (int)getPercentScreenHeight();
        int titleBoxWidth = (int)getScreenWidth();
        JPanel titleBox = new JPanel();
        titleBox.setBounds(0,0,titleBoxWidth, titleBoxHeight);
        titleBox.setBackground(getBackground().RED);
        titleBox.add(titleContent());
        titleBox.setLayout(null);
        return titleBox;
    }

    private JPanel titleContent(){
        setPercentScreenHeight(10);
        setPercentScreenWidth(50);
        int titleContentWidth = (int)getPercentScreenWidth();
        int titleContentHeight = (int)getPercentScreenHeight();
        
        JPanel titleContent = new JPanel();
        titleContent.setBounds((int)(getScreenWidth() * 0.25),0,titleContentWidth,titleContentHeight);
        titleContent.setBackground(getBackground().blue);
        
        titleContent.add(title(titleContentWidth,titleContentHeight));
        titleContent.setLayout(null);
        return titleContent;
    }

    private JLabel title(int titleContentWidth, int titleContentHeight){
        JLabel title = new JLabel("PROTOTYPE",SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setBounds(0,0,titleContentWidth,titleContentHeight);
        return title;
    }

    private JPanel navbar(){
        setPercentScreenHeight(10);
        int navY = (int)getPercentScreenHeight();
        JPanel navbar = new JPanel();
        navbar.setBounds((int)(getScreenWidth() * 0.10), navY + 30, (int)(getScreenWidth() * 0.80), (navY - 10));
        JButton[] buttons = {menu,employee,supplier,customer,role};
        for(JButton button:buttons){
            navbar.add(button);
        }
        navbar.setLayout(new GridLayout(1,5, 20, 20));
        return navbar;  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.getContentPane().removeAll();
            this.add(titleBox());
            this.add(navbar());
            if(e.getSource() == employee){
                Employee employeePanel = new Employee(getScreenWidth(), getScreenHeight());
                this.add(employeePanel);
            }else if(e.getSource() == supplier){
                Supplier supplierPanel = new Supplier(getScreenWidth(), getScreenHeight());
                this.add(supplierPanel);
            }else if(e.getSource() == customer){
                Customer customerPanel = new Customer(getScreenWidth(), getScreenHeight());
                this.add(customerPanel);
            }else if(e.getSource() == menu){
                Menu menuPanel = new Menu(getScreenWidth(), getScreenHeight());
                this.add(menuPanel);
            }else if(e.getSource() == role){
                Role rolePanel = new Role(getScreenWidth(),getScreenHeight());
                this.add(rolePanel);
            }
            this.revalidate();
            this.repaint();
        } catch (Exception x) {
            System.out.println(x.getMessage());
        }
    }

    
}
