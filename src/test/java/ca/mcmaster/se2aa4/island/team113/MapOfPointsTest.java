package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class MapOfPointsTest {

    private MapOfPoints map;

    @BeforeEach
    public void initializeMap() {
        map = new MapOfPoints();
        map.addPointOfInterest("Emergency Site", 15.5, 7);
        map.addPointOfInterest("Creek1", 18.5, 11);
    }

    @Test
    public void addPointOfInterestTest() {
        assertNull(map.getPointOfInterest("Creek2"));
        map.addPointOfInterest("Creek2", 51.4, 11.9);
        assertNotNull(map.getPointOfInterest("Creek2"));
    }

    @Test
    public void getPointOfInterestTest() {
        Coordinate poi = map.getPointOfInterest("Creek1");
        assertEquals(18.5, poi.getX());
        assertEquals(11, poi.getY());
    }

    @Test
    public void calculatedDistanceShouldBeFive() {
        double distance = map.calculateDistance("Emergency Site", "Creek1");
        assertEquals(5.0, distance);
    }

}
