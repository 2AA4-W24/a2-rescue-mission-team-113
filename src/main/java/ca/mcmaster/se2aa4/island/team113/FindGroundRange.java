package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class FindGroundRange implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        Commands command = new Commands();
        JSONObject decision = new JSONObject();
        
        if(context.getFlyCount() > 0){
            decision = command.fly();
            context.setFlyCount(context.getFlyCount()- 1);
            context.setState(new FindGroundRange());
        }else{
            decision = command.scan();


            context.setState(new FlyToGround());

        }
        

        

        return decision;
    }

    
}
