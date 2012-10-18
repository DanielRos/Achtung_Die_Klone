package se.liu.ida.danro686.tddc69.projekt.Overview.Models;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.GamePanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.Utility;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Player {

    public boolean isAlive;
    private final Color color;
    private final Coordinate coordinate;
    private final String name;
    private String direction;
    private final int leftKey;
    private final int rightKey;
    private int score;
    private int holeCounter;
    private final int holeLength;
    private final int holeDelay;
    private int nextHoleCounter;
    private int smoothStartCounter;
    private int powerUpEffectCounter;
    private double angle;
    private final double radius;
    private final double initSpeed;
    private final double turnSpeed;
    private double speed;
    private double speedChange;
    private boolean hole;
    private final Set<PlayerListener> PlayerListenerSet;

    public Player(String playerName, Color color, int turnLeft, int turnRight) {
        this.coordinate = new Coordinate(0.0, 0.0);
        this.name = playerName;
        this.color = color;
        this.rightKey = turnRight;
        this.leftKey = turnLeft;

        angle = Utility.random(360);
        PlayerListenerSet = new HashSet<PlayerListener>();
        direction = "";
        radius = 5.0;
        score = 0;
        initSpeed = 1.1;
        turnSpeed = 1.2;
        holeLength = 35;
        holeDelay = 125;
        newRound();

    }

    private void randomStartPos(Coordinate coo) {
        double width = GamePanel.getGameArea().getWidth();
        double height = GamePanel.getGameArea().getHeight();

        double lowerMargin = 0.2;
        double upperMargin = 0.8;
        double x = Utility.random(width* lowerMargin, width* upperMargin);
        double y = Utility.random(height* lowerMargin, height* upperMargin);

        coo.setX(x);
        coo.setY(y);
    }

    public void newRound(){
        randomStartPos(coordinate);
        speed = initSpeed;
        isAlive = true;
        prepareNextHole();
        smoothStartCounter = 100;
        speedChange = 0;

    }

    void prepareNextHole(){
        nextHoleCounter = holeDelay;
        holeCounter = 0;
        hole = false;
    }

    void prepareHole(){
        holeCounter = holeLength;
        nextHoleCounter = 0;
        hole = true;
    }

    public void addPlayerListener(PlayerListener pl){
        PlayerListenerSet.add(pl);
    }

    void notifyListeners(){
        for(PlayerListener pl : PlayerListenerSet){
            pl.playerChanged();
        }
    }

    public void move(){
        if(isAlive){
            if (direction.equals("R")) angle += turnSpeed;
            else if(direction.equals("L")) angle -= turnSpeed;

            angle %= 360;

            if (angle < 0) {
                angle += 360;
            }
            float r = (float)Math.toRadians(angle);

            coordinate.moveX(Math.cos(r) * speed);
            coordinate.moveY(Math.sin(r)* speed);

            manageEffectsAndHoles();

            notifyListeners();
        }
    }

    private void manageEffectsAndHoles() {
        if(hole){
            holeCounter--;
            if(holeCounter <= 0)prepareNextHole();
        }
        else{
            nextHoleCounter--;
            if(nextHoleCounter <= 0)prepareHole();
        }
        if (smoothStartCounter > 0) smoothStartCounter--;

        if (powerUpEffectCounter > 0) powerUpEffectCounter--;

        else if(speedChange > 0 ){
            resetSpeed();
        }
    }

    public void affectedByPowerUp(int effectLifeTime) {
        powerUpEffectCounter = effectLifeTime;
    }

    public Vector<Coordinate> getNextPixels(Coordinate c, double angle, double radius){
        Vector<Coordinate> coordinates = new Vector<Coordinate>();
        coordinates.add(getNextPixel(c,angle,radius));
        coordinates.add(getNextPixel(c,angle+50,radius));
        coordinates.add(getNextPixel(c,angle-50,radius));

        return coordinates;
    }

    public Coordinate getNextPixel(Coordinate c, double angle, double radius){
        float r = (float)Math.toRadians(angle);

        double x =  c.getX();
        double y =  c.getY();

        // radius + 1.4 är det närmaste man kan komma för att den inte ska krocka med sig själv
        double dx =  Math.cos(r) * (radius+1.4);
        double dy =  Math.sin(r) * (radius+1.4);

        int nextXpixel;
        int nextYpixel;

        // ser till så att doubles av minusvärden och positiva värden avrundas korrekt
        if(dx< 0){
            nextXpixel = (int)Math.floor(dx);
        }
        else{
            nextXpixel = (int)Math.ceil(dx);
        }

        if(dy< 0){
            nextYpixel = (int)Math.floor(dy);
        }
        else{
            nextYpixel = (int)Math.ceil(dy);
        }

        return new Coordinate ((int)x + nextXpixel, (int)y + nextYpixel);

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

    public double getRadius() {
        return radius;
    }

    public int getScore() {
        return score;
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

    public void setAlive(boolean b) {
        isAlive = b;
    }

    public void increaseScore() {
        score++;

    }

    public void resetScore(){
        score = 0;
    }

    public void setSpeed() {
        if (speedChange < radius){
            speedChange+= 0.5;
            speed = speed + this.speedChange;
        }
    }

    void resetSpeed(){
        speed -= speedChange;
        speedChange = 0;
    }

    public boolean getHole() {
        return hole ;
    }

    public boolean justStarted(){
        return smoothStartCounter > 0;
    }
}

