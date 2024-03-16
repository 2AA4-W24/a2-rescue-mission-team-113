package ca.mcmaster.se2aa4.island.team113;

public enum Direction {
    N, E, S, W;

    public Direction goRight() {
        switch (this) {
            case N: return E;
            case E:  return S;
            case S: return W;
            case W:  return N;
            default:    throw new IllegalStateException("Unknown direction: " + this);
        }
    }

    public Direction goLeft() {
        switch (this) {
            case N: return W;
            case W:  return S;
            case S: return E;
            case E:  return N;
            default:    throw new IllegalStateException("Unknown direction: " + this);
        }
    }

    public String directionToString(){
        switch (this) {
            case N: return "N";
            case W:  return "W";
            case S: return "S";
            case E:  return "E";
            default:    throw new IllegalStateException("Unknown direction: " + this);
        }
    }

    public Direction oppositeDirection( ){
        switch(this){
            case N: return S;
            case E: return W;
            case S: return N;
            case W: return E;
            default: throw new IllegalStateException("Unknown direction: " + this);
        }
    }

    public static Direction stringToDirection(String direction) {
        switch (direction) {
            case "N": return N;
            case "E":  return E;
            case "S": return S;
            case "W":  return W;
            default:    throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    


}
