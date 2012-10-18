package se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.powerUps;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.SnakePanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.PowerUp;

import java.awt.*;

public class ClearFieldPowerUp extends PowerUp {
    public ClearFieldPowerUp() {
        super();
        this.color = Color.WHITE;
    }

    public void activate(){
        SnakePanel.getInstance().clearField();
    }
}
