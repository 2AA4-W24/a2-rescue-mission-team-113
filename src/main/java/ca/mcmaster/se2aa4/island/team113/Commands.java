package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Commands {
    private final Logger logger = LogManager.getLogger();

    
    public JSONObject echo(Direction direction) {
        JSONObject decision = new JSONObject();
        
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", direction.directionToString()));
        return decision;
    }

    
    public JSONObject TurnRight(Direction direction) {
        JSONObject decision = new JSONObject();
        String right = direction.goRight().directionToString();
        decision.put("action", "turn");
        decision.put("parameters", new JSONObject().put("direction", right));
        return decision;
    }

    
    public JSONObject TurnLeft(Direction direction) {
        JSONObject decision = new JSONObject();
        String left = direction.goLeft().directionToString();
        decision.put("action", "turn");
        decision.put("parameters", new JSONObject().put("direction", left));
        return decision;
    }
    
    public JSONObject Turn(Direction direction) {
        JSONObject decision = new JSONObject();
        String turn = direction.directionToString();
        logger.info("TURN DIRECTION {}", turn);
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", turn));
        return decision;
    }

    
    public JSONObject fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        return decision;
    }

    
    public JSONObject scan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision;
    }
    
}
