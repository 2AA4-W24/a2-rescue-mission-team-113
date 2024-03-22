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
        double newX = x;
        double newY = y;

        switch(direction) {
            case N:
                newY += 1.0;
                break;
            case E:
                newX += 1.0;
                break;
            case S:
                newY -= 1.0;
                break;
            case W:
                newX -= 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(newX, newY);
    }
    public Coordinate turnRight(Direction direction) {
        double newX = x;
        double newY = y;

        switch(direction) {
            case N:
                newY += 1.0;
                newX += 1.0;
                break;
            case E:
                newX += 1.0;
                newY -= 1.0;
                break;
            case S:
                newY -= 1.0;
                newX -= 1.0;
                break;
            case W:
                newX -= 1.0;
                newY += 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(newX, newY);
    }

    public Coordinate turnLeft(Direction direction) {
        double newX = x;
        double newY = y;

        switch(direction) {
            case N:
                newY += 1.0;
                newX -= 1.0;
                break;
            case E:
                newX += 1.0;
                newY += 1.0;
                break;
            case S:
                newY -= 1.0;
                newX += 1.0;
                break;
            case W:
                newX -= 1.0;
                newY -= 1.0;
                break;
            default:
                throw new IllegalStateException("Unknown direction: " + direction);
        }
        return new Coordinate(newX, newY);
    }


}
