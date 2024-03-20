package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONArray;
import org.json.JSONObject;

public class Scanning implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                decision = command.echo(context.getCurrentDirection());
                context.setState(new CheckGround());
            } else{
                decision = command.fly();
                context.setState(new Flying());
            }      

        return decision;
    }
    
}
