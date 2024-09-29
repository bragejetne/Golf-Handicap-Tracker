package golfhandicaptracker;

public class Courseinfo {
    private final int courseSlope;
    private final double courserating;

    public Courseinfo(int courseSlope, double courserating) {
        this.courseSlope = courseSlope;
        this.courserating = courserating;
    }

    public int getSlope() {
        return courseSlope;
    }

    public double getRating() {
        return courserating;
    }

    public int get(int i) {
        return i;
    }
}
