package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;

class PositionTrackTest {

    private PositionTrack positionTrack;

    @BeforeEach
    void setUp() {
        positionTrack = new PositionTrack(Direction.N);
        positionTrack.addPOI("site", 0, 0); 
        positionTrack.addPOI("creek1", 3, 4); 
        positionTrack.addPOI("creek2", 6, 8); 
        
        positionTrack.addCreek("creek1");
        positionTrack.addCreek("creek2");
    }
    
    @Test
    void findClosestCreek_noCreeks_shouldReturnEmptyString() {
        positionTrack.clearCreeks();
        String closestCreek = positionTrack.findClosestCreek();
        assertEquals("", closestCreek, "With no creeks, should return an empty string for the closest creek.");
    }
    

    @Test
    void getCurrentCoordinate_initialPosition_shouldBeZeroZero() {
        Coordinate cord = new Coordinate(0, 0);
        assertEquals(cord.getX(), positionTrack.getCurrentCoordinate().getX(), "Initial position should be (0,0).");
        assertEquals(cord.getY(), positionTrack.getCurrentCoordinate().getY(), "Initial position should be (0,0).");
    }

    @Test
    void getcurrentDirection_initialDirection_shouldMatchConstructor() {
        assertEquals(Direction.N, positionTrack.getcurrentDirection(), "Initial direction should match the one passed to constructor.");
    }


    @Test
    void positionTracker_turnLeft_shouldChangeDirection() {
        JSONObject decision = new JSONObject();
        String left = positionTrack.getcurrentDirection().goLeft().directionToString();
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", left));
        positionTrack.positionTracker(decision);
        assertNotEquals(Direction.N, positionTrack.getcurrentDirection(), "Direction should change after turning left.");
    }

    @Test
    void positionTracker_turnRigth_shouldreturnRigthDirection() {
        JSONObject decision = new JSONObject();
        String right = positionTrack.getcurrentDirection().goRight().directionToString();
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", right));
        positionTrack.positionTracker(decision);
        assertEquals(Direction.E, positionTrack.getcurrentDirection(), "Direction should change after turning left.");
    }

    @Test
    void findClosestCreek_withMultipleCreeks_shouldReturnClosestCreek() {
        String closestCreek = positionTrack.findClosestCreek();
        assertEquals("creek1", closestCreek, "The closest creek to the site should be 'creek1'.");
    }
    

}