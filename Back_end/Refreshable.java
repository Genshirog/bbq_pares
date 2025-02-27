package Back_end;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import java.util.Map;

public interface Refreshable {
    public void form_btn();
    public void view_btn();
    public GridPane getForm();
    public ComboBox<String> getCombo();
    public Map<String, String> getFormData();
    public void clearForm();
}
