package Back_end;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public interface Refreshable {
    public void form_btn();
    public void view_btn();
    public GridPane getForm();
    public ComboBox<String> getCombo();
}
