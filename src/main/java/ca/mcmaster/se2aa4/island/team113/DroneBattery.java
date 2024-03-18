package ca.mcmaster.se2aa4.island.team113;

public class DroneBattery {
    private Integer charge;

    public DroneBattery(Integer juice) {
        this.charge = juice;
    }

    public Integer getCharge(){
        return charge;
    }

    public void loseJuice(Integer used){
        charge = charge - used;
    }

    public boolean lowcheck(){
        return charge < 27;
    }

    
}
