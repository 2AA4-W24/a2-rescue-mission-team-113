package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DroneBatteryTest {

     DroneBattery battery;

    @BeforeEach
     void initializeBattery() {
        battery = new DroneBattery(100);
    }

    @Test
     void shouldDecreaseBatteryBy10() {
        battery.loseCharge(10);
        assertEquals(90, battery.getCharge());
    }

    @Test
     void shouldDecreaseBatteryBy40() {
        battery.loseCharge(40);
        assertEquals(60, battery.getCharge());
    }
    
    @Test
     void shouldBeLow() {
        battery.loseCharge(80);
        assertTrue(battery.lowcheck());
    }

    @Test
     void shouldNotBeLow() {
        battery.loseCharge(70);
        assertFalse(battery.lowcheck());
    }

    @Test
     void shouldBeLowEdgeCase() {
        battery.loseCharge(74);
        assertTrue(battery.lowcheck());
    }

    @Test
     void shouldNotBeLowEdgeCase() {
        battery.loseCharge(73);
        assertFalse(battery.lowcheck());
    }

}
