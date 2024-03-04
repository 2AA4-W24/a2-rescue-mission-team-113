package ca.mcmaster.se2aa4.island.team113;

import java.util.Map;
import java.util.HashMap;

public class MapOfPoints {
    private Map<String, Position> pointsOfInterest;

    public MapOfPoints() {
        pointsOfInterest = new HashMap<>();
    }

    public void addPointOfInterest(String poi, double x, double y) {
        pointsOfInterest.put(poi, new Position(x, y));
    }

    public Position getPointOfInterest(String poi) {
        return pointsOfInterest.get(poi);
    }

    public double calculateDistance(String emergency_site, String creek) {
        Position emergency_site_pos = pointsOfInterest.get(emergency_site);
        Position creek_pos = pointsOfInterest.get(creek);

        if (emergency_site_pos == null || creek_pos == null) {
            throw new IllegalArgumentException("One or both points not found in map");
        }

        double x = emergency_site_pos.getX() - creek_pos.getX();
        double y = emergency_site_pos.getY() - creek_pos.getY();

        return Math.sqrt(x*x + y*y);
    }
}
