package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.json.JSONArray;

class GridSearchTest {
    
    private GridSearch gridSearch;
    private Commands commands = new Commands();

    @BeforeEach
    void setUp() {
        gridSearch = new GridSearch(Direction.N);
    }

    @Test
    void scanState_OceanBiome_TransitionsToCheckGround() {

        JSONObject response = new JSONObject();
        JSONArray biomes = new JSONArray();
        biomes.put("OCEAN");
        response.put("biomes", biomes);
        Information info = new Information(12, response);
        
        gridSearch.setStatePublic(gridSearch.getScanState());
        gridSearch.resultCheck(info);
        
        JSONObject decision = gridSearch.makeDecision();
        
        assertTrue(commands.echo(gridSearch.getCurrentDirection()).similar(decision));

    }

    @Test
    void flyState_transitionsToScan() {

        gridSearch.setStatePublic(gridSearch.getFlyState());

        JSONObject decision = gridSearch.makeDecision();

        JSONObject expectedDecision = commands.scan();
        assertTrue(decision.similar(expectedDecision));
    }

    @Test
void checkGroundState_groundFound_transitionsToFlyRange() {

    gridSearch.setStatePublic(gridSearch.getCheckGroundState());

    JSONObject response = new JSONObject();
    response.put("found", "GROUND");
    response.put("range", 5);
    Information info = new Information(12, response);

    gridSearch.resultCheck(info);

    JSONObject decision = gridSearch.makeDecision();

    assertTrue(commands.fly().similar(decision));
}

@Test
void uturn1State_transitionsToUturn2() {
    gridSearch.setStatePublic(gridSearch.getUturn1State());
    Direction currentDirection = gridSearch.getCurrentDirection();
    Direction expectedDirection = currentDirection.goRight(); 

    JSONObject decision = gridSearch.makeDecision();

    JSONObject expectedDecision = commands.turn(expectedDirection);
    assertTrue(decision.similar(expectedDecision));
}

    
}

