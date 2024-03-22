package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;


class CommandsTest {

    private Direction direction;

    @BeforeEach
    void initializeDirection() {
        direction = Direction.N;
    }

    @Test
    void echoShouldReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.echo(direction);

        assertEquals("echo", decision.getString("action"));
        assertEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void echoShouldNotReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.echo(direction);

        assertEquals("echo", decision.getString("action"));
        assertNotEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnRightShouldReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnRight(direction);

        assertEquals("heading", decision.getString("action"));
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnRightShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnRight(direction);

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnLeftShouldReturnW() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnLeft(direction);

        assertEquals("heading", decision.getString("action"));
        assertEquals("W", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnLeftShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnLeft(direction);

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnWithGoRightShouldReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.turn(direction.goRight());

        assertEquals("heading", decision.getString("action"));
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnWithGoRightShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turn(direction.goRight());

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnWithGoLeftShouldReturnW() {
        Commands commands = new Commands();
        JSONObject decision = commands.turn(direction.goLeft());

        assertEquals("heading", decision.getString("action"));
        assertEquals("W", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void turnWithGoLeftShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turn(direction.goLeft());

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    void flyTest() {
        Commands commands = new Commands();
        JSONObject decision = commands.fly();

        assertEquals("fly", decision.getString("action"));
    }

    @Test
    void scanTest() {
        Commands commands = new Commands();
        JSONObject decision = commands.scan();

        assertEquals("scan", decision.getString("action"));
    }

}
