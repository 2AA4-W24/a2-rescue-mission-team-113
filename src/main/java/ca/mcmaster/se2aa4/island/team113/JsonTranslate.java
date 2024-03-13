package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonTranslate {
    //read response from decisions, convert it into usable info aka not JSON object
    private final Logger logger = LogManager.getLogger();

    public Information translate(JSONObject response) {
        Information info = new Information(getCost(response), getExtras(response));
        return info;
    }

    private  Integer getCost(JSONObject response){

        logger.info("CHECKPINT TRANSLATE");
        Integer cost = response.getInt("cost");
        logger.info("CHECKPINT TRANSLATE 2");

        return cost;
        
    }

    private  JSONObject getExtras(JSONObject response){
        
        JSONObject extras = response.getJSONObject("extras");
        return extras;
    }
}
