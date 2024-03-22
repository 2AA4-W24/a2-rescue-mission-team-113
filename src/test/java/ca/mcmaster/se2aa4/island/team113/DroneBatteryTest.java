package ca.mcmaster.se2aa4.island.team113;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DroneBatteryTest {

    private DroneBattery battery;

    @BeforeEach
     void initializeBattery() {
        battery = new DroneBattery(100);
    }

    @Test
     void shouldDecreaseBatteryBy10() {
        battery.loseJuice(10);
        assertEquals(90, battery.getCharge());
    }

    @Test
     void shouldDecreaseBatteryBy40() {
        battery.loseJuice(40);
        assertEquals(60, battery.getCharge());
    }
    
    @Test
     void shouldBeLow() {
        battery.loseJuice(80);
        assertTrue(battery.lowcheck());
    }

    @Test
     void shouldNotBeLow() {
        battery.loseJuice(70);
        assertFalse(battery.lowcheck());
    }

    @Test
     void shouldBeLowEdgeCase() {
        battery.loseJuice(74);
        assertTrue(battery.lowcheck());
    }

    @Test
     void shouldNotBeLowEdgeCase() {
        battery.loseJuice(73);
        assertFalse(battery.lowcheck());
    }

}
