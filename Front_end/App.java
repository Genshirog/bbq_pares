package Front_end;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class App {
    private StackPane root;
    public App(){
        root = new StackPane();
        //root.getStyleClass().add("root_form");
        //root.getChildren().add(app());  // Add VBox to the root
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 800, 600);
        //scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot(){
        return root;
    }
}
