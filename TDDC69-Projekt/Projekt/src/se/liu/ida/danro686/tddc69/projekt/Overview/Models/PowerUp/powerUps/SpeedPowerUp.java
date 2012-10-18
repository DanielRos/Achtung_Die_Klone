package se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.powerUps;

import se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.PowerUp;

import java.awt.*;

public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(){
        super();
        this.color = Color.BLUE;
        this.effectLifeTime = 300;
    }

    public void activate(){
        owner.affectedByPowerUp(effectLifeTime);
        owner.setSpeed();
    }
}
