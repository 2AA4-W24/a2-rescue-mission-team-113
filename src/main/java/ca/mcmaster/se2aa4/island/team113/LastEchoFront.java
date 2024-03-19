package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class LastEchoFront implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();
        Integer range;

        range = extras.getInt("range");
        context.setFlyCount(range);
        decision = command.fly();
        context.setState(new FindGroundRange());

        

        return decision;
    }
    
    
}
