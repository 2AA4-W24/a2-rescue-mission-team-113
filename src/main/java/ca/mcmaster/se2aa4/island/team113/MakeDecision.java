package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class MakeDecision {
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


    public MakeDecision(Integer battery, String direction){
        this.initialDirection = Direction.stringToDirection(direction);
        this.battery = new DroneBattery(battery);
        this.initialScanner= false;
        this.mapDirection= initialDirection;
        this.map = new MapOfPoints();
        this.position = new Coordinate(0,0);
        ground = new GoToGround(initialDirection);
        
        
    }

    private void initializeScanner(){
        if (!initialScanner){
            islandScanner = new ScanIsland(ground.getcurrentDirection(), initialDirection);
            initialScanner = true;

        }
    }
    private void posiitonTracker(JSONObject decision){
        if(decision.similar(command.TurnLeft(mapDirection))){
            logger.info("GOING LEFT");
            logger.info("{}",command.TurnLeft(mapDirection).toString(2));
            mapDirection = mapDirection.goLeft();

        } else if (decision.similar(command.TurnRight(mapDirection))){
            logger.info("GOING RIGHT");
            logger.info("{}",command.TurnRight(mapDirection).toString(2));
            mapDirection = mapDirection.goRight();

        }else if (decision.similar(command.fly())){
            logger.info("GOING FORWARD");
            logger.info("MAP DIRECTION {}", mapDirection.directionToString());
            logger.info("{}",command.fly().toString(2));
            position = position.move(mapDirection);
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

    public void resultCheck(Information info){ 
        Integer cost = info.getCost();
        battery.loseJuice(cost);
        logger.info("BATTERY CHECK: {}", battery.getJuice());
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
        posiitonTracker(decision);
       
        return decision;
    }

    
}
