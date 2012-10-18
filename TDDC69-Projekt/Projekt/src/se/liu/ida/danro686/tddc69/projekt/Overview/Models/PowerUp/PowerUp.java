package se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.GamePanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.Coordinate;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.Player;
import se.liu.ida.danro686.tddc69.projekt.Overview.Utility;

import java.awt.*;

public abstract class PowerUp {
    private final static int size = 30;
    private Coordinate coordinate;
    public Color color;
    public Player owner;
    public int effectLifeTime;

    public PowerUp(){
        coordinate = placePowerUp();
    }

    private Coordinate placePowerUp() {
        Coordinate coo = new Coordinate(0.0,0.0);
        double width = GamePanel.getGameArea().getWidth();
        double height = GamePanel.getGameArea().getHeight();

        double lowerMargin = 0.1;
        double upperMargin = 0.9;
        double x = Utility.random(width* lowerMargin, width* upperMargin);
        double y = Utility.random(height * lowerMargin, height * upperMargin);

        coo.setX(x);
        coo.setY(y);
        return coo;
    }

    public void paint(Graphics2D g2){

        g2.setColor(color);
        g2.fillRect(coordinate.getIntX(),coordinate.getIntY(),size,size);
    }

    public boolean HitByPlayer(double xPos, double yPos){
        boolean collided = false;
        Rectangle r = new Rectangle();
        r.setRect(coordinate.getIntX(), coordinate.getIntY(), size,size);
        if(r.contains(xPos, yPos)){
            collided = true;
        }
        return collided;
    }

    public void setOwner(Player p){
       owner = p;
    }

    public void activate(){}

}
