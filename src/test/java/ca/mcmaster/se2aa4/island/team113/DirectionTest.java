package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DirectionTest {
    @Test
    public void goRightTest() {
        assertEquals(Direction.E, Direction.N.goRight());
        assertEquals(Direction.S, Direction.E.goRight());
        assertEquals(Direction.W, Direction.S.goRight());
        assertEquals(Direction.N, Direction.W.goRight());
    }

    @Test
    public void goLeftTest() {
        assertEquals(Direction.W, Direction.N.goLeft());
        assertEquals(Direction.N, Direction.E.goLeft());
        assertEquals(Direction.E, Direction.S.goLeft());
        assertEquals(Direction.S, Direction.W.goLeft());
    }

    @Test
    public void directionToStringTest() {
        assertEquals("N", Direction.N.directionToString());
        assertEquals("E", Direction.E.directionToString());
        assertEquals("S", Direction.S.directionToString());
        assertEquals("W", Direction.W.directionToString());
    }

    @Test
    public void oppositeDirectionTest() {
        assertEquals(Direction.S, Direction.N.oppositeDirection());
        assertEquals(Direction.W, Direction.E.oppositeDirection());
        assertEquals(Direction.N, Direction.S.oppositeDirection());
        assertEquals(Direction.E, Direction.W.oppositeDirection());
    }

    @Test
    public void stringToDirectionTest() {
        assertEquals(Direction.N, Direction.stringToDirection("N"));
        assertEquals(Direction.E, Direction.stringToDirection("E"));
        assertEquals(Direction.S, Direction.stringToDirection("S"));
        assertEquals(Direction.W, Direction.stringToDirection("W"));
    }

}
