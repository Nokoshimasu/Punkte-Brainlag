package org.example.punkte_brainlag;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private int teamCount;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Brainlag Scoreboard");
        primaryStage.setScene(createTeamSelectionScene(primaryStage));
        primaryStage.show();
    }

    // Szene 1: Auswahl der Teamanzahl
    private Scene createTeamSelectionScene(Stage primaryStage) {
        VBox vBox = new VBox(40);
        vBox.setPadding(new Insets(40));
        vBox.setAlignment(Pos.CENTER);

        Label title = new Label("Brainlag Scoreboard");
        title.setFont(Font.font(32));

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);

        for (int teamCount : List.of(2, 3, 4, 5, 6)) {
            Button b = new Button(teamCount + " Teams");
            b.setPrefWidth(120);
            b.setAlignment(Pos.CENTER);
            b.setOnAction(e -> {
                this.teamCount = teamCount;
                primaryStage.setScene(createTeamNameScene(primaryStage));
                primaryStage.centerOnScreen();
            });
            hBox.getChildren().add(b);
        }



        vBox.getChildren().addAll(title, hBox);
        return new Scene(vBox, 600, 200);
    }

    // Szene 2: Eingabe der Teamnamen
    private Scene createTeamNameScene(Stage primaryStage) {
        VBox tf = new VBox(15);
        tf.setPadding(new Insets(25));
        tf.setAlignment(Pos.CENTER);

        Label team = new Label("Teamnamen eingeben");
        team.setFont(Font.font(24));
        tf.getChildren().add(team);

        List<TextField> fields = new ArrayList<>();
        for (int i = 1; i <= this.teamCount; i++) {
            TextField field = new TextField("Team " + i);
            field.setPrefWidth(250);
            fields.add(field);
            tf.getChildren().add(field);
        }

        Button start = new Button("Spiel starten");
        tf.getChildren().add(start);

        start.setOnAction(ev -> {
            List<Team> teams = new ArrayList<>();
            for (TextField tF : fields) {
                teams.add(new Team(tF.getText()));
            }
            primaryStage.setScene(createScoreboardScene(teams));
            primaryStage.setMaximized(true);
        });

        return new Scene(tf, 400, 400);
    }

    // Szene 3: Scoreboard
    private Scene createScoreboardScene(List<Team> teams) {
        BorderPane borderPane = new BorderPane();

        // Center der BorderPane
        HBox punkte = new HBox(20);
        punkte.setAlignment(Pos.CENTER);

        VBox labels = new VBox(40);
        labels.setAlignment(Pos.CENTER);
        labels.setMinWidth(100);
        labels.setMaxWidth(100);

        Label team = new Label("Team");
        team.setFont(Font.font(30));
        team.setStyle("-fx-font-weight: bold");

        Label score = new Label("Punkte");
        score.setFont(Font.font(30));
        score.setStyle("-fx-font-weight: bold");

        labels.getChildren().addAll(team, score);
        punkte.getChildren().add(labels);

        for (Team tm : teams) {
            VBox vBox1 = new VBox(40);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setMinWidth(140);
            vBox1.setMaxWidth(140);

            Label t = new Label(tm.getName());
            t.setFont(Font.font(30));
            t.setMinWidth(140);
            t.setMaxWidth(140);
            t.setEllipsisString("...");
            t.setTextOverrun(OverrunStyle.ELLIPSIS);
            t.setWrapText(false);
            t.setAlignment(Pos.CENTER);

            Label p = new Label();
            p.setFont(Font.font(30));
            p.setMinWidth(140);
            p.setMinWidth(140);
            p.setAlignment(Pos.CENTER);
            p.textProperty().bind(tm.punkteProperty().asString());

            vBox1.getChildren().addAll(t, p);
            punkte.getChildren().add(vBox1);
        }

        // Linke Seite der BorderPane
        VBox links = new VBox(20);
        links.setPadding(new Insets(25));
        links.setAlignment(Pos.CENTER);
        CheckBox check = new CheckBox("x1,5");
        check.setFont(Font.font(15));
        HBox points = new HBox(20);
        points.setAlignment(Pos.CENTER);
        VBox plusPunkte = new VBox(10);
        plusPunkte.setAlignment(Pos.CENTER);
        VBox minusPunkte = new VBox(10);
        minusPunkte.setAlignment(Pos.CENTER);

        // Buttons
        List<Integer> normalPlus = List.of(200, 300, 400, 500, 600);
        List<Integer> normalMinus = List.of(-100, -150, -200, -250, -300);
        List<Integer> bonusPlus = List.of(300, 450, 600, 750, 900);
        List<Integer> bonusMinus = List.of(-150, -225, -300, -375, -450);

        updateScoreButtons(plusPunkte, minusPunkte, normalPlus, normalMinus, teams);

        check.selectedProperty().addListener((ov, wasSelected, isSelected) -> {
            if (check.isSelected()) {
                updateScoreButtons(plusPunkte, minusPunkte, bonusPlus, bonusMinus, teams);
            } else {
                updateScoreButtons(plusPunkte, minusPunkte, normalPlus, normalMinus, teams);
            }
        });

        points.getChildren().addAll(plusPunkte, minusPunkte);
        links.getChildren().addAll(check, points);

        borderPane.setCenter(punkte);
        borderPane.setLeft(links);

        return new Scene(borderPane, 800, 500);
    }

    private Button createScoreButton(int value, List<Team> teams) {
        Button button = new Button((value >= 0 ? "+" : "") + value);
        button.setFont(Font.font(15));
        button.setOnAction(event -> DialogScreen.openDialogScreen(value, teams));
        return button;
    }

    private void updateScoreButtons(VBox plusPunkte, VBox minusPunkte, List<Integer> plusValues, List<Integer> minusValues, List<Team> teams) {
        plusPunkte.getChildren().clear();
        minusPunkte.getChildren().clear();

        for (int value : plusValues) {
            plusPunkte.getChildren().add(createScoreButton(value, teams));
        }
        for (int value : minusValues) {
            minusPunkte.getChildren().add(createScoreButton(value, teams));
        }
    }
}
