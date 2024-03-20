package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Fly implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.echo(context.getCurrentDirection());
        context.setEchoDirection(context.getCurrentDirection());
        context.setState(new EchoFront());

        return decision;
    }
    
}