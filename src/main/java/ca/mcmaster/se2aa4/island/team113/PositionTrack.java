package ca.mcmaster.se2aa4.island.team113;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class PositionTrack {
    private final Logger logger = LogManager.getLogger();
    private MapOfPoints map;
    private Direction currentDirection;
    private Coordinate position;
    private ArrayList<String> allCreeks;
    private double distance;
    private String closestCreek;
    Commands command = new Commands();

    public PositionTrack(Direction initialDirection){
        this.map = new MapOfPoints();
        this.currentDirection=initialDirection;
        this.allCreeks = new ArrayList<>();
        this.distance = Double.POSITIVE_INFINITY;
        this.closestCreek = "";
        this.position = new Coordinate(0,0);

        
    }
    public Coordinate getCurrentCoordinate(){
        return position;
    }
    public Direction getcurrentDirection(){
        return currentDirection;
    }
    public void addCreek(String creekID){ //added for testing
        allCreeks.add(creekID);
    }

    public void clearCreeks(){ //added for testing
        allCreeks.clear();
    }

    public void addPOI(String key, double x, double y){ //added for testing
        map.addPointOfInterest(key, x, y);
    }


    public String findClosestCreek(){

        for (int i =0; i< allCreeks.size(); i++){
            double newDistance = map.calculateDistance("site", allCreeks.get(i));

            if (newDistance < distance){
                distance = newDistance;
                closestCreek = allCreeks.get(i);
            }
        }
        return closestCreek;
        
    }

    public void positionTracker(JSONObject decision){
        if(decision.similar(command.turnLeft(currentDirection))){
            logger.info("GOING LEFT");
            position = position.turnLeft(currentDirection);
            currentDirection = currentDirection.goLeft();

        } else if (decision.similar(command.turnRight(currentDirection))){
            logger.info("GOING RIGHT");
            position = position.turnRight(currentDirection);
            currentDirection = currentDirection.goRight();

        }else if (decision.similar(command.fly())){
            logger.info("GOING FORWARD");
            position = position.forward(currentDirection);
        }

    }

    public void mapUpdate(Information info){
        JSONObject extras = info.getExtras();
        if (extras.has("creeks")){
            JSONArray creeks = extras.getJSONArray("creeks");
            if (creeks.length() > 0){
                for (int i=0; i<creeks.length(); i++){
                    map.addPointOfInterest(creeks.optString(i), position.getX(), position.getY());
                    allCreeks.add(creeks.optString(i));
                }
            }
        }
        if (extras.has("sites")){
            JSONArray sites = extras.getJSONArray("sites");
            if (sites.length() > 0){
                for (int i=0; i<sites.length(); i++){
                    map.addPointOfInterest("site", position.getX(), position.getY());
                }
            }
        }

    }
    
}
