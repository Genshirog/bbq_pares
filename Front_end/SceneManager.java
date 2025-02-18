package Front_end;
import javafx.scene.*;
import javafx.stage.*;

public class SceneManager {
    private final Stage stage;
    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void show_login(){
        Login login = new Login(this);
        Scene login_scene = login.getScene();  // Get Scene from the Login class
        stage.setScene(login_scene);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }

    public void show_main_app(){
        App app = new App();
        Scene app_scene = app.getScene();
        stage.setScene(app_scene);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.setResizable(false);
    }

}
