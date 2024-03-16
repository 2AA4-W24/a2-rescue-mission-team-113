package ca.mcmaster.se2aa4.island.team113;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate move(Direction direction) {
        int new_x = x;
        int new_y = y;

        switch(direction) {
            case N:
                new_y += 1;
                break;
            case E:
                new_x += 1;
                break;
            case S:
                new_y -= 1;
                break;
            case W:
                new_x -= 1;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(new_x, new_y);
    }
}
