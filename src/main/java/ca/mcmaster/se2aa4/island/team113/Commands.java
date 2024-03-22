package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Commands {
    private final Logger logger = LogManager.getLogger();
    private String actionstr = "action";
    private String directionstr = "direction";
    private String parameterstr = "parameters";
    private String headingstr = "heading";


    
    public JSONObject echo(Direction direction) {
        JSONObject decision = new JSONObject();
        
        decision.put(actionstr, "echo");
        decision.put(parameterstr, new JSONObject().put(directionstr, direction.directionToString()));
        return decision;
    }

    
    public JSONObject turnRight(Direction direction) {
        JSONObject decision = new JSONObject();
        String right = direction.goRight().directionToString();
        decision.put(actionstr, headingstr);
        decision.put(parameterstr, new JSONObject().put(directionstr, right));
        return decision;
    }

    
    public JSONObject turnLeft(Direction direction) {
        JSONObject decision = new JSONObject();
        String left = direction.goLeft().directionToString();
        decision.put(actionstr, headingstr);
        decision.put(parameterstr, new JSONObject().put(directionstr, left));
        return decision;
    }
    
    public JSONObject turn(Direction direction) {
        JSONObject decision = new JSONObject();
        String turn = direction.directionToString();
        logger.info("TURN DIRECTION {}", turn);
        decision.put(actionstr, headingstr);
        decision.put(parameterstr, new JSONObject().put(directionstr, turn));
        return decision;
    }

    
    public JSONObject fly() {
        JSONObject decision = new JSONObject();
        decision.put(actionstr, "fly");
        return decision;
    }

    
    public JSONObject scan() {
        JSONObject decision = new JSONObject();
        decision.put(actionstr, "scan");
        return decision;
    }
    
}
