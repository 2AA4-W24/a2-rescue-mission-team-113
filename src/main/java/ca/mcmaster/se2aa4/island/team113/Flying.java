package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Flying implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.scan();
        context.setState(new Scanning());

        return decision;
    }
    
    
}
