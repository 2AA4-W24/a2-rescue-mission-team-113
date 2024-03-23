package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public interface DecisionMaker {
    JSONObject makeDecision();
    public void resultCheck(Information info);
    Direction getCurrentDirection();
    boolean getCompleted();




}
