package start;

import ChessGUI.MainScene;
import gameGUI.EraPickScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Start extends Application {

    private static Stage stage1;
    private Scene scene;
    @Override
    public void start(Stage stage){
        stage1 = new Stage();

        Image icon = new Image(String.valueOf(getClass().getResource("/tablaSah.png")));
        stage1.getIcons().add(icon);

        StackPane root = new StackPane();
        scene = new EraPickScene(root, 300, 300);
        //root.setId("pane");
        //scene.getStylesheets().addAll(this.getClass().getResource("/background.css").toExternalForm()); /// NOT WORKING IDK WHY
        //scene = new MainScene(root, 720, 720, Color.BROWN);
        /* css file
            .root {
                -fx-background-image: url("/backgrounds/background.jpg");
                -fx-background-repeat: stretch;
                -fx-background-size: 300 300;
                -fx-background-position: center center;
            }
         */

        stage1.setScene(scene);
        stage1.setTitle("SAH");
        stage1.centerOnScreen();
        stage1.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage1() {
        return stage1;
    }
}