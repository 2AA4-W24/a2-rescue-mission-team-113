package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements DecisionMaker{
    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    Commands command = new Commands();
    private Direction currentDirection;
    private int edgeCounter;
    private boolean goingRight;
    private PositionTrack map;
    private GridSearchStates currentState;
    private Information info;


    public GridSearch(Direction currentDirection, Direction initialDirection){

        this.currentDirection = currentDirection;
        this.edgeCounter = 0;
        this.goingRight=true;
        this.map = new PositionTrack(currentDirection);
        this.currentState = new Flying();

    }
    
    public void setState(GridSearchStates state) {
        this.currentState = state;
    }

    public void resultCheck(Information info) {  

        this.info = info;
        
    }

    public JSONObject makeDecision() {
        
        logger.info("CUREENT DIRECTION {}", currentDirection.directionToString());
        decision = currentState.handleNextState(this);
        map.positionTracker(decision);
        this.currentDirection= map.getcurrentDirection();
        return decision;
    }

    private interface GridSearchStates {
        JSONObject handleNextState(GridSearch context);
    }

    private class Flying implements GridSearchStates{

        @Override
        public JSONObject handleNextState(GridSearch context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            
            decision = command.scan();
            context.setState(new Scanning());
    
            return decision;
        }
        
    }

    private class Scanning implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                decision = command.echo(currentDirection);
                context.setState(new CheckGround());
            } else{
                decision = command.fly();
                context.setState(new Flying());
            }      

        return decision;
    }
    
}

private class CheckGround implements GridSearchStates{
    private final Logger logger = LogManager.getLogger();

   @Override
   public JSONObject handleNextState(GridSearch context) {
       JSONObject decision = new JSONObject();
       Commands command = new Commands();
       JSONObject extras = info.getExtras();

       if (extras.getString("found").equals("GROUND")){
           decision = command.fly();
           context.setState(new Flying());

       }else{
           logger.info("NO GROUND FOUND CASE");
           Direction temp = currentDirection;
           logger.info("TEMP DIRECTION {}", temp.directionToString());
           if (goingRight){
               
               decision = command.Turn(temp.goRight());
           }else{
               decision = command.Turn(temp.goLeft());
           }
           context.setState(new Uturn1());
       }

       

       return decision;
   }
}
private class Uturn1 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Direction temp = currentDirection;

        if (goingRight){
                
            decision = command.Turn(temp.goRight());
        }else{
            decision = command.Turn(temp.goLeft());
        }
        context.setState(new Uturn2());
        goingRight= !goingRight;

        return decision;
    }
    
    
}
private class Uturn2 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.echo(currentDirection);
        context.setState(new CheckEdge());

        return decision;
    }
    
    
}
private class CheckEdge implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        JSONObject extras = info.getExtras();

        if (extras.getString("found").equals("GROUND")){
            decision = command.fly();
            context.setState(new Flying());

        }else{
            edgeCounter++;
            context.setState(new AtEdge());
            decision = command.fly();
        }
        return decision;
    }
    
    
}

private class AtEdge implements GridSearchStates{

    private final Logger logger = LogManager.getLogger();
    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("AT EDGE");
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        logger.info("AT EDGE COUNT {}", edgeCounter);
        
        if(edgeCounter >= 3){
            decision = decision.put("action", "stop");

        }else{
            Direction temp = currentDirection;
            if (goingRight){
                logger.info("AT EDGE ECHO LEFT");
                
                decision = command.echo(temp.goLeft());
            }else{
                logger.info("AT EDGE ECHO RIGHT");
                decision = command.echo(temp.goRight());
            }

            context.setState(new EchoSide());

        }
        

        return decision;
    }
    
    
}

private class EchoSide implements GridSearchStates{
    private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();

        JSONObject extras = info.getExtras();
        Direction temp = currentDirection;

        if (extras.getString("found").equals("GROUND")){
            logger.info("ECHO SIDE GROUND FOUND");
            decision = command.fly();
            context.setState(new AtEdge());

        }else{
            logger.info("ECHO SIDE NO GROUND FOUND");
            goingRight= !goingRight;

            if (goingRight){
                decision = command.Turn(temp.goRight());
            }else{
                decision = command.Turn(temp.goLeft());
            }

            context.setState(new BigTurn1());
            
        }
        return decision;
    }
    
}
private class BigTurn1 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.fly();
        context.setState(new BigTurn2());

        return decision;
    }
    
}

private class BigTurn2 implements GridSearchStates{
    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        Direction temp = currentDirection;
        
        if (goingRight){
            decision = command.Turn(temp.goRight());
        }else{
            decision = command.Turn(temp.goLeft());
        }
        goingRight= !goingRight;

        context.setState(new BigTurn3());
        return decision;
    }
}
private class BigTurn3 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject decision = new JSONObject();
        Commands command = new Commands();
        
        decision = command.fly();
        context.setState(new Flying());

        return decision;
    } 
}


}
