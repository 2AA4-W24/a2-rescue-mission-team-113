package ca.mcmaster.se2aa4.island.team113;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Position move(Direction direction) {
        double new_x = x;
        double new_y = y;

        switch(direction) {
            case N:
                new_x += 1.0;
                break;
            case E:
                new_y += 1.0;
                break;
            case S:
                new_x -= 1.0;
                break;
            case W:
                new_y -= 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Position(new_x, new_y);
    }
}
