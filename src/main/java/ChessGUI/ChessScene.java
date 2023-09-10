package ChessGUI;

import figure.Figura;
import gameGUI.MapScene;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import observer.Subscriber;
import start.Start;

public class ChessScene extends Scene implements Subscriber {

    private Tabla t;
    protected StackPane root;
    protected VBox box;
    private HBox uppBox;
    private HBox downBox;
    private Button backBtn;

    public ChessScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
        this.root = (StackPane) parent;
        box = new VBox();
        root.getChildren().add(box);
        init();
        t.addSub(this);
    }

    private void init(){

        uppBox = new HBox();
        uppBox.setMinHeight(70);
        downBox = new HBox();
        downBox.setMinHeight(70);

        box.getChildren().add(uppBox);
        t = new Tabla(560);

        t.nacrtajTablu();
        t.postaviFigure();
        box.getChildren().add(t);

        box.getChildren().add(downBox);
        Insets padding = new Insets(10, 10, 0, 80);
        root.setPadding(padding);
        backBtn = new Button("previous screen");
        box.getChildren().add(backBtn);
        backBtn.setOnAction(e -> {
            Start.getStage1().setScene(new MapScene(new StackPane(), 720, 720));
            Start.getStage1().centerOnScreen();
            Start.getStage1().show();
        });
    }

    @Override
    public void update(Object notification) {
        if(notification instanceof Figura){
            if(((Figura)notification).boja == Figura.Boja.BELA) {
                uppBox.getChildren().add((Figura) notification);
            } else {
                downBox.getChildren().add((Figura) notification);
            }
            System.out.println(notification);
        }
    }
}
