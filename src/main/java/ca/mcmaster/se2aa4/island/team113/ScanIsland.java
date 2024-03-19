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
    private boolean onOcean;
    private Direction currentDirection;
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
    private int runs;
    private Coordinate currentCoordinate;
    private Coordinate starCoordinate;
    private boolean turn1;
    private boolean turn2;
    private boolean goingUp;
    private PositionTrack map;
    private Coordinate initalCoordinate;




    public ScanIsland(Direction currentDirection, Direction initialDirection){
        this.fly = false;
        this.scan = false;
        this.onOcean = false;
        this.currentDirection = currentDirection;
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
        this.runs =0;
        this.turn1 = false;
        this.turn2=false;
        this.goingUp=true;
        this.map = new PositionTrack(initialDirection);
        this.initalCoordinate = new Coordinate(0, 0);


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
        



    private JSONObject  fourPointTurn(){
        //going down = 


        logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
        switch (move){
            case 0:
            logger.info("MOVE 0");
                if (!test){
                    if (!goingUp){
                        currentDirection = currentDirection.goLeft();
                    }else {
                        currentDirection = currentDirection.goRight();
                    }
                    logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
                    decision = command.Turn(currentDirection);
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
                    if (!goingUp){
                        currentDirection = currentDirection.goRight();
                    }else {
                        currentDirection = currentDirection.goLeft();
                    }
                    logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
                    decision = command.Turn(currentDirection);
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
                    if (!goingUp){
                        currentDirection = currentDirection.goRight();
                    }else {
                        currentDirection = currentDirection.goLeft();
                    }
                    logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
                    decision = command.Turn(currentDirection);
                    test = true;

                }else{
                    move++;
                    decision = command.scan();
                    test = false;
                }
                
                
                break;
            case 3:
                logger.info("MOVE 3");
                logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
                decision = command.fly();
                move++;
                break;
            case 4:
                logger.info("MOVE 4");
                if (!goingUp){
                    currentDirection = currentDirection.goRight();
                }else {
                    currentDirection = currentDirection.goLeft();
                }
                logger.info("CURRENT DIRECTION CHECK {}", currentDirection.directionToString());
                decision = command.Turn(currentDirection);
                move ++;
                flyCount = 0;
                break;
            case 5:
                logger.info("MOVE 5");
                logger.info("FLY COUNT {}", flyCount);
                if (flyCount < 3){
                    logger.info("FLY COUNT");
                    decision = command.fly();
                    flyCount++;
                }else{
                    move++;
                }
                break;
            case 6:
                logger.info("MOVE 6");
                if (!goingUp){
                    currentDirection = currentDirection.goLeft();
                }else {
                    currentDirection = currentDirection.goRight();
                }
                decision = command.Turn(currentDirection);
                //ecision = decision.put("action", "stop");
                move++;
                break;
            case 7:
                logger.info("MOVE 7");
                if (!goingUp){
                    currentDirection = currentDirection.goLeft();
                }else {
                    currentDirection = currentDirection.goRight();
                }
                decision = command.Turn(currentDirection);
                move++;
                break;
            case 8:
                logger.info("MOVE 8");
                decision = command.scan();
                checkEdge=false;
                move =0;
                goingUp = !goingUp;
                runs++;
    
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
        //if(map.getCurrentCoordinate().equals(initalCoordinate)){
          //  logger.info("ARE EQUAL ");
            //runs++;
        //}
        logger.info("RUNS: {}", runs);
        if (runs == 3){
            logger.info("RUNS IS 3");
            decision = decision.put("action", "stop");

        } else if(checkEdge){
            logger.info("CHECKING EDGE");
            if (!foundGround){
                logger.info("NO GROUND FOUND");
                stoppingDirection=currentDirection;
                decision= fourPointTurn();
                
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
        logger.info("CUREENT DIRECTION {}", currentDirection.directionToString());
        map.positionTracker(decision);
        return decision;
    }
  
}
