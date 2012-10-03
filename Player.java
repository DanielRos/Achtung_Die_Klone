import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class Player {
    private static Player reference;
    public Boolean isAlive;
    private Color color;
    private double angle , speed;
    private Coordinate coordinate;
    private int radius;

    private String direction;
    private String name;
    private int leftKey;
    private int rightKey;
    private int weaponKey;
    private Set<PlayerListener> PlayerListenerSet;

    public Player(Coordinate c, String playerName, Color color, int turnLeft, int turnRight) {
        this.coordinate = c;
        this.name = playerName;
        this.color = color;
        this.rightKey = turnRight;
        this.leftKey = turnLeft;
        this.weaponKey = weaponKey;
        reference = this;

        isAlive = true;
        angle =  0;
        PlayerListenerSet = new HashSet<PlayerListener>();
        direction = "";
        radius = 5;
    }

    public void getNextPixel(){}

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

        if (direction == "R") angle+=2;
        else if(direction == "L") angle -=2;

        speed = 2;
        angle %= 360;

        if (angle < 0) {
            angle += 360;
        }
        float r = (float)Math.toRadians(angle);

        coordinate.moveX(Math.cos(r) * speed);
        coordinate.moveY(Math.sin(r)* speed);


            //position = new Coordinate(coordinate.getX(),coordinate.getY());

        //position = new Coordinate(coordinate.getX(),coordinate.getY());
            //trail.pushCoordinate(position);
           // System.out.println("pushing coo   " + coordinate.getX() + "  "+ coordinate.getY() +"\n");
               //sesmt
        notifyListeners();

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
}

