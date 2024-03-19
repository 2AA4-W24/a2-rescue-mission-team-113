package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class EchoRight implements GoToGroundState{
    private final Logger logger = LogManager.getLogger();

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
            logger.info("FOUND GROUND STATE ECHORIGHT");
            context.setState(new TurnToGround());
            logger.info("CHECKPOINT 1");
            Direction turndirection = context.getCurrentDirection();
            decision = command.Turn(turndirection.goRight());
            logger.info("CHECKPOINT 2");
        }
        return decision;
    }
    
}
