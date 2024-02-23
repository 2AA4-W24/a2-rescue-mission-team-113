package ca.mcmaster.se2aa4.island.team113;

public class DroneBattery {
    private int juice;

    public DroneBattery(int juice) {
        this.juice = juice;
    }

    public int getJuice(){
        return juice;
    }

    public void loseJuice(int used){
        juice = juice - used;
    }

    public boolean lowcheck(){
        return juice < 10;
    }

    
}
