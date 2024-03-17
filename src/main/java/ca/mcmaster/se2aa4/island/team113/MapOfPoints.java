package ca.mcmaster.se2aa4.island.team113;

import java.util.Map;
import java.util.HashMap;

public class MapOfPoints {
    private Map<String, Coordinate> pointsOfInterest;

    public MapOfPoints() {
        pointsOfInterest = new HashMap<>();
    }

    public void addPointOfInterest(String poi, double x, double y) {
        pointsOfInterest.put(poi, new Coordinate(x, y));
    }

    public Coordinate getPointOfInterest(String poi) {
        return pointsOfInterest.get(poi);
    }

    public double calculateDistance(String emergency_site, String creek) {
        Coordinate emergency_site_pos = pointsOfInterest.get(emergency_site);
        Coordinate creek_pos = pointsOfInterest.get(creek);

        if (emergency_site_pos == null || creek_pos == null) {
            throw new IllegalArgumentException("One or both points not found in map");
        }

        double x = emergency_site_pos.getX() - creek_pos.getX();
        double y = emergency_site_pos.getY() - creek_pos.getY();

        return Math.sqrt(x*x + y*y);
    }
}
