package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MakeDecision {
    private final Logger logger = LogManager.getLogger();
    private Direction currentDirection ;
    private Direction echoDirection ;
    private Direction right;
    private Direction left;
    CommandCentre command = new Commands();
    private boolean echoFront;
    private boolean echoRight;
    private boolean echoLeft;
    private boolean foundGround;
    private boolean echoedAll;
    private Integer range;
    private boolean flyGround;
    private boolean facingGround;
    private int flyingToGround = 0;
    private boolean missioncomplete;
    private boolean checkRange;
    private boolean firstrun;

    public MakeDecision(){
        this.currentDirection = Direction.E;
        this.echoDirection = Direction.E;
        this.echoFront = false;
        this.echoRight = false;
        this.echoLeft = false;
        this.missioncomplete = false;
        this.foundGround = false;
        this.echoedAll = false;
        this.facingGround = false;
        this.checkRange = false;
        this.firstrun =true;
    }




    public Direction getcurrentDirection(){
        return currentDirection;
    }

    

    public void resultCheck(JSONObject extras){
        //only update range while your still looking for ground, so the latest value will be the range of ground
        if (!echoedAll && checkRange){
            logger.info("THE RANGE IS: {}", range);
            range=extras.getInt("range"); //THIS IS THE PROBLEM
            logger.info("THE RANGE IS SECOND: {}", range);
            String found = extras.getString("found");
            logger.info("FOUND VALUE: {}", found);
            if (found.equals("GROUND")){
                foundGround=true;
                echoedAll = true;
            }
        }
        
        
    }
    private JSONObject echoAll(){
        JSONObject decision = new JSONObject();
        right = echoDirection.goRight();
        left = echoDirection.goLeft();

        if (!echoFront && !echoRight && !echoLeft){
            decision = command.echo(this.echoDirection);
            echoFront = true;
            checkRange = true;
            logger.info("ECHO FRONT");
        }else if (echoFront && !echoRight && !echoLeft){
            echoDirection = right;
            logger.info("ECHO DIRECTION {}", echoDirection.directionToString());
            decision = command.echo(echoDirection);
            echoRight = true;
            checkRange = true;
            logger.info("FIRST RIGHT");
        } else if (echoFront && echoRight && !echoLeft){
            echoDirection = left;
            logger.info("ECHO DIRECTION {}", echoDirection.directionToString());
            decision = command.echo(echoDirection);
            echoLeft = true;
            checkRange = true;
            logger.info("FIRST LEFT");
        } else if (echoFront && echoRight && echoLeft){
            logger.info("FLYING");
            checkRange = false;
            decision = command.fly();
            echoFront = false;
            echoRight = false;
            echoLeft = false;
        }
        return decision;
    }

    private JSONObject faceGround(){
        JSONObject decision = new JSONObject();
        logger.info("ECHO DIRECTION: {}", this.echoDirection);
        this.currentDirection= this.echoDirection;
        logger.info("ECHO DIRECTION: {}", this.currentDirection);
        decision = command.Turn(currentDirection); 
        facingGround = true;

        return decision;
    }

    private JSONObject flyToGround(){
        JSONObject decision = new JSONObject();
        
        if (!facingGround){
            decision = faceGround();
        } else if (facingGround){
            if (flyingToGround < range){
                decision = command.fly();
                flyingToGround++;
            } else if (flyingToGround == range){
                decision = command.scan();
                missioncomplete = true;
            }
        }
        return decision;
    }

    public JSONObject makeDecision(){
        JSONObject decision = new JSONObject();
        
        if (missioncomplete){
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
        return decision;
    }
}
