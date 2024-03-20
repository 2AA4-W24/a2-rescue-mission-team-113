package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoToGround implements DecisionMaker{
    private GoToGroundState currentState;
    private Direction currentDirection ;
    private Direction echoDirection ;
    Commands command = new Commands();

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

    public void setFlyCount(Integer flyCount){
        this.flyCount= flyCount;
    }

    public int getFlyCount(){
        return flyCount;
    }

    public Direction getCurrentDirection(){
        return currentDirection;

    }
    public Direction getEchoDirection(){
        return echoDirection;
    }

    public Information getInfo(){
        return info;
    }
    public void setEchoDirection(Direction echoDirection){
        this.echoDirection=echoDirection;

    }

    public boolean getonGround(){
        return onGround;
    }

    public void setOnGround(boolean onGround){
        this.onGround= onGround;

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
            Commands command = new Commands();
            
            decision = command.echo(context.getCurrentDirection());
            context.setEchoDirection(context.getCurrentDirection());
            context.setState(new EchoFront());
    
            return decision;
        }
        
    }

    private class EchoFront implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            Information info = context.getInfo();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.echo(context.getCurrentDirection().goRight());
                context.setEchoDirection(context.getCurrentDirection().goRight());
    
                context.setState(new EchoRight());
    
            }else{
                decision = command.echo(context.getCurrentDirection());
                context.setState(new GroundRange());
            }
    
            return decision;
    
            
        }
        
    }

    private class EchoRight implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            Information info = context.getInfo();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.echo(context.getCurrentDirection().goLeft());
                context.setEchoDirection(context.getCurrentDirection().goLeft());
    
                context.setState(new EchoLeft());
    
            }else{
                context.setState(new TurnToGround());
                Direction turndirection = context.getCurrentDirection();
                decision = command.Turn(turndirection.goRight());
            }
            return decision;
        }
        
    }
    
    private class EchoLeft implements GoToGroundState {
        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            Information info = context.getInfo();
            JSONObject extras = info.getExtras();
    
            if (extras.getString("found").equals("OUT_OF_RANGE")){
                decision = command.fly();
    
                context.setState(new Fly());
    
            }else{
                context.setState(new TurnToGround());
                decision = command.Turn(context.getCurrentDirection().goLeft());
            }
            return decision;
        }
        
    }

    private class GroundRange implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            Information info = context.getInfo();
            JSONObject extras = info.getExtras();
            Integer range;
    
            range = extras.getInt("range");
            context.setFlyCount(range);
            decision = command.fly();
            context.setState(new FlyToGround());
    
            
    
            return decision;
        }
        
        
    }
    private class TurnToGround implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            JSONObject decision = new JSONObject();
            Commands command = new Commands();
            
            decision = command.echo(context.getCurrentDirection());
            context.setState(new GroundRange());
    
            return decision;
        }
        
        
    }
    private class FlyToGround implements GoToGroundState{

        @Override
        public JSONObject handleNextState(GoToGround context) {
            Commands command = new Commands();
            JSONObject decision = new JSONObject();
            
            if(context.getFlyCount() > 0){
                decision = command.fly();
                context.setFlyCount(context.getFlyCount()- 1);
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
        Commands command = new Commands();
        Information info = context.getInfo();
        JSONObject extras = info.getExtras();

        JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1 && ("OCEAN").equals(biomesArray.optString(0))){
                logger.info("CHECKING BIOMES ARRAY");
                decision = command.fly();
                context.setState(new Fly());
                
            } else{
                logger.info("NOT ON OCEAN");
                context.setOnGround(true);
                decision = command.fly();
            }   
        return decision;
       
    }
    
}

    


    
    


}
