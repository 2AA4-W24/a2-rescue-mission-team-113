package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class JsonTranslate {
    //read response from decisions, convert it into usable info aka not JSON object

    public Information translate(JSONObject response) {
        Information info = new Information(getCost(response), getExtras(response));
        return info;
    }

    private  Integer getCost(JSONObject response){

        JSONObject extract = new JSONObject();
        Integer cost = extract.getInt("cost");

        return cost;
        
    }

    private  JSONObject getExtras(JSONObject response){
        JSONObject extract = new JSONObject();
        JSONObject extras = extract.getJSONObject("extras");

        return extras;
    }
}
