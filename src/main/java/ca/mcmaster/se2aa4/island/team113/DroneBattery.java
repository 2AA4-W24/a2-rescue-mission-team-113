package ca.mcmaster.se2aa4.island.team113;

public class DroneBattery {
    private Integer juice;

    public DroneBattery(Integer juice) {
        this.juice = juice;
    }

    public Integer getJuice(){
        return juice;
    }

    public void loseJuice(Integer used){
        juice = juice - used;
    }

    public boolean lowcheck(){
        return juice < 27;
    }

    
}
