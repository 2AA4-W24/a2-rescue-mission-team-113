package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class EchoLeft implements GoToGroundState {
    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("OUT_OF_RANGE")){
            decision = command.fly();

            context.setState(new Fly());

        }else{
            context.setState(new TurnToGround());
            decision = command.Turn(context.getCurrentDirection().goLeft());
        }
        return decision;
    }
    
}
