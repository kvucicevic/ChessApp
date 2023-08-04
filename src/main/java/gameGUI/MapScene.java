package gameGUI;

import ChessGUI.MainScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import start.Start;

public class MapScene extends Scene {

    private Label textLabel;
    private VBox box;
    private Button nextBtn;

    public MapScene(Parent parent, double v, double v1) {
        super(parent, v, v1);
        textLabel = new Label();
        box = new VBox();
        setTextForLabel(textLabel);

        nextBtn = new Button("NEXT");
        nextBtn.setAlignment(Pos.BOTTOM_RIGHT);

        BackgroundImage myBI= new BackgroundImage(new Image("/europe1941.jpg",600,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        ((StackPane)parent).setBackground(new Background(myBI));

        VBox imageBox = new VBox();
        Label nameLbl = new Label("Commander Himmler");
        nameLbl.setStyle("-fx-font-weight: bold;");
        nameLbl.setStyle("-fx-background-color: rgba(250,235,215);");
        imageBox.setMaxSize(1000, 500);
        Image commander = new Image("/himmler.jpg", 150, 250, false, true);
        ImageView imageView = new ImageView(commander);
        imageBox.getChildren().addAll(imageView, nameLbl, nextBtn);
        imageBox.setAlignment(Pos.BOTTOM_LEFT);
        Insets paddingPic = new Insets(20, 20, 20, 20);
        imageBox.setPadding(paddingPic);

        box.getChildren().addAll(textLabel);
        textLabel.autosize();
        textLabel.toFront();
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-font-weight: bold;");
        Insets paddingText = new Insets(20, 20, 20, 20);
        box.setPadding(paddingText);
        VBox.setVgrow(textLabel, javafx.scene.layout.Priority.ALWAYS);
        box.setFillWidth(true);
        box.setMaxSize(400, 250);
        box.setStyle("-fx-background-color: rgba(250,235,215, 0.7);");
        box.setAlignment(Pos.CENTER_LEFT);

        ((StackPane)parent).getChildren().addAll(box, imageBox);
        actionSet();
    }

    private void setTextForLabel(Label textLbl){
        textLbl.setText("Commander,\n" +
                "\n" +
                "As we approach the critical moment of our mission, " +
                "I want to emphasize the significance of the task ahead. " +
                "Our Poland conquest was a success, and I expect at least the same from this one." +
                "Our opponent is a strong French army. Their oppening tactics are strong, " +
                "therefore we have to prepare our opening attacks well. " +
                "The success of this operation rests on your leadership " +
                "and the preparedness of your unit. I have utmost confidence in your abilities, " +
                "and I know that you and your soldiers are ready for this challenge. ");
    }

    private void actionSet(){

        nextBtn.setOnAction(e -> {
            Start.getStage1().setScene(new MainScene(new StackPane(), 720, 720));
            Start.getStage1().centerOnScreen();
            Start.getStage1().show();
        });
    }
}
