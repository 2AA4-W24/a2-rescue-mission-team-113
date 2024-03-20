package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class CheckGround implements GridSearchStates{
     private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("GROUND")){
            decision = command.fly();
            context.setState(new Flying());

        }else{
            logger.info("NO GROUND FOUND CASE");
            Direction temp = context.getCurrentDirection();
            logger.info("TEMP DIRECTION {}", temp.directionToString());
            if (context.getGoingRight()){
                
                decision = command.Turn(temp.goRight());
            }else{
                decision = command.Turn(temp.goLeft());
            }
            context.setState(new Uturn1());
        }

        

        return decision;
    }
}
