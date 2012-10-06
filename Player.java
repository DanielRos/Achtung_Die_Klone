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
            if (direction == "R") angle+=2;
            else if(direction == "L") angle -=2;

            speed = 1.5;
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
    public Coordinate getNextPixels(){
        float r = (float)Math.toRadians(angle);
        //Vector<Coordinate> nextCoordinates = new Vector<Coordinate>();
        int x = (int) coordinate.getX()+ radius;
        int y = (int) coordinate.getY()+ radius;

        return new Coordinate(x + Math.cos(r) * speed, y + Math.sin(r)* speed);
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
}

