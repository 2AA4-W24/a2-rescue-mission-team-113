package ca.mcmaster.se2aa4.island.team113;

import org.json.JSONObject;

public interface CommandCentre {
    JSONObject echo(Direction direction );
    JSONObject TurnRight();
    JSONObject TurnLeft();
    JSONObject fly();
    JSONObject scan();


}