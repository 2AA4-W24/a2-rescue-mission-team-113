package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class EchoFront implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("OUT_OF_RANGE")){
            decision = command.echo(context.getCurrentDirection().goRight());
            context.setEchoDirection(context.getCurrentDirection().goRight());

            context.setState(new EchoRight());

        }else{
            decision = command.echo(context.getCurrentDirection());
            context.setState(new LastEchoFront());
        }

        

        return decision;

        
    }
    
    
}
