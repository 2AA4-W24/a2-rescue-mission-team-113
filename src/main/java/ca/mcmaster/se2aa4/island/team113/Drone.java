package ca.mcmaster.se2aa4.island.team113;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Drone {
    private final Logger logger = LogManager.getLogger();
    private Direction currentDirection ;
    Integer charge;
    private DroneBattery battery;
    GoToGround ground;
    GridSearch islandScanner;
    private boolean initialScanner;
    private Direction initialDirection;
    private PositionTrack mapTrack;


    public Drone(Integer battery, String direction){
        this.initialDirection = Direction.stringToDirection(direction);
        this.battery = new DroneBattery(battery);
        this.initialScanner= false;
        ground = new GoToGround(initialDirection);
        this.mapTrack = new PositionTrack(initialDirection);
        
    }

    public Coordinate getCurrentPosition(){
        return mapTrack.getCurrentCoordinate();
    }

    private void initializeScanner(){
        if (!initialScanner){
            islandScanner = new GridSearch(ground.getCurrentDirection(), initialDirection);
            initialScanner = true;

        }
    }

    public void resultCheck(Information info){ 
        Integer cost = info.getCost();
        battery.loseJuice(cost);
        logger.info("BATTERY CHECK: {}", battery.getCharge());
        ground.resultCheck(info);
        if (ground.getonGround()){
            initializeScanner();
            currentDirection = ground.getCurrentDirection();
            islandScanner.resultCheck(info);
        }
        mapTrack.mapUpdate(info);
        
    }
    public String closestCreek(){

        logger.info("THE CLOSEST CREEK IS {}", mapTrack.findClosestCreek());

        return mapTrack.findClosestCreek();
    }
    
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();   

        if (battery.lowcheck()){
            decision.put("action", "stop") ;
        }else if(ground.getonGround()){
            logger.info("ON GROUND IS TRUE");
            decision = islandScanner.makeDecision();

        }else if (!ground.getonGround()){
            logger.info("ON GROUND IS FALSE");
             decision = ground.makeDecision();
        }
        mapTrack.positionTracker(decision);

        //if(decision.getString("action").equals("stop")){
          //  logger.info("THE CLOSEST CREEK IS {}", mapTrack.findClosestCreek());
            //return mapTrack.findClosestCreek();
        //}

        //closestCreek(decision);

        
       
        return decision;
    }

    
}
