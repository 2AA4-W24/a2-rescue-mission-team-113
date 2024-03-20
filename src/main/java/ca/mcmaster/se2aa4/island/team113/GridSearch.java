package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public Information getInfo(){
        return info;
    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }

    public boolean getGoingRight(){
        return goingRight;
    }

    public int getEdgeCount(){
        return edgeCounter;
    }
    public void setEdgeCount(int edgeCount){
        this.edgeCounter = edgeCount;
    }

    public void setGoingRight(boolean goingRight){
        this.goingRight = goingRight;
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
  
}
