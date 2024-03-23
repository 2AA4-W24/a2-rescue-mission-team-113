package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
     Coordinate coordinate;

    @BeforeEach
     void initializeCoordinate() {
        coordinate = new Coordinate(0.0, 0.0);
    }

    @Test
     void getXTest() {
        double x = coordinate.getX();
        assertEquals(0.0, x);
    }

    @Test
     void getYTest() {
        double y = coordinate.getY();
        assertEquals(0.0, y);
    }

    @Test
     void forwardN() {
        Coordinate new_coord = coordinate.forward(Direction.N);
        assertEquals(0.0, new_coord.getX());
        assertEquals(1.0, new_coord.getY());
    }

    @Test
     void forwardE() {
        Coordinate new_coord = coordinate.forward(Direction.E);
        assertEquals(1.0, new_coord.getX());
        assertEquals(0.0, new_coord.getY());
    }

    @Test
     void forwardS() {
        Coordinate new_coord = coordinate.forward(Direction.S);
        assertEquals(0.0, new_coord.getX());
        assertEquals(-1.0, new_coord.getY());
    }

    @Test
     void forwardW() {
        Coordinate new_coord = coordinate.forward(Direction.W);
        assertEquals(-1.0, new_coord.getX());
        assertEquals(0.0, new_coord.getY());
    }

    @Test
     void turnRightN() {
        Coordinate new_coord = coordinate.turnRight(Direction.N);
        assertEquals(1.0, new_coord.getX());
        assertEquals(1.0, new_coord.getY());
    }

    @Test
     void turnRightE() {
        Coordinate new_coord = coordinate.turnRight(Direction.E);
        assertEquals(1.0, new_coord.getX());
        assertEquals(-1.0, new_coord.getY());
    }

    @Test
     void turnRightS() {
        Coordinate new_coord = coordinate.turnRight(Direction.S);
        assertEquals(-1.0, new_coord.getX());
        assertEquals(-1.0, new_coord.getY());
    }

    @Test
     void turnRightW() {
        Coordinate new_coord = coordinate.turnRight(Direction.W);
        assertEquals(-1.0, new_coord.getX());
        assertEquals(1.0, new_coord.getY());
    }

    @Test
     void turnLeftN() {
        Coordinate new_coord = coordinate.turnLeft(Direction.N);
        assertEquals(-1.0, new_coord.getX());
        assertEquals(1.0, new_coord.getY());
    }

    @Test
     void turnLeftE() {
        Coordinate new_coord = coordinate.turnLeft(Direction.E);
        assertEquals(1.0, new_coord.getX());
        assertEquals(1.0, new_coord.getY());
    }

    @Test
     void turnLeftS() {
        Coordinate new_coord = coordinate.turnLeft(Direction.S);
        assertEquals(1.0, new_coord.getX());
        assertEquals(-1.0, new_coord.getY());
    }

    @Test
     void turnLeftW() {
        Coordinate new_coord = coordinate.turnLeft(Direction.W);
        assertEquals(-1.0, new_coord.getX());
        assertEquals(-1.0, new_coord.getY());
    }
    
}
