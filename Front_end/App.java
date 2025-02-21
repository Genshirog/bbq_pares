package Front_end;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class App {
    private StackPane root;
    private SceneManager scene;
    public App(SceneManager scene){
        this.scene = scene;
        root = new StackPane();
        root.getStyleClass().add("root_form");
        root.setOpacity(0);
        //root.getChildren().add(app());  // Add VBox to the root
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot(){
        return root;
    }
}
