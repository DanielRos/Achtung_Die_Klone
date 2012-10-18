package se.liu.ida.danro686.tddc69.projekt.Overview;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args){
        final GameManager gm = new GameManager();

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    gm.getCurrentState().tick();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        };

        final Timer clockTimer = new Timer(8, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }
}

