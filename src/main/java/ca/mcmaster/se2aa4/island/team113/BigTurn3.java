package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class BigTurn3 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.fly();
        context.setState(new Flying());

        return decision;
    }

    
}
