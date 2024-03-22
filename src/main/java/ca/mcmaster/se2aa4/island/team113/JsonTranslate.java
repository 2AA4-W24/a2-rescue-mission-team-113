package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class JsonTranslate {
    //read response from decisions, convert it into usable info aka not JSON object

    public Information translate(JSONObject response) {

        return new Information(getCost(response), getExtras(response));
    }

    private  Integer getCost(JSONObject response){

        return response.getInt("cost");
    }

    private  JSONObject getExtras(JSONObject response){

        return  response.getJSONObject("extras");
    }
}
