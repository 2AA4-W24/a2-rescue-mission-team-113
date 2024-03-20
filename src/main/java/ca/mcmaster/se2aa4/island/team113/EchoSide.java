package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class EchoSide implements GridSearchStates{
    private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();
        Direction temp = context.getCurrentDirection();

        if (extras.getString("found").equals("GROUND")){
            logger.info("ECHO SIDE GROUND FOUND");
            decision = command.fly();
            context.setState(new AtEdge());

        }else{
            logger.info("ECHO SIDE NO GROUND FOUND");
            context.setGoingRight(!context.getGoingRight());

            if (context.getGoingRight()){
                decision = command.Turn(temp.goRight());
            }else{
                decision = command.Turn(temp.goLeft());
            }

            context.setState(new BigTurn1());
            
        }
        return decision;
    }
    
}
