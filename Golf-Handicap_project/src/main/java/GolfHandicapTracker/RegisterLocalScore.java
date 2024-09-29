package golfhandicaptracker;

import java.util.ArrayList;
import java.util.HashMap;

public interface RegisterLocalScore {

   public double handicapSpiltTil(int bruttoscore, String golf_course);
   public HashMap<String,ArrayList<Integer>> golf_courses = new HashMap<String, ArrayList<Integer>>();

}
