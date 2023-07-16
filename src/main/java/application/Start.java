package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Start extends Application {

    private Scene scene;
    @Override
    public void start(Stage stage){
        Stage stage1 = new Stage();

        Image icon = new Image(String.valueOf(getClass().getResource("/tablaSah.png")));
        stage1.getIcons().add(icon);

        GridPane root = new GridPane();
        scene = new MainScene(root, 720, 720, Color.BROWN);

        stage1.setScene(scene);
        stage1.setTitle("SAH");
        stage1.centerOnScreen();
        stage1.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Scene getScene() {
        return scene;
    }
}