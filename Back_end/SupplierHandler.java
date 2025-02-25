package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.sql.Ref;

public class SupplierHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Manager manager;
    private Refreshable refreshable;
    public SupplierHandler(String btn, VBox buttonContainer, Manager manager, VBox logoutContainer){
        this.btn = btn;
        this.manager = manager;
        this.refreshable= new Supplier(buttonContainer, manager, logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "supplier":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                manager.showBackButton();
                break;
            case "AddSup":
                System.out.println("Nigga");
                break;
            case "SearchSup":
                System.out.println("Nigga2");
                break;
            case "EditSup":
                System.out.println("Nigga3");
                break;
            case "RemSup":
                System.out.println("Nigga4");
                break;
            case "ViewSup":
                refreshable.view_btn();
                manager.updateComboHolder(refreshable.getCombo());
                manager.showSupplierTable();
                break;
            case "FormSup":
                refreshable.form_btn();
                manager.clearComboHolder();
                manager.displayForm(refreshable.getForm());
                break;
            case "Back":
                manager.originalComboHolder();
                manager.buttonContainer();
                manager.showSupplierTable();
                manager.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
