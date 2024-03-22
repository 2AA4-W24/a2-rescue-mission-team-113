package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;


public class CommandsTest {

    private Direction direction;

    @BeforeEach
    public void initializeDirection() {
        direction = Direction.N;
    }

    @Test
    public void echoShouldReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.echo(direction);

        assertEquals("echo", decision.getString("action"));
        assertEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void echoShouldNotReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.echo(direction);

        assertEquals("echo", decision.getString("action"));
        assertNotEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnRightShouldReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnRight(direction);

        assertEquals("heading", decision.getString("action"));
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnRightShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnRight(direction);

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnLeftShouldReturnW() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnLeft(direction);

        assertEquals("heading", decision.getString("action"));
        assertEquals("W", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnLeftShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.turnLeft(direction);

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnWithGoRightShouldReturnE() {
        Commands commands = new Commands();
        JSONObject decision = commands.Turn(direction.goRight());

        assertEquals("heading", decision.getString("action"));
        assertEquals("E", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnWithGoRightShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.Turn(direction.goRight());

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnWithGoLeftShouldReturnW() {
        Commands commands = new Commands();
        JSONObject decision = commands.Turn(direction.goLeft());

        assertEquals("heading", decision.getString("action"));
        assertEquals("W", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void turnWithGoLeftShouldNotReturnN() {
        Commands commands = new Commands();
        JSONObject decision = commands.Turn(direction.goLeft());

        assertEquals("heading", decision.getString("action"));
        assertNotEquals("N", decision.getJSONObject("parameters").getString("direction"));
    }

    @Test
    public void flyTest() {
        Commands commands = new Commands();
        JSONObject decision = commands.fly();

        assertEquals("fly", decision.getString("action"));
    }

    @Test
    public void scanTest() {
        Commands commands = new Commands();
        JSONObject decision = commands.scan();

        assertEquals("scan", decision.getString("action"));
    }

}
