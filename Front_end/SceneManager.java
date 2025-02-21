package Front_end;
import javafx.animation.FadeTransition;
import javafx.scene.*;
import javafx.stage.*;
import javafx.util.Duration;

public class SceneManager {
    private final Stage stage;
    private Node currentRoot;
    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void show_login(){
        Login login = new Login(this);
        Scene login_scene = login.getScene();
        this.currentRoot = login.getRoot();

        stage.setScene(login_scene);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.setMaximized(true);
        stage.setResizable(false);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), login.getRoot());
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        stage.show();
    }

    public void show_main_app(){
        App app = new App(this);
        app.getRoot().setOpacity(0);
        Scene app_scene = app.getScene();
        if (currentRoot != null) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(800), currentRoot);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(e -> {
                stage.setScene(app_scene);
                stage.setResizable(true);
                stage.setMaximized(false);
                stage.setMaximized(true);
                stage.setResizable(false);

                FadeTransition fadeIn = new FadeTransition(Duration.millis(800), app.getRoot());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

                currentRoot = app.getRoot();
            });

            fadeOut.play();
        } else {
            stage.setScene(app_scene);
            stage.setResizable(true);
            stage.setMaximized(false);
            stage.setMaximized(true);
            stage.setResizable(false);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(800), app.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            currentRoot = app.getRoot();
        }
    }

}
