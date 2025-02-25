package Front_end;
import javafx.application.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            SceneManager sceneManager = new SceneManager(primaryStage);
            sceneManager.show_login();
            Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("BBQ_PARES");
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}