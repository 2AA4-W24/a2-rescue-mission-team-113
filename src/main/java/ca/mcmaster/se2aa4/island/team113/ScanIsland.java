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


    public ScanIsland(Direction currentDirection){
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
        if (!turn1 && !turn2){
            if (!goingUp){
                logger.info("DIRECTION CHECK {}", currentDirection.directionToString());
                decision = command.TurnLeft(currentDirection);
                currentDirection = currentDirection.goLeft();
                turn1= true;
            }else if(goingUp){
                decision = command.TurnRight(currentDirection);
                currentDirection = currentDirection.goRight();
                turn1= true;
            }
            
        }else if (turn1 && !turn2){
            if (!goingUp){
                decision = command.TurnLeft(currentDirection);
                currentDirection = currentDirection.goLeft();
                turn2= true;
            }else if(goingUp){
                decision = command.TurnRight(currentDirection);
                currentDirection = currentDirection.goRight();
                turn2= true;
            }
        }else if (turn1 && turn2){
            uTurned=true;
            decision = command.scan();
            turn1 = false;
            turn2= false;
            goingUp = !goingUp;
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
                decision.put("action", "stop");
            }else{
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
