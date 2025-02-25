package Back_end;

import Front_end.SceneManager;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Duration;
public class LogoutHandler implements EventHandler<ActionEvent> {
    private final SceneManager scene;
    public LogoutHandler(SceneManager scene){
        this.scene = scene;
    }

    @Override
    public void handle(ActionEvent event){
        scene.show_login();
    }

}
