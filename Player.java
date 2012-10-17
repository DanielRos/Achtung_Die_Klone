import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Player {

    public boolean isAlive;
    private final Color color;
    private final Coordinate coordinate;
    private final int radius;
    private String name, direction;
    private int leftKey, rightKey, score, holeCounter, nextHoleCounter, smoothStartCounter, powerUpEffectCounter;
    private double angle, initSpeed, turnSpeed, speed, speedChange;
    private boolean hole;
    private final Set<PlayerListener> PlayerListenerSet;

    public Player(String playerName, Color color, int turnLeft, int turnRight) {
        this.coordinate = new Coordinate(0.0, 0.0);
        this.name = playerName;
        this.color = color;
        this.rightKey = turnRight;
        this.leftKey = turnLeft;

        angle =  Main.random(360);
        PlayerListenerSet = new HashSet<PlayerListener>();
        direction = "";
        radius = 5;
        score = 9;
        initSpeed = 2;
        speed = initSpeed;
        speedChange = 0;
        turnSpeed = 2.5;
        newRound();

    }

    private void randomStartPos(Coordinate coo) {
        double width = GamePanel.getGameArea().getWidth();
        double height = GamePanel.getGameArea().getHeight();

        double lowerMargin = 0.2;
        double upperMargin = 0.8;
        double x = Main.random(width* lowerMargin, width* upperMargin);
        double y = Main.random(height* lowerMargin, height* upperMargin);

        coo.setX(x);
        coo.setY(y);
    }

    public void newRound(){
        randomStartPos(coordinate);
        speed = initSpeed;
        isAlive = true;
        prepareNextHole();
        smoothStartCounter = 50;
        speedChange = 0;

    }

    public void prepareNextHole(){
        nextHoleCounter = Main.random(50,100);
        holeCounter = 0;
        hole = false;
    }

    public void prepareHole(){
        holeCounter = Main.random(12,17);
        nextHoleCounter = 0;
        hole = true;
    }

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

    public Coordinate getNextPixel(Coordinate c, double angle, int radius){
        float r = (float)Math.toRadians(angle);

        double x =  c.getX();
        double y =  c.getY();

        double dx =  Math.cos(r) * (radius+1);
        double dy =  Math.sin(r) * (radius+1);

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

    public int getRadius() {
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
            speedChange++;
            speed = speed + this.speedChange;
        }
    }

    public void resetSpeed(){
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

