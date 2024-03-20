package ca.mcmaster.se2aa4.island.team113;
import org.json.JSONObject;

public class EchoRight implements GoToGroundState{

    @Override
    public JSONObject handleNextState(GoToGround context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("OUT_OF_RANGE")){
            decision = command.echo(context.getCurrentDirection().goLeft());
            context.setEchoDirection(context.getCurrentDirection().goLeft());

            context.setState(new EchoLeft());

        }else{
            context.setState(new TurnToGround());
            Direction turndirection = context.getCurrentDirection();
            decision = command.Turn(turndirection.goRight());
        }
        return decision;
    }
    
}
