package ca.mcmaster.se2aa4.island.team113;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    public int runs =0;
    public Information information;
    private JsonTranslate translator;
    


    @Override
    public void initialize(String s) {

        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        
        String direction = info.getString("heading");
        Direction currentDirection = Direction.stringToDirection(direction);
        logger.info("ENUM INITIALIZE : {}", currentDirection.directionToString());
        Integer batteryLevel = info.getInt("budget");
        
        drone = new Drone(batteryLevel, direction);
        
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        decision = drone.makeDecision();
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }   


    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        JsonTranslate translator = new JsonTranslate();
        //info = translator.translate(response);
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        information = translator.translate(response);
        drone.resultCheck(information);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
