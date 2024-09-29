package mittprosjekt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;

public class GolfHandicapController {

    @FXML
    private Label totalHandicapLabel;

    @FXML
    private BarChart<String, Number> roundsBarChart;

    @FXML
    private TextField bruttoScoreField;

    @FXML
    private ComboBox<String> golfCourseComboBox;

    @FXML
    public void initialize() {
        golfCourseComboBox.getItems().addAll("Bærum", "Haga", "Ostøya", "Kjekstad", "Oslo", "Onsøy", "Borre"); // Eksempelbaner
        golfCourseComboBox.getSelectionModel().selectFirst(); // Velg første element som standard
        displayTotalHandicap();
        displayLastRounds();
    }

    @FXML
    private void updateHandicap() {
        try {
            int bruttoScore = Integer.parseInt(bruttoScoreField.getText());
            String golfCourse = golfCourseComboBox.getValue();

            HandicapPlayedTo.startApp(bruttoScore, golfCourse);
            displayTotalHandicap();
            displayLastRounds();
        } catch (NumberFormatException e) {
            // Håndter feil input her, f.eks. ved å vise en advarsel til brukeren
            totalHandicapLabel.setText("Invalid input");
        }
    }

    private void displayTotalHandicap() {
        TotalHandicap totalHandicapCalculator = new TotalHandicap();
        String totalHandicap = totalHandicapCalculator.yourTotalHandicap();
        totalHandicapLabel.setText(totalHandicap);
    }

    private void displayLastRounds() {
    Queue<Double> lastRounds = HandicapPlayedTo.loadTwentyLastRounds();
    List<Double> roundList = new ArrayList<>(lastRounds);

    // Finner indeksene til de åtte beste handicapene. Vi bruker en "prioritetskø" for å holde de laveste handicapene.
    PriorityQueue<Integer> bestRounds = new PriorityQueue<>(
        8, Comparator.<Integer>comparingDouble(i -> roundList.get(i))
          .thenComparingInt(i -> roundList.size() - i).reversed()
    );

    for (int i = 0; i < roundList.size(); i++) {
        if (bestRounds.size() < 8) {
            bestRounds.offer(i);
        } else {
            // Sammenligner nåværende runde med den høyeste runden i de beste rundene.
            int highestBestRoundIndex = bestRounds.peek();
            Double currentHandicap = roundList.get(i);
            Double highestBestHandicap = roundList.get(highestBestRoundIndex);
            if (currentHandicap < highestBestHandicap || 
                (currentHandicap.equals(highestBestHandicap) && i > highestBestRoundIndex)) {
                bestRounds.poll();
                bestRounds.offer(i);
            }
        }
    }

    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Handicap per Runde");

    for (int i = 0; i < roundList.size(); i++) {
        Double roundHandicap = roundList.get(i);
        XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(i + 1), roundHandicap);
        series.getData().add(data);

        final boolean isBestRound = bestRounds.contains(i);
        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                String color = isBestRound ? "blue" : "black";
                newNode.setStyle("-fx-bar-fill: " + color + ";");
            }
        });
    }

    roundsBarChart.getData().clear();
    roundsBarChart.getData().add(series);
}
}
