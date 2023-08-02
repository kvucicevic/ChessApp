package gameGUI;

import ChessGUI.MainScene;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import start.Start;

public class modernCourtyPickScene extends Scene {

    private Button germany;
    private Button france;
    private Button britan;
    private Button austia;
    private Button russia;
    private HBox box;
    private GridPane root;


    public modernCourtyPickScene(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1);
        init();
        actionSet();
        this.root = (GridPane) parent;
        root.getChildren().add(box);
    }

    private void init(){
        germany = new Button("GERMANY", new ImageView(String.valueOf(getClass().getResource("/flags/germany.jpg"))));
        france = new Button("FRANCE", new ImageView(String.valueOf(getClass().getResource("/flags/france.jpg"))));
        britan = new Button("BRITAN", new ImageView(String.valueOf(getClass().getResource("/flags/britan.jpg"))));
        austia = new Button("AUSTRIA", new ImageView(String.valueOf(getClass().getResource("/flags/germany.jpg"))));
        russia = new Button("RUSSIA", new ImageView(String.valueOf(getClass().getResource("/flags/russia.jpg"))));
        box = new HBox();
        box.getChildren().addAll(germany, france, russia, britan, austia);

    }

    private void actionSet(){

        germany.setOnAction(e -> {
            Start.getStage1().setScene(new MainScene(new GridPane(), 720, 720, Color.BROWN));
            Start.getStage1().show();
        });
    }
}
