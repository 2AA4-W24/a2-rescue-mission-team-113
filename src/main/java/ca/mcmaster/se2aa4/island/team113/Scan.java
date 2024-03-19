package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Scan implements GoToGroundState{
    private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GoToGround context) {
        

        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                logger.info("CHECKING BIOMES ARRAY");
                decision = command.fly();
                context.setState(new Fly());
                
            } else{
                logger.info("NOT ON OCEAN");
                context.setOnGround(true);
                decision = command.fly();
            }   
        return decision;
       
    }
    
}
