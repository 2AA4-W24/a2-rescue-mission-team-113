package ca.mcmaster.se2aa4.island.team113;

import javax.sound.sampled.SourceDataLine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MakeDecision {
    private final Logger logger = LogManager.getLogger();
    private Direction currentDirection ;
    CommandCentre command = new Commands();
    Integer charge;
    private DroneBattery battery;
    private Information info;
    GoToGround ground = new GoToGround(currentDirection);


    public MakeDecision(Integer battery, String direction){
        this.currentDirection = Direction.stringToDirection(direction);
        this.battery = new DroneBattery(battery);
    }
    public void resultCheck(Information info){ 
        Integer cost = info.getCost();
        battery.loseJuice(cost);
        logger.info("BATTERY CHECK: {}", battery.getJuice());
        ground.resultCheck(info);
        
    }
    
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();   

        if (battery.lowcheck()){
            decision.put("action", "stop") ;
        }else if(ground.getonGround()){
            //decision = islandscan

        }else if (!ground.getonGround()){
             decision = ground.makeDecision();
        }
        
        return decision;
    }
}
