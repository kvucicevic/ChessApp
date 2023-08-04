package gameGUI;

import ChessGUI.MainScene;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import start.Start;

public class modernCourtyPickScene extends Scene {

    private GridPane root;
    private HBox box;
    private Button germany;
    private Label germanyLbl;
    private VBox germanyBox;

    private Button france;
    private Label franceLbl;
    private VBox franceBox;

    private Button britain;
    private Label britainLbl;
    private VBox britainBox;

    private Button austria;
    private Label austriaLbl;
    private VBox austriaBox;

    private Button russia;
    private Label russiaLbl;
    private VBox russiaBox;


    public modernCourtyPickScene(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1, paint);
        init();
        actionSet();
        this.root = (GridPane) parent;
        root.getChildren().add(box);
    }

    private void init(){
        germany = new Button("", new ImageView(String.valueOf(getClass().getResource("/flags/germany.jpg"))));
        germanyLbl = new Label("GERMANY");
        germanyBox = new VBox();
        germanyBox.getChildren().addAll(germany, germanyLbl);
        germanyBox.setAlignment(Pos.CENTER);

        france = new Button("", new ImageView(String.valueOf(getClass().getResource("/flags/france.jpg"))));
        franceLbl = new Label("FRANCE");
        franceBox = new VBox();
        franceBox.getChildren().addAll(france, franceLbl);
        franceBox.setAlignment(Pos.CENTER);

        britain = new Button("", new ImageView(String.valueOf(getClass().getResource("/flags/britan.jpg"))));
        britainLbl = new Label("BRITAIN");
        britainBox = new VBox();
        britainBox.getChildren().addAll(britain, britainLbl);
        britainBox.setAlignment(Pos.CENTER);

        austria = new Button("", new ImageView(String.valueOf(getClass().getResource("/flags/germany.jpg"))));
        austriaLbl = new Label("AUSTRIA");
        austriaBox = new VBox();
        austriaBox.getChildren().addAll(austria, austriaLbl);
        austriaBox.setAlignment(Pos.CENTER);

        russia = new Button("", new ImageView(String.valueOf(getClass().getResource("/flags/russia.jpg"))));
        russiaLbl = new Label("RUSSIA");
        russiaBox = new VBox();
        russiaBox.getChildren().addAll(russia, russiaLbl);
        russiaBox.setAlignment(Pos.CENTER);

        box = new HBox();
        box.getChildren().addAll(germanyBox, franceBox, russiaBox, britainBox, austriaBox);
        box.setAlignment(Pos.CENTER);
    }

    private void actionSet(){
        germany.setOnAction(e -> {
            Start.getStage1().setScene(new MapScene(new StackPane(), 720, 720));
            Start.getStage1().centerOnScreen();
            Start.getStage1().show();
        });
    }
}
