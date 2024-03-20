package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public class Uturn1 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Direction temp = context.getCurrentDirection();

        if (context.getGoingRight()){
                
            decision = command.Turn(temp.goRight());
        }else{
            decision = command.Turn(temp.goLeft());
        }
        context.setState(new Uturn2());
        context.setGoingRight(!context.getGoingRight());

        return decision;
    }
    
    
}
