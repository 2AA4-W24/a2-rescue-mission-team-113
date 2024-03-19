package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class GoToGround implements DecisionMaker{
    private GoToGroundState currentState;

    private final Logger logger = LogManager.getLogger();
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
   
   

    /*

    private JSONObject echoAll(){
        JSONObject decision = new JSONObject();
        right = echoDirection.goRight();
        left = echoDirection.goLeft();

        if (!echoFront && !echoRight && !echoLeft){
            decision = command.echo(this.echoDirection);
            echoFront = true;
            logger.info("ECHO FRONT");
        }else if (echoFront && !echoRight && !echoLeft){
            echoDirection = right;
            logger.info("ECHO DIRECTION {}", echoDirection.directionToString());
            decision = command.echo(echoDirection);
            echoRight = true;
            logger.info("FIRST RIGHT");
        } else if (echoFront && echoRight && !echoLeft){
            echoDirection = left;
            logger.info("ECHO DIRECTION {}", echoDirection.directionToString());
            decision = command.echo(echoDirection);
            echoLeft = true;
            logger.info("FIRST LEFT");
        } else if (echoFront && echoRight && echoLeft){
            logger.info("FLYING");
            decision = command.fly();
            echoFront = false;
            echoRight = false;
            echoLeft = false;
        }
        return decision;
    }

    private JSONObject faceGround(){
        JSONObject decision = new JSONObject();
        if (echoDirection == currentDirection){
            decision = command.scan();
            facingGround=true;
        }else{
            logger.info("ECHO DIRECTION: {}", this.echoDirection);
            this.currentDirection= this.echoDirection;
            logger.info("ECHO DIRECTION: {}", this.currentDirection);
            decision = command.Turn(currentDirection); 
            facingGround = true;

        }
        return decision;
    }

    private JSONObject flyToGround(){
        JSONObject decision = new JSONObject();
        
        if (!facingGround){
            decision = faceGround();
        } else if (facingGround){
            if (flyCount < range){
                decision = command.fly();
                flyCount++;
            } else if (flyCount == range){
                decision = command.scan();
                onGround = true;
            }
        }
        return decision;
    }

    */
    public void resultCheck(Information info){
        
        JSONObject extras = info.getExtras();
        this.info = info;

        /*if (extras.has("range")){
            range = extras.getInt("range");
            logger.info("RANGE : {}", range); 
            String found = extras.getString("found");
            logger.info("FOUND VALUE: {}", found);

            if (found.equals("GROUND")){
                foundGround=true;
            }
        }
        */
    }
    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();

        if(!onGround){
            decision = currentState.handleNextState(this);
        }
        map.positionTracker(decision);
        this.currentDirection= map.getcurrentDirection();

        
        
        /*if (onGround){
            decision.put("action", "stop") ;
        }else if (firstrun == true){
            decision = command.echo(echoDirection);
            this.firstrun = false;
        }else {
            if (!foundGround){
                decision = echoAll();
            } else {
                decision = flyToGround();
            }
        }
        */
        return decision;
    }
}
