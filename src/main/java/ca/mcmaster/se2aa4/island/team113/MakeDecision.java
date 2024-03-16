package ca.mcmaster.se2aa4.island.team113;

import javax.sound.sampled.SourceDataLine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


    public MakeDecision(Integer battery, String direction){
        this.initialDirection = Direction.stringToDirection(direction);
        this.battery = new DroneBattery(battery);
        this.initialScanner= false;
        ground = new GoToGround(initialDirection);
        
        
    }

    private void initializeScanner(){
        if (!initialScanner){
            islandScanner = new ScanIsland(ground.getcurrentDirection(), initialDirection);
            initialScanner = true;

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
        
        
    }
    
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();   

        if (battery.lowcheck()){
            decision.put("action", "stop") ;
        }else if(ground.getonGround()){
            logger.info("CURRENT DIRECTION 2 {}", currentDirection.directionToString());
            decision = islandScanner.makeDecision();

        }else if (!ground.getonGround()){
             decision = ground.makeDecision();
        }
        
        return decision;
    }
}
