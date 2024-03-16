package ca.mcmaster.se2aa4.island.team113;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScanIsland implements DecisionMaker{
    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    Commands command = new Commands();
    private boolean fly;
    private boolean scan;
    private boolean turn1;
    private boolean turn2;
    private boolean onOcean;
    private Direction currentDirection;
    private boolean goingUp;
    private boolean uTurned;
    private boolean echofront;
    private boolean foundGround;
    private boolean checkEdge;
    private final Direction initialDirection;
    private int move;
    private Direction stoppingDirection;
    private int flyCount;
    private int edgeCounter;
    private int turnCount;
    private boolean test;




    public ScanIsland(Direction currentDirection, Direction initialDirection){
        this.fly = false;
        this.scan = false;
        this.turn1 = false;
        this.turn2 = false;
        this.onOcean = false;
        this.currentDirection = currentDirection;
        this.goingUp = false;
        this.uTurned=false;
        this.foundGround=false;
        this.echofront = false;
        this.checkEdge= false;
        this.initialDirection= initialDirection;
        this.move = 0;
        this.flyCount =0;
        this.edgeCounter = 0;
        this.turnCount =0;
        this.test = false;

    }

    private JSONObject scanner(){
        if (!fly){
            decision = command.fly();
            fly = true;
            scan = false;
        } else if (!scan){
            decision = command.scan();
            scan = true;
            fly = false;
        }
        return decision;
    }


    private JSONObject Uturn(){
        if (edgeCounter == 0){
            switch (turnCount){
                case 0:
                    decision = command.Turn(initialDirection);
                    turnCount++;
                    break;
                case 1:
                    decision = command.Turn(currentDirection.oppositeDirection());
                    turnCount++;
                    currentDirection = currentDirection.oppositeDirection();
                    break;
                case 2:
                    uTurned=true;
                    turnCount =0;
                    decision = command.scan();
            }

        }else {
            switch (turnCount){
                case 0:
                    decision = command.Turn(initialDirection.oppositeDirection());
                    turnCount++;
                    break;
                case 1:
                    decision = command.Turn(currentDirection.oppositeDirection());
                    turnCount++;
                    currentDirection = currentDirection.oppositeDirection();
                    break;
                case 2:
                    uTurned=true;
                    turnCount =0;
                    decision = command.scan();
            }

        }
        
        

        return decision;
    }

    private JSONObject echoAll(){
        if (!echofront){
            decision = command.echo(currentDirection);
            echofront = true;
        }



        return null;
    }
    private JSONObject  fourPointTurn(){

        switch (move){
            case 0:
            logger.info("MOVE 0");
                if (!test){
                    decision = command.Turn(initialDirection);
                    test= true;
                    
                }else{
                    decision = command.scan();
                    move++;
                    test = false;

                }
                
                
                break;
            case 1:
                logger.info("MOVE 1");
                if (!test){
                    decision = command.Turn(stoppingDirection);
                    test = true;
                }else{
                    move++;
                    decision = command.scan();
                    test=false;

                }
                
                
                break;
            case 2:
                logger.info("MOVE 2");
                if (!test){
                    decision = command.Turn(initialDirection.oppositeDirection());
                    test = true;

                }else{
                    move++;
                    decision = command.scan();
                    test = false;
                }
                
                
                break;
            case 3:
                logger.info("MOVE 3");
                decision = command.fly();
                move++;
                break;
            case 4:
                logger.info("MOVE 4");
                decision = command.Turn(stoppingDirection.oppositeDirection());
                move ++;
                break;
            case 5:
                logger.info("MOVE 5");
                if (flyCount<3){
                    decision = command.fly();
                    flyCount++;
                }else{
                    move++;
                }
                break;
            case 6:
                logger.info("MOVE 6");
                decision = command.Turn(initialDirection.oppositeDirection());
                move++;
                break;
            case 7:
                logger.info("MOVE 7");
                decision = command.Turn(stoppingDirection);
                move++;
                break;
            case 8:
                logger.info("MOVE 8");
                decision = command.scan();
                checkEdge=false;
                //goingUp=!goingUp;
                move =0;
                edgeCounter++;
    
                break;

        }

        return decision;
    }

    
    public void resultCheck(Information info) {
        JSONObject extras = info.getExtras();

        if (extras.has("found")){
            String found = extras.getString("found");
            if (found.equals("GROUND")){
                foundGround=true;

            }else if (found.equals("OUT_OF_RANGE")){
                foundGround=false;

            }

        }
        if (extras.has("biomes")){
            JSONArray biomesArray = extras.getJSONArray("biomes");

            if (biomesArray.length() == 1){
                if (("OCEAN").equals(biomesArray.optString(0))) {
                    logger.info("CHECKING FOR OCEAN");
                    onOcean = true;
                    if (onOcean){
                        logger.info("OCEAN FOUND");
                    }
                }
            }               

        }
        
    }

    public JSONObject makeDecision() {
        
        if (checkEdge){
            logger.info("CHECKING EDGE");
            if (!foundGround){
                logger.info("NO GROUND FOUND");
                if (edgeCounter == 0){
                    logger.info("EDGE COUNT 0 : {}", edgeCounter);
                    stoppingDirection=currentDirection;
                    decision= fourPointTurn();
                } else{
                    logger.info("EDGE COUNT not 0 : {}", edgeCounter);
                    decision.put("action", "stop");
                }

            }else{
                logger.info("GROUND FOUND");
                checkEdge=false;
            }

        } else if (!onOcean){
            logger.info("NOT ON OCEAN");
            decision = scanner();
            uTurned=false;
        } else if (onOcean){
            logger.info("ON OCEAN");
            if(!echofront){
                logger.info("CHECKPOINT 1");
                decision = command.echo(currentDirection);
                echofront = true;
            }else if (echofront){
                logger.info("CHECKPOINT 2");
                if (foundGround){
                    logger.info("FOUND GROUND");
                    onOcean = false;
                    decision = scanner();
                    echofront=false;
                }else if (!foundGround){
                    logger.info("NOT FOUND GROUND");
                    if(!uTurned){
                        logger.info("NOT UTURNED");
                        decision = Uturn();
                    }else if (uTurned){
                        logger.info("UNTURNED");
                        onOcean=false;
                        echofront = true;
                        checkEdge=true;
                        decision = command.echo(currentDirection);
                    }
                }
            
            }    
            
        }
        
        return decision;
    }
  
}
