package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoToGround implements DecisionMaker{
    private GoToGroundState currentState;
    private Direction currentDirection ;
    private Direction echoDirection ;
    private Commands command = new Commands();

    private int flyCount = 0;
    private boolean onGround;
    private Information info;

    private PositionTrack map;

    public GoToGround(Direction direction){
        this.currentDirection = direction;
        this.echoDirection = direction;
        this.onGround = false;
        this.map = new PositionTrack(direction);
        this.currentState = new Fly();
    }
    public void setState(GoToGroundState state) {
        this.currentState = state;
    }

    public Direction getCurrentDirection(){
        return currentDirection;

    }

    public boolean getonGround(){
        return onGround;
    }

    public void resultCheck(Information info){
        this.info = info;

    }
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();

        if(!onGround){
            decision = currentState.handleNextState(this);
        }
        map.positionTracker(decision);
        this.currentDirection= map.getcurrentDirection();
        return decision;
    }

    private interface GoToGroundState {
        JSONObject handleNextState(GoToGround context);
    }
    

    private class Fly implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            
            decision = command.echo(currentDirection);
            echoDirection= currentDirection;
            context.setState(new EchoFront());
    
            return decision;
        }
        
    }

    private class EchoFront implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.echo(currentDirection.goRight());
                echoDirection= currentDirection.goRight();
    
                context.setState(new EchoRight());
    
            }else{
                decision = command.echo(currentDirection);
                context.setState(new GroundRange());
            }
    
            return decision;
    
            
        }
        
    }

    private class EchoRight implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.echo(currentDirection.goLeft());
                echoDirection= currentDirection.goLeft();
    
                context.setState(new EchoLeft());
    
            }else{
                context.setState(new TurnToGround());
                Direction turndirection = currentDirection;
                decision = command.Turn(turndirection.goRight());
            }
            return decision;
        }
        
    }
    
    private class EchoLeft implements GoToGroundState {
        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.fly();
    
                context.setState(new Fly());
    
            }else{
                context.setState(new TurnToGround());
                decision = command.Turn(currentDirection.goLeft());
            }
            return decision;
        }
        
    }

    private class GroundRange implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            JSONObject extras = info.getExtras();
            Integer range;
    
            range = extras.getInt("range");
            flyCount= range;
            decision = command.fly();
            context.setState(new FlyToGround());
    
            
    
            return decision;
        }
        
        
    }
    private class TurnToGround implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            
            decision = command.echo(currentDirection);
            context.setState(new GroundRange());
    
            return decision;
        }
        
        
    }
    private class FlyToGround implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            
            if(flyCount > 0){
                decision = command.fly();
                flyCount--;
                context.setState(new FlyToGround());
            }else{
                decision = command.scan();
                context.setState(new Scan());
    
            }
    
            return decision;
        }
    
        
    }
    private class Scan implements GoToGroundState{
    private final Logger logger = LogManager.getLogger();

    @Override
    public JSONObject handleNextState(GoToGround context) {   

        JSONObject decision = new JSONObject();
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                logger.info("CHECKING BIOMES ARRAY");
                decision = command.fly();
                context.setState(new Fly());
                
            } else{
                logger.info("NOT ON OCEAN");
                onGround= true;
                decision = command.fly();
            }   
        return decision;
       
    }
    
}


}
