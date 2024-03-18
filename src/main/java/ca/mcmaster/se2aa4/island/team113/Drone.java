package ca.mcmaster.se2aa4.island.team113;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Drone {
    private final Logger logger = LogManager.getLogger();
    private Direction currentDirection ;
    Commands command = new Commands();
    Integer charge;
    private DroneBattery battery;
    private Information info;
    GoToGround ground;
    ScanIsland islandScanner;
    private boolean initialScanner;
    private Direction initialDirection;
    private Direction mapDirection;
    private MapOfPoints map;
    private Coordinate position;
    private ArrayList<String> allCreeks;
    private double distance;
    private double newDistance;
    private String closestCreek;


    public Drone(Integer battery, String direction){
        this.initialDirection = Direction.stringToDirection(direction);
        this.battery = new DroneBattery(battery);
        this.initialScanner= false;
        this.mapDirection= initialDirection;
        this.map = new MapOfPoints();
        this.position = new Coordinate(0,0);
        ground = new GoToGround(initialDirection);
        this.allCreeks = new ArrayList<>();
        this.distance = Double.POSITIVE_INFINITY;
        this.closestCreek = "";
        
        
    }

    private void initializeScanner(){
        if (!initialScanner){
            islandScanner = new ScanIsland(ground.getcurrentDirection(), initialDirection);
            initialScanner = true;

        }
    }
    private void positionTracker(JSONObject decision){
        if(decision.similar(command.TurnLeft(mapDirection))){
            logger.info("GOING LEFT");
            logger.info("{}",command.TurnLeft(mapDirection).toString(2));
            position = position.turnLeft(mapDirection);
            mapDirection = mapDirection.goLeft();

        } else if (decision.similar(command.TurnRight(mapDirection))){
            logger.info("GOING RIGHT");
            logger.info("{}",command.TurnRight(mapDirection).toString(2));
            position = position.turnRight(mapDirection);
            mapDirection = mapDirection.goRight();

        }else if (decision.similar(command.fly())){
            logger.info("GOING FORWARD");
            logger.info("MAP DIRECTION {}", mapDirection.directionToString());
            logger.info("{}",command.fly().toString(2));
            position = position.forward(mapDirection);
        }
        logger.info(" X COORDINATE {}", position.getX());
        logger.info(" Y COORDINATE {}", position.getY());

    }

    private void mapUpdate(Information info){
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
    private String findClosestCreek(){
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

    public void resultCheck(Information info){ 
        Integer cost = info.getCost();
        battery.loseJuice(cost);
        logger.info("BATTERY CHECK: {}", battery.getCharge());
        ground.resultCheck(info);
        if (ground.getonGround()){
            initializeScanner();
            currentDirection = ground.getcurrentDirection();
            logger.info("CURRENT DIRECTION 1 {}", currentDirection.directionToString());
            islandScanner.resultCheck(info);
        }
        mapUpdate(info);


        
        
    }
    
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();   

        if (battery.lowcheck()){
            decision.put("action", "stop") ;
        }else if(ground.getonGround()){
            decision = islandScanner.makeDecision();

        }else if (!ground.getonGround()){
             decision = ground.makeDecision();
        }
        logger.info("BEGIN MAP");
        logger.info("{}",decision.toString(2));
        positionTracker(decision);

        if(decision.getString("action").equals("stop")){
            logger.info("THE CLOSEST CREEK IS {}", findClosestCreek());
        }
       
        return decision;
    }

    
}
