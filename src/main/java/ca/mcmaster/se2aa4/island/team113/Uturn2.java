package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Uturn2 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.echo(context.getCurrentDirection());
        context.setState(new CheckEdge());

        return decision;
    }
    
    
}

