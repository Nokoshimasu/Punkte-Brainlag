package org.example.punkte_brainlag;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

import java.util.List;

public class DialogScreen {
    public static void openDialogScreen(int points, List<Team> teams) {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        for (Team team : teams) {
            Button button = new Button(team.getName());
            hBox.getChildren().add(button);
            button.setOnAction(e -> {
               team.addPunkte(points);
            });
        }
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Punktevergabe");
        dialog.getDialogPane().setContent(hBox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

}
