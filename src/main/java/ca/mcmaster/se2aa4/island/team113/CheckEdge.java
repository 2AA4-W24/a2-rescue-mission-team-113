package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class CheckEdge implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("GROUND")){
            decision = command.fly();
            context.setState(new Flying());

        }else{
            context.setEdgeCount(context.getEdgeCount() + 1);
            context.setState(new AtEdge());
            decision = command.fly();
        }
        return decision;
    }
    
    
}
