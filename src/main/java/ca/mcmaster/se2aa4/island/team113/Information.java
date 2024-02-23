package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Information {
    private int cost;
    private JSONObject extras;

    public Information(int cost, JSONObject extras) {
        this.cost = cost;
        this.extras = extras;
    }

    public JSONObject getExtras() {
        return extras;
    }

    public int getCost() {
        return cost;
    }
}
