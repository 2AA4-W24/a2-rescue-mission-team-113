package ca.mcmaster.se2aa4.island.team113;

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
}
