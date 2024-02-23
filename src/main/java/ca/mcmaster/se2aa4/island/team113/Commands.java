package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Commands implements CommandCentre{

    private Direction echoDirection;

    public Commands(Direction echoDirection){
        this.echoDirection = echoDirection;
    }
    @Override
    public JSONObject echo(Direction echoDirection) {
        JSONObject decision = new JSONObject();
        
        decision.put("action", "echo");
        decision.put("parameters", new JSONObject().put("direction", echoDirection));
        return decision;
    }

    @Override
    public JSONObject TurnRight() {
        JSONObject decision = new JSONObject();
        String right = echoDirection.goRight().directionToString();
        decision.put("action", "turn");
        decision.put("parameters", new JSONObject().put("direction", right));
        return decision;
    }

    @Override
    public JSONObject TurnLeft() {
        JSONObject decision = new JSONObject();
        String left = echoDirection.goLeft().directionToString();
        decision.put("action", "turn");
        decision.put("parameters", new JSONObject().put("direction", left));
        return decision;
    }

    @Override
    public JSONObject fly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        return decision;
    }

    @Override
    public JSONObject scan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        return decision;
    }
    
}
