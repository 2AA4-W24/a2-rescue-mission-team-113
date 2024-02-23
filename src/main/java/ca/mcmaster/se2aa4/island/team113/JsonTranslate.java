package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class JsonTranslate {
    //read response from decisions, convert it into usable info aka not JSON object

    public Information translate(JSONObject response) {
        Information info = new Information(getRange(response), getBound(response));
        return info;
    }

    private  Integer getRange(JSONObject response){

        JSONObject extract = new JSONObject();
        Integer range = extract.getInt("range");

        return range;
        
    }

    private  JSONObject getBound(JSONObject response){
        JSONObject extract = new JSONObject();
        JSONObject bound = extract.getJSONObject("found");

        return bound;
    }
}
