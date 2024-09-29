package golfhandicaptracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GolfHandicapApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Last inn FXML-filen
            Parent root = FXMLLoader.load(getClass().getResource("Golf.fxml"));

            // Setter opp scenen
            Scene scene = new Scene(root);

            // Viser scenen
            primaryStage.setScene(scene);
            primaryStage.setTitle("Golf Handicap App");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}






