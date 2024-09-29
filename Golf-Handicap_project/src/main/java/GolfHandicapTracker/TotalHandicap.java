package golfhandicaptracker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime; 

public class TotalHandicap {

    List<Double> copiedTwentyLastRounds;
    String copiedGolfPlayer;
    String formattedResult;
    List<Double> eightBest = new ArrayList<Double>();
    List<String> writeEightBest = new ArrayList<String>();
    HashMap<String,Collection<String>> former_handicaps = new HashMap<String,Collection<String>>();
    public double handicap;
    public String atm;
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    

    public String yourTotalHandicap() {
        // Lager en kopi av køen med handicaps
        copiedTwentyLastRounds = new ArrayList<>(HandicapPlayedTo.twentyLastRounds);
        this.copiedGolfPlayer = HandicapPlayedTo.golfPlayer;
    
        // Finner de 8 sterkeste rundene
        if (copiedTwentyLastRounds.size() > 8) {
            Collections.sort(copiedTwentyLastRounds);
            for (int i = 0; i < 8; i++) {
                eightBest.add(copiedTwentyLastRounds.get(i));
            }
        } else {
            eightBest = copiedTwentyLastRounds;
        }
    
        // Sjekker om det er noen runder i eightBest
        if (eightBest.isEmpty()) {
            return "No rounds available";
        }
    
        // Finner handicappet
        double sum = eightBest.stream().mapToDouble(Double::doubleValue).sum();
        handicap = sum / eightBest.size();
        
        // Sjekker om handicap er NaN eller ugyldig
        if (Double.isNaN(handicap) || Double.isInfinite(handicap)) {
            return "Invalid handicap value";
        }
    
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        this.formattedResult = decimalFormat.format(handicap);
    
        // Registrerer datoen handicappet ble registrert
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        this.atm = dtf.format(now);

        //Konverterer elementene i eightBest til String
        for (Double score : eightBest){
            writeEightBest.add(decimalFormat.format(score));
        }
    
        return (copiedGolfPlayer + "'s handicap at " + atm + " is " + formattedResult);
    }
    
    

    public String myTotalhandicap(){
        return formattedResult;
    }

    public void writeHandicapToFile(){
        //skriver inn dato og handicap for gjeldene registrering
        try (FileWriter writer = new FileWriter("HandicapHistorikk.txt", true)) {
            writer.write("Hcp: " + handicap + " at " + atm + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}
         
