package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class FlyToGround implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();

        decision = command.scan();
        context.setState(new Scan());


        return decision;
    }
    
    
}
