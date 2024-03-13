package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Information {
    private Integer cost;
    private JSONObject extras;

    public Information(Integer cost, JSONObject extras) {
        this.cost = cost;
        this.extras = extras;
    }

    public JSONObject getExtras() {
        return extras;
    }

    public Integer getCost() {
        return cost;
    }
}
