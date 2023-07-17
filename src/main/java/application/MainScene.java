package application;

import figure.Figura;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import observer.Subscriber;

public class MainScene extends Scene implements Subscriber {

    private Tabla t;
    protected GridPane root;
    protected VBox box;
    private HBox uppBox;
    private HBox downBox;

    public MainScene(Parent parent, double v, double v1, Paint paint) {
        super(parent, v, v1, paint);
        this.root = (GridPane) parent;
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
