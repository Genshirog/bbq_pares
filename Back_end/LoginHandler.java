package Back_end;

import Front_end.   SceneManager;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Duration;

import javax.swing.*;
import javax.xml.crypto.Data;

public class LoginHandler implements EventHandler<ActionEvent> {
    private final TextField username;
    private final PasswordField password;
    private final ComboBox<String> role;
    private final Label errormsg;
    private final SceneManager scene;
    public LoginHandler(TextField username, PasswordField password, ComboBox<String> role, Label errormsg, SceneManager scene){
        this.username = username;
        this.password = password;
        this.role = role;
        this.errormsg = errormsg;
        this.scene = scene;
    }

    @Override
    public void handle(ActionEvent event){
        String username = this.username.getText();
        String password = this.password.getText();
        String role = this.role.getValue();
        DatabaseHandler db = new DatabaseHandler();
        String key = db.loginKey(username,password,role);
        if(key.equals(role)){
            switch (role){
                case "Manager" -> this.scene.show_main_manager();
                case "Cashier" -> this.scene.show_main_cashierorder();
                case "Inventory Clerk" -> this.scene.show_main_inventory();
            }
            JOptionPane.showMessageDialog(null,"Log in Successfully");
            this.errormsg.setVisible(false);
        }else{
            shakeFields();
            this.username.setText("");
            this.password.setText("");
            this.role.setValue(null);
            this.errormsg.setText("Account Does not Exist");
            this.errormsg.setVisible(true);
        }

    }

    private void shakeFields() {
        shakeNode(username);
        shakeNode(password);
        if(role !=null) {
            shakeNode(role);
        }
    }

    private void shakeNode(Control node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), node);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

}
