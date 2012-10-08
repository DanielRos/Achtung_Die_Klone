import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


public class Player {

    private static Player reference;
    public Boolean isAlive;
    private final Color color;
    private double angle , speed;
    private final Coordinate coordinate;
    private final int radius;

    private String direction;
    private String name;
    private int leftKey;
    private int rightKey;
    private int weaponKey;
    private final Set<PlayerListener> PlayerListenerSet;

    public Player(String playerName, Color color, int turnLeft, int turnRight) {
        this.coordinate = new Coordinate(randomStartPos("x"),randomStartPos("y")) ;
        this.name = playerName;
        this.color = color;
        this.rightKey = turnRight;
        this.leftKey = turnLeft;
        this.weaponKey = weaponKey;
        reference = this;

        isAlive = true;
        angle =  Main.random(360);
        PlayerListenerSet = new HashSet<PlayerListener>();
        direction = "";
        radius = 5;

    }

    private double randomStartPos(String pos) {
        double start = GUI.getScreenHeight()/2;
        double width = GUI.getScreenWidth();
        double height = GUI.getScreenHeight();
        if(pos.equals("x")){
            start = Main.random(width);
            if(start > width *0.8) start -= width * 0.2;
            else if (start < width * 0.2) start += width * 0.2;
            return start;
        }
        else if(pos.equals("y")){
            start = Main.random(height);
            if(start > height *0.8) start -= height * 0.2;
            else if (start < height * 0.2) start += height * 0.2;
            return start;
        }

        return start;
    }

    public void holeGeneration(){}

    public void addPlayerListener(PlayerListener pl){
        PlayerListenerSet.add(pl);
    }

    protected void notifyListeners(){
        for(PlayerListener pl : PlayerListenerSet){
            pl.playerChanged();
        }

    }
    public void move(){
        if(isAlive){
            if (direction.equals("R")) angle += 2;
            else if(direction.equals("L")) angle -= 2;

            speed = 2;
            angle %= 360;

            if (angle < 0) {
                angle += 360;
            }
            float r = (float)Math.toRadians(angle);

            coordinate.moveX(Math.cos(r) * speed);
            coordinate.moveY(Math.sin(r)* speed);

            notifyListeners();
        }
    }

    public Coordinate getNextPixels(Coordinate c, double angle, double speed, int radius){
        float r = (float)Math.toRadians(angle);
        //Vector<Coordinate> nextCoordinates = new Vector<Coordinate>();
        double x =  c.getX();
        double y =  c.getY();

        double dx =  Math.cos(r) * radius*2;
        double dy =  Math.sin(r) * radius*2;

        return new Coordinate (x + dx, y + dy);

    }

    public double getX(){
        return coordinate.getX();
    }
    public double getY(){
        return coordinate.getY();
    }

    public int getLeftKey() {

        return leftKey;
    }
    public int getRightKey() {
        return rightKey;
    }

    public void setDirection(String d) {
        direction = d;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
    public static Player getPlayer(){
        return reference;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getAngle() {
        return angle;
    }

    public double getSpeed() {
        return speed;
    }
}

