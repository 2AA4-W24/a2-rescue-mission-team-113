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
    private double newDistance;
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


    public String findClosestCreek(){
        logger.info("SITE X COORDINATE {}", map.getPointOfInterest("site").getX());
        logger.info("SITE Y COORDINATE {}", map.getPointOfInterest("site").getY());
        for (int i =0; i< allCreeks.size(); i++){
            newDistance = map.calculateDistance("site", allCreeks.get(i));
            logger.info("CREEK ID: {}", allCreeks.get(i));
            logger.info("DISTENCE FROM SITE {}", newDistance);
            logger.info("CREEK X COORDINATE {}", map.getPointOfInterest(allCreeks.get(i)).getX());
            logger.info("CREEK Y COORDINATE {}", map.getPointOfInterest(allCreeks.get(i)).getY());
            if (newDistance < distance){
                distance = newDistance;
                closestCreek = allCreeks.get(i);
            }
        }
        return closestCreek;
        
    }

    public void positionTracker(JSONObject decision){
        if(decision.similar(command.TurnLeft(currentDirection))){
            logger.info("GOING LEFT");
            logger.info("{}",command.TurnLeft(currentDirection).toString(2));
            position = position.turnLeft(currentDirection);
            currentDirection = currentDirection.goLeft();

        } else if (decision.similar(command.TurnRight(currentDirection))){
            logger.info("GOING RIGHT");
            logger.info("{}",command.TurnRight(currentDirection).toString(2));
            position = position.turnRight(currentDirection);
            currentDirection = currentDirection.goRight();

        }else if (decision.similar(command.fly())){
            logger.info("GOING FORWARD");
            logger.info("MAP DIRECTION {}", currentDirection.directionToString());
            logger.info("{}",command.fly().toString(2));
            position = position.forward(currentDirection);
        }
        //logger.info(" X COORDINATE {}", position.getX());
        //logger.info(" Y COORDINATE {}", position.getY());

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
