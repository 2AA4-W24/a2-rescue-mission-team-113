package ca.mcmaster.se2aa4.island.team113;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.json.JSONArray;

class GoToGroundTest {

    private GoToGround goToGround;

    @BeforeEach
    void setUp() {
        goToGround = new GoToGround(Direction.N);
    }

    @Test
    void makeDecision_initialState_shouldEchoFront() {
        JSONObject decision = goToGround.makeDecision();
        assertEquals("echo", decision.getString("action"), "Initial state should echo front.");
        assertEquals(Direction.N.directionToString(), decision.getJSONObject("parameters").getString("direction"), "Echo should be in the initial direction.");
    }

    @Test
    void EchoFront_NoGroundFound_goToEchoRight(){
        goToGround.setStatePublic(goToGround.getEchoFrontState());

        JSONObject response = new JSONObject();
        response.put("found", "OUT_OF_RANGE");
        Information info = new Information (12, response);
        goToGround.resultCheck(info);
        Direction temp = goToGround.getCurrentDirection();
        JSONObject decision = goToGround.makeDecision();
        JSONObject check = new JSONObject().put("action", "echo").put("parameters", new JSONObject().put("direction", temp.goRight().directionToString()));
        assertTrue(decision.similar(check));
    }

    @Test
void Scan_NotOverOcean_LandAndSetOnGround() {

    goToGround.setStatePublic(goToGround.getScanState());
    JSONObject response = new JSONObject();
    JSONArray biomes = new JSONArray();
    biomes.put("FOREST"); 
    response.put("biomes", biomes);
    Information info = new Information(12, response);

    goToGround.resultCheck(info);

    JSONObject decision = goToGround.makeDecision();

    JSONObject expectedDecision = new JSONObject().put("action", "fly");

    assertTrue(decision.similar(expectedDecision), "Decision should match expected action to fly when not over ocean.");
    assertTrue(goToGround.getCompleted(), "The onGround flag should be set to true when not over ocean.");
}




    

    

}
