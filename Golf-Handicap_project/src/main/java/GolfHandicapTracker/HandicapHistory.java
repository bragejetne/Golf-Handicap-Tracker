package mittprosjekt;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class HandicapHistory {

   // TotalHandicap handicapRN;
   DecimalFormat decimalFormat = new DecimalFormat("#.#");
   List<Double> eightScore = new ArrayList<>();

    public void writeToFile(TotalHandicap handicapRN) {
        try {
            FileWriter writer = new FileWriter("Handicapstorer.txt");
            writer.write(HandicapPlayedTo.golfPlayer + "'s rounds: ");
            // Henter køen med de siste 20 rundene
            Queue<Double> lastTwentyRounds = HandicapPlayedTo.twentyLastRounds;

            // Skriver inn hver score med kun én desimal, og begrenser antallet til 20
            int count = 0;
            for (Double score : lastTwentyRounds) {
                writer.write(decimalFormat.format(score));
                count++;

                // Sjekker om vi har skrevet ut 20 verdier
                if (count >= 20) {
                    break;
                }
                writer.write(" ");
            }
            writer.write("\n");
            writer.write("Your handicap-counting rounds are: " + handicapRN.writeEightBest);
            writer.write("\n");
            writer.write("Amount of rounds played: " + count + "\n");
            writer.write("Current handicap: " + handicapRN.myTotalhandicap());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
