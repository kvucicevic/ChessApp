package gameGUI;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import start.Start;

public class EraPickScene extends Scene {

    private VBox box;
    private Button old;
    private Button middle;
    private Button newEra;
    private Button modern;
    private StackPane root;

    public EraPickScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
        init();
        actionSet();
        this.root = (StackPane) parent;
        root.getChildren().add(box);

        BackgroundImage myBI= new BackgroundImage(new Image("/background.jpg",300,300,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        root.setBackground(new Background(myBI));
    }

    private void init(){
        old = new Button("OLD AGE");
        middle = new Button("MIDDLE AGE");
        newEra = new Button("NEW ERA");
        modern = new Button("MODERN AGE");
        box = new VBox();
        box.getChildren().addAll(old, middle, newEra, modern);
        box.setAlignment(Pos.CENTER);
    }

    private void actionSet(){
        modern.setOnAction(e -> {
            Start.getStage1().setScene(new modernCourtyPickScene(new GridPane(), 700, 400, Color.BROWN));
            Start.getStage1().show();
        });
    }
}
