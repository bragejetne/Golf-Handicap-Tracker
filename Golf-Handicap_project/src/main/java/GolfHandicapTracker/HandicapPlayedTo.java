package golfhandicaptracker;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HandicapPlayedTo implements RegisterLocalScore{

    public double handicapThisRound;
    public Courseinfo courseInfo;
    public static String golfPlayer;
    static Queue<Double> twentyLastRounds = new LinkedList<Double>();
    static HashMap<String,List<String>> formerRounds = new HashMap<String,List<String>>();
    HashMap<String, Courseinfo> golfCourses = new HashMap<String, Courseinfo>();

    // setter golfbanene man kan registrere scorer ved
    public void localCourses(){
        golfCourses.put("Bærum", new Courseinfo(134, 71));
        golfCourses.put("Haga", new Courseinfo(136, 71));
        golfCourses.put("Ostøya", new Courseinfo(126, 70.8));
        golfCourses.put("Kjekstad", new Courseinfo(123, 64.5));
        golfCourses.put("Oslo", new Courseinfo(134, 75.9));
        golfCourses.put("Onsøy", new Courseinfo(133, 71.4));
        golfCourses.put("Borre", new Courseinfo(146, 72.6));
    }

   
    //finner ut hvem som har spilt
    public String whoPlayed(String name){
        HandicapPlayedTo.golfPlayer = name;
        return golfPlayer;
    }
    

    // regner ut handicapet spilt til for denne runden
    @Override
    public double handicapSpiltTil(int bruttoscore, String golfcourse) {
    if (golfCourses.containsKey(golfcourse)) {
        Courseinfo courseInfo = golfCourses.get(golfcourse);
            if (courseInfo != null) {
                double slope = courseInfo.getSlope();
                double rating = courseInfo.getRating();

                //Sjekker at slope og rating ikke er null
                if (slope != 0 && rating != 0) { 
                    double handicapThisRound = (113 / slope) * (bruttoscore - rating);
                    if (twentyLastRounds.size() == 20) {
                        twentyLastRounds.poll(); 
                    }
                    twentyLastRounds.offer(handicapThisRound);
                    return handicapThisRound;
                } else {
                    throw new IllegalArgumentException("Slope or rating cannot be zero for golf course: " + golfcourse);
                }
            } else {
                throw new IllegalArgumentException("Courseinfo is null for golf course: " + golfcourse);
            }
    } else {
        throw new IllegalArgumentException("Unknown golf course: " + golfcourse);
    }
}

    //Åpner de siste tjue rundene
    @SuppressWarnings("unchecked")
    static Queue<Double> loadTwentyLastRounds() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("twentyLastRounds.txt"))) {
            return (Queue<Double>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Hvis den ikke finnes fra før, opprettes den
            return new LinkedList<>();
        }
    }

    // Lagrer de siste tjue rundene
    private static void saveTwentyLastRounds(Queue<Double> twentyLastRounds) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("twentyLastRounds.txt"))) {
            oos.writeObject(twentyLastRounds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void startApp(int bruttoscoren, String golfCoursen) {
        twentyLastRounds = loadTwentyLastRounds();
        HandicapPlayedTo handicapCalculator = new HandicapPlayedTo();
        TotalHandicap totalhandicap_and_date = new TotalHandicap();
        HandicapHistory handicapFileWrite = new HandicapHistory();
        handicapCalculator.localCourses();
        handicapCalculator.whoPlayed("Brage");
        double resultat = handicapCalculator.handicapSpiltTil(bruttoscoren, golfCoursen);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedResult = decimalFormat.format(resultat);
        saveTwentyLastRounds(twentyLastRounds);
        System.out.println("Handicap " + golfPlayer + " played to was: " + formattedResult);
        System.out.println(totalhandicap_and_date.yourTotalHandicap());
        totalhandicap_and_date.myTotalhandicap();
        System.out.println(totalhandicap_and_date.myTotalhandicap());
        handicapFileWrite.writeToFile(totalhandicap_and_date);
    }


}








