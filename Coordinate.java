
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(int X, int Y) {
        this.x = X;
        this.y = Y;
    }
    public void moveX(int increment){
        this.x += increment;
    }
    public void moveY(int increment){
        this.y += increment;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
