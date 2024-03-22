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

    public double calculateDistance(String emergencySite, String creek) {
        Coordinate emergencySitePOS = pointsOfInterest.get(emergencySite);
        Coordinate creekPOS = pointsOfInterest.get(creek);

        if (emergencySitePOS == null || creekPOS == null) {
            throw new IllegalArgumentException("One or both points not found in map");
        }

        double x = emergencySitePOS.getX() - creekPOS.getX();
        double y = emergencySitePOS.getY() - creekPOS.getY();

        return Math.sqrt(x*x + y*y);
    }

    
}
