package gameGUI;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameMainScene extends Scene {

    private StackPane root;

    public GameMainScene(Parent parent, int width, int height) {
        super(parent, width, height);
        root = (StackPane) parent;
        design();
    }

    public void design(){
        Button campaignButton = new Button("Campaign");
        Button multiplayerButton = new Button("Multiplayer");
        Button helpButton = new Button("Help");
        Button aboutButton = new Button("About");

        // Customize button styles to make them look historical
        campaignButton.setStyle("-fx-font-size: 18px; -fx-background-color: #8B4513;");
        multiplayerButton.setStyle("-fx-font-size: 18px; -fx-background-color: #8B4513;");
        helpButton.setStyle("-fx-font-size: 18px; -fx-background-color: #8B4513;");
        aboutButton.setStyle("-fx-font-size: 18px; -fx-background-color: #8B4513;");

        // Create labels for additional elements
        Label titleLabel = new Label("Historical Chess");
        Label versionLabel = new Label("Version 1.0");
        Label authorLabel = new Label("Developed by Kajson");

        // Customize label styles
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #8B4513;");
        versionLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #8B4513;");
        authorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #8B4513;");

        VBox menuLayout = new VBox(55);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-image: url('mainBack.jpg'); -fx-background-size: cover;");
        menuLayout.getChildren().addAll(titleLabel, versionLabel, authorLabel, campaignButton, multiplayerButton, helpButton, aboutButton);


        root.getChildren().add(menuLayout);

    }
}
