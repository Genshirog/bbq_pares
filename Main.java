import javax.swing.SwingUtilities;

public class Main{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
        Display display = new Display();
        display.setVisible(true);
        });
    }
}


//To ADD discount, 