package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class AtEdge implements GridSearchStates{

    private final Logger logger = LogManager.getLogger();
    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("AT EDGE");
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        logger.info("AT EDGE COUNT {}", context.getEdgeCount());
        
        if(context.getEdgeCount() >= 3){
            decision = decision.put("action", "stop");

        }else{
            Direction temp = context.getCurrentDirection();
            if (context.getGoingRight()){
                logger.info("AT EDGE ECHO LEFT");
                
                decision = command.echo(temp.goLeft());
            }else{
                logger.info("AT EDGE ECHO RIGHT");
                decision = command.echo(temp.goRight());
            }

            context.setState(new EchoSide());

        }
        

        return decision;
    }
    
    
}
