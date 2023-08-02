module com.example.chessapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens ChessGUI to javafx.fxml;
    exports ChessGUI;
    exports observer;
    opens observer to javafx.fxml;
    exports start;
    opens start to javafx.fxml;
}