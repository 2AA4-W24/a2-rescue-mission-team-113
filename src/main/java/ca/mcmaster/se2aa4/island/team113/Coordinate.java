package ca.mcmaster.se2aa4.island.team113;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Coordinate forward(Direction direction) {
        double new_x = x;
        double new_y = y;

        switch(direction) {
            case N:
                new_y += 1.0;
                break;
            case E:
                new_x += 1.0;
                break;
            case S:
                new_y -= 1.0;
                break;
            case W:
                new_x -= 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(new_x, new_y);
    }
    public Coordinate turnRight(Direction direction) {
        double new_x = x;
        double new_y = y;

        switch(direction) {
            case N:
                new_y += 1.0;
                new_x += 1.0;
                break;
            case E:
                new_x += 1.0;
                new_y -= 1.0;
                break;
            case S:
                new_y -= 1.0;
                new_x -= 1.0;
                break;
            case W:
                new_x -= 1.0;
                new_y += 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(new_x, new_y);
    }

    public Coordinate turnLeft(Direction direction) {
        double new_x = x;
        double new_y = y;

        switch(direction) {
            case N:
                new_y += 1.0;
                new_x -= 1.0;
                break;
            case E:
                new_x += 1.0;
                new_y += 1.0;
                break;
            case S:
                new_y -= 1.0;
                new_x += 1.0;
                break;
            case W:
                new_x -= 1.0;
                new_y -= 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(new_x, new_y);
    }


}
