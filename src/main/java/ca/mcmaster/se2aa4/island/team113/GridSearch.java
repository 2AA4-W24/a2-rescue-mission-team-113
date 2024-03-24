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
    private int range;
    private int flyCount;
    private String foundstr = "found";
    private String groundstr = "GROUND";
    private String rangestr = "range";
    private boolean completed;



    public GridSearch(Direction currentDirection){
        this.currentDirection = currentDirection;
        this.edgeCounter = 0;
        this.goingRight=true;
        this.map = new PositionTrack(currentDirection);
        this.currentState = new Fly();

    }

    private void setState(GridSearchStates state) {
        this.currentState = state;
    }

    public void setStatePublic(GridSearchStates state) { //for testing
        this.currentState = state;
    }

    public GridSearchStates getScanState(){ //for testing
        return new Scan();
    }

    public GridSearchStates getFlyState(){ //for testing
        return new Fly();
    }

    public GridSearchStates getCheckGroundState(){ //for testing
        return new CheckGround();
    }

    public GridSearchStates getUturn1State(){ //for testing
        return new Uturn1();
    }

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
}

    @Override
    public boolean getCompleted() {
        return completed;
}
    @Override
    public void resultCheck(Information info) {  
        this.info = info; 
    }
    @Override
    public JSONObject makeDecision() {
        
        decision = currentState.handleNextState(this);
        map.positionTracker(decision);
        this.currentDirection= map.getcurrentDirection();
        return decision;
    }

    private interface GridSearchStates {
        JSONObject handleNextState(GridSearch context);
    }

    private class Fly implements GridSearchStates{

        @Override
        public JSONObject handleNextState(GridSearch context) {

            flyCount++;
            decision = command.scan();
            context.setState(new Scan());
    
            return decision;
        }
        
    }

    private class Scan implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                decision = command.echo(currentDirection);
                context.setState(new CheckGround());
            } else{
                decision = command.fly();
                context.setState(new Fly());
            }      

        return decision;
    }
    
}

private class CheckGround implements GridSearchStates{
    private final Logger logger = LogManager.getLogger();

   @Override
   public JSONObject handleNextState(GridSearch context) {
       JSONObject extras = info.getExtras();

       if (extras.getString(foundstr).equals(groundstr)){
           range = extras.getInt(rangestr);
           decision = command.fly();
           flyCount += range;
           context.setState(new FlyRange());

       }else{
           logger.info("NO GROUND FOUND CASE");

           range = extras.getInt(rangestr) - 4;
           flyCount += range;
           decision = command.scan();
           context.setState(new FlyToMapEdge());


           
       }

       return decision;
   }
}
private class FlyRange implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        if(range > 0){
            decision = command.fly();
            range--;
            context.setState(new FlyRange());
        }else{
            decision= command.scan();
            context.setState(new Scan());
        }

        return decision;
    }
    
}

private class FlyToMapEdge implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        Direction temp = currentDirection;

        if (range >0){
            decision= command.fly();
            range--;
            context.setState(new FlyToMapEdge());
        }else{
            if (goingRight){
               
                decision = command.turn(temp.goRight());
            }else{
                decision = command.turn(temp.goLeft());
            }
            context.setState(new Uturn1());
        }
        return decision;
    }
    
}


private class Uturn1 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        Direction temp = currentDirection;

        if (goingRight){
                
            decision = command.turn(temp.goRight());
        }else{
            decision = command.turn(temp.goLeft());
        }
        context.setState(new Uturn2());
        goingRight= !goingRight;

        return decision;
    }
    
    
}
private class Uturn2 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        
        decision = command.echo(currentDirection);
        context.setState(new CheckEdge());

        return decision;
    }
    
    
}
private class CheckEdge implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("CHECK EDGE");
        JSONObject extras = info.getExtras();
        

        if (extras.getString(foundstr).equals(groundstr)){
            range = extras.getInt(rangestr);
            decision = command.fly();
            flyCount =0;
            context.setState(new FlyRange());

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
        logger.info("AT EDGE COUNT {}", edgeCounter);
        
        if(edgeCounter >= 3){
            decision = decision.put("action", "stop");
            completed = true;

        }else{
            if (flyCount > 0){
                flyCount--;
                decision = command.fly();
                context.setState(new AtEdge());
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

        }
        

        return decision;
    }
    
    
}

private class EchoSide implements GridSearchStates{
    private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("ECHO SIDE");
        JSONObject extras = info.getExtras();
        Direction temp = currentDirection;

        if (extras.getString(foundstr).equals(groundstr)){
            logger.info("ECHO SIDE GROUND FOUND");
            decision = command.fly();
            context.setState(new FlyBack());

        }else{
            logger.info("ECHO SIDE NO GROUND FOUND");
            goingRight= !goingRight;

            if (goingRight){
                decision = command.turn(temp.goRight());
            }else{
                decision = command.turn(temp.goLeft());
            }

            context.setState(new BigTurn1());
            
        }
        return decision;
    }
    
}

private class FlyBack implements GridSearchStates{
    
    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("ECHO SIDE");

    Direction temp = currentDirection;
    if (goingRight){
        logger.info("AT EDGE ECHO LEFT");
        
        decision = command.echo(temp.goLeft());
    }else{
        logger.info("AT EDGE ECHO RIGHT");
        decision = command.echo(temp.goRight());
    }

    context.setState(new EchoSide());

    return decision;
    }
    
}

private class BigTurn1 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("BIG TURN 1");
        
        decision = command.fly();
        context.setState(new BigTurn2());

        return decision;
    }
    
}

private class BigTurn2 implements GridSearchStates{
    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("BIG TURN 2");
        Direction temp = currentDirection;
        
        if (goingRight){
            decision = command.turn(temp.goRight());
        }else{
            decision = command.turn(temp.goLeft());
        }
        goingRight= !goingRight;

        context.setState(new BigTurn3());
        return decision;
    }
}
private class BigTurn3 implements GridSearchStates{

    @Override
    public JSONObject handleNextState(GridSearch context) {
        logger.info("BIG TURN 3");

        decision = command.fly();
        context.setState(new Fly());

        return decision;
    } 
}



}