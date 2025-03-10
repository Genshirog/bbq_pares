package Back_end;

import Front_end.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
//This is the report-inventory-handler this is the sub-backend of the system when a button is press it goes here to be process this is the request server
public class ReportInventoryHandler implements EventHandler<ActionEvent> {
    private final String btn;
    private Inventory inventory;
    private Refreshable refreshable;
    public ReportInventoryHandler(String btn, VBox buttonContainer, Inventory inventory, VBox logoutContainer){
        this.btn = btn;
        this.inventory = inventory;
        this.refreshable= new ReportInventory(buttonContainer, inventory, logoutContainer);
    }

    @Override
    public void handle(ActionEvent event){
        switch (btn){
            case "AddRepo":
                System.out.println("Nigga");
                break;
            case "SearchRepo":
                System.out.println("Nigga2");
                break;
            case "EditRepo":
                System.out.println("Nigga3");
                break;
            case "RemRepo":
                System.out.println("Nigga4");
                break;
            case "ViewRepo":
                refreshable.view_btn();
                inventory.updateComboHolder(refreshable.getCombo());
                inventory.showReportTable();
                break;
            case "FormRepo":
                refreshable.form_btn();
                inventory.clearComboHolder();
                inventory.displayForm(refreshable.getForm());
                break;
            case "Back":
                inventory.originalComboHolder();
                inventory.buttonContainer();
                inventory.showInventoryTable();
                inventory.showLogoutButton();
                break;
            default:
                System.out.println("Wrong button");
                break;
        }
    }


}
