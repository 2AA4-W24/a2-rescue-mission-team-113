package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


class MapOfPointsTest {

    MapOfPoints map;

    @BeforeEach
     void initializeMap() {
        map = new MapOfPoints();
        map.addPointOfInterest("Emergency Site", 15.5, 7);
        map.addPointOfInterest("Creek1", 18.5, 11);
    }

    @Test
     void addPointOfInterestTest() {
        assertNull(map.getPointOfInterest("Creek2"));
        map.addPointOfInterest("Creek2", 51.4, 11.9);
        assertNotNull(map.getPointOfInterest("Creek2"));
    }

    @Test
     void getPointOfInterestTest() {
        Coordinate poi = map.getPointOfInterest("Creek1");
        assertEquals(18.5, poi.getX());
        assertEquals(11, poi.getY());
    }

    @Test
     void calculatedDistanceShouldBeFive() {
        double distance = map.calculateDistance("Emergency Site", "Creek1");
        assertEquals(5.0, distance);
    }

}
