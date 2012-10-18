package se.liu.ida.danro686.tddc69.projekt.Overview.Models;

public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double X, double Y) {
        this.x = X;
        this.y = Y;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
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
    public int getIntX(){
        return (int)x;
    }
    public int getIntY(){
        return (int)y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "se.liu.ida.danro686.tddc69.projekt.Overview.Models.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
