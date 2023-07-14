package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Stage stage1 = new Stage();

        GridPane root = new GridPane();
        Tabla t = new Tabla(560);

        Image icon = new Image(String.valueOf(getClass().getResource("/tablaSah.png")));
        stage1.getIcons().add(icon);

        t.nacrtajTablu();
        t.postaviFigure();
        root.getChildren().add(t);
        Scene scene = new Scene(root, 560, 560, Color.BROWN);



        stage1.setTitle("SAH");

        stage1.setScene(scene);
        stage1.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}