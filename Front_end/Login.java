package Front_end;

import Back_end.LoginHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.animation.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.*;

public class Login {
    private final StackPane root;
    private final SceneManager scene;
    public Login(SceneManager sceneManager){
        this.scene = sceneManager;
        root = new StackPane();
        root.getStyleClass().add("root_form");
        root.getChildren().add(overlay());
        root.getChildren().add(logo_container());
        root.getChildren().addAll(rect(),Form());
        //Main VBox
        //root.getChildren().add(login(sceneManager));
    }

    public Scene getScene() {
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    public StackPane getRoot() {
        return root;
    }

    private Pane overlay(){
        Pane overlay = new Pane();
        overlay.getStyleClass().add("overlay");
        return overlay;
    }

    private HBox logo_container(){
        HBox logo_container = new HBox();
        logo_container.getStyleClass().add("container");
        StackPane.setAlignment(logo_container, Pos.TOP_CENTER);
        logo_container.getChildren().add(logo());
        return logo_container;
    }

    private Pane logo(){
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/logo.png")));
        imageView.getStyleClass().add("imageLogo");
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);

        Glow glow = new Glow(0.8);
        imageView.setEffect(glow);

        Timeline glowAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, e -> glow.setLevel(0.8)),
                new KeyFrame(Duration.seconds(1), e -> glow.setLevel(1.5)),
                new KeyFrame(Duration.seconds(2), e -> glow.setLevel(0.8))
        );
        glowAnimation.setCycleCount(Timeline.INDEFINITE);
        glowAnimation.setAutoReverse(true);
        glowAnimation.play();


        Pane logo = new Pane();
        logo.getChildren().add(imageView);
        logo.getStyleClass().add("logo");
        return logo;
    }

    private VBox Form(){
        VBox form = new VBox();
        form.getStyleClass().add("form-1");

        HBox login_container = new HBox();
        Label login = new Label("BBQ LAGAO");
        login_container.setAlignment(Pos.CENTER);
        login.getStyleClass().add("label");

        VBox center = new VBox();
        HBox username_container = new HBox();
        TextField userText = new TextField();
        userText.setPromptText("Username:");
        userText.getStyleClass().add("textfield");
        Pane personIcon = new Pane();
        personIcon.getStyleClass().add("personIcon");
        HBox.setMargin(personIcon,new Insets(7.5 ,0 ,0, -60));
        username_container.getStyleClass().add("containerForm");

        HBox password_container = new HBox();
        PasswordField passText = new PasswordField();
        passText.setPromptText("Password:");
        passText.getStyleClass().add("textfield");
        Pane lockIcon = new Pane();
        lockIcon.getStyleClass().add("lockIcon");
        HBox.setMargin(lockIcon,new Insets(7.5 ,0 ,0, -60));
        password_container.getStyleClass().add("containerForm");

        HBox dropbox_container = new HBox();
        ComboBox<String> roles = new ComboBox<>();
        roles.getItems().addAll("Manager","Cashier","Inventory Clerk");
        roles.setPromptText("Select Role");
        roles.getStyleClass().addAll("drop_box", "border-radius", "background-radius");
        roles.setOnMouseClicked(event -> playFadeIn(roles));
        dropbox_container.getStyleClass().add("containerForm");

        Label errormsg = new Label();
        errormsg.setStyle("-fx-text-fill:red; -fx-font-size: 14px;");
        errormsg.setVisible(false);

        HBox button_container = new HBox();
        Button loginbtn = new Button();
        loginbtn.setText("Login");
        loginbtn.getStyleClass().addAll("btn","border-radius", "background-radius");
        button_container.getStyleClass().add("containerForm");
        loginbtn.setOnAction(new LoginHandler(userText,passText,roles,errormsg,this.scene));

        center.setAlignment(Pos.CENTER);
        button_container.getChildren().add(loginbtn);
        dropbox_container.getChildren().add(roles);
        username_container.getChildren().addAll(userText,personIcon);
        password_container.getChildren().addAll(passText,lockIcon);
        center.getChildren().addAll(username_container,password_container,dropbox_container,errormsg,button_container);
        login_container.getChildren().add(login);
        form.getChildren().addAll(login_container,center);
        return form;
    }

    private Rectangle rect(){
        GaussianBlur blur = new GaussianBlur(10);
        Rectangle rect = new Rectangle(700,500, Color.color(1,1,1,0.5));
        rect.setArcHeight(60);
        rect.setArcWidth(60);
        rect.setEffect(blur);
        return rect;
    }

    private void playFadeIn(ComboBox<String> roles){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), roles);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

}
