module org.example.punkte_brainlag {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.punkte_brainlag to javafx.fxml;
    exports org.example.punkte_brainlag;
}