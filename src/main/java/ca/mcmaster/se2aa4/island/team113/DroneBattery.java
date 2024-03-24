package ca.mcmaster.se2aa4.island.team113;

public class DroneBattery {
    private Integer charge;

    public DroneBattery(Integer charge) {
        this.charge = charge;
    }

    public Integer getCharge(){
        return charge;
    }

    public void loseCharge(Integer used){
        charge = charge - used;
    }

    public boolean lowcheck(){
        return charge < 40;
    }

    
}
