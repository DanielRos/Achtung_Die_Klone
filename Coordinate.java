
public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double X, double Y) {
        this.x = X;
        this.y = Y;
    }
    public void moveX(double increment){
        this.x += increment;
    }
    public void moveY(double increment){
        this.y += increment;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
