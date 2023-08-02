package gameGUI;

import ChessGUI.MainScene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import start.Start;

public class EraPickScene extends Scene {

    private VBox box;
    private Button old;
    private Button middle;
    private Button newEra;
    private Button modern;
    private GridPane root;

    public EraPickScene(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1, paint);
        init();
        actionSet();
        this.root = (GridPane) parent;
        root.getChildren().add(box);
    }

    private void init(){
        old = new Button("OLD AGE");
        middle = new Button("MIDDLE AGE");
        newEra = new Button("NEW ERA");
        modern = new Button("MODERN AGE");
        box = new VBox();
        box.getChildren().addAll(old, middle, newEra, modern);
    }

    private void actionSet(){
        modern.setOnAction(e -> {
            Start.getStage1().setScene(new modernCourtyPickScene(new GridPane(), 700, 400, Color.BROWN));
            Start.getStage1().show();
        });
    }
}
