import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



public class Player {
    public Boolean isAlive;
    private PixelColor color;
    private double angle , speed;
    private int x,y;
    private int radius;

    private String direction;
    private String name;
    private int leftKey;
    private int rightKey;
    private int weaponKey;
    private Set<PlayerListener> PlayerListenerSet;

    public Player(int x, int y, String playerName, PixelColor color, int turnLeft, int turnRight) {
        this.x = x;
        this.y = y;
        this.name = playerName;
        this.color = color;
        this.rightKey = turnRight;
        this.leftKey = turnLeft;
        this.weaponKey = weaponKey;

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

        if (direction == "R") angle+=4;
        else if(direction == "L") angle -=4;

        speed = 4;
        angle %= 360;

        if (angle < 0) {
            angle += 360;
        }
        float r = (float)Math.toRadians(angle);

        x += Math.cos(r) * speed;
        y += Math.sin(r)* speed;


            //position = new Coordinate(coordinate.getX(),coordinate.getY());

        //position = new Coordinate(coordinate.getX(),coordinate.getY());
            //trail.pushCoordinate(position);
           // System.out.println("pushing coo   " + coordinate.getX() + "  "+ coordinate.getY() +"\n");
               //sesmt
        notifyListeners();

    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
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

    public PixelColor getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }
}

