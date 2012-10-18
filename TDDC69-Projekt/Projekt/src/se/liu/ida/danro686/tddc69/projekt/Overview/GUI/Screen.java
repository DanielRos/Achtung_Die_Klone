package se.liu.ida.danro686.tddc69.projekt.Overview.GUI;

import javax.swing.*;
import java.awt.*;

public class Screen {

    private final GraphicsDevice vc;

    public Screen(){

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
    }

    public void setFullScreen(DisplayMode dm, JFrame window) throws UnsupportedOperationException,
            IllegalArgumentException {
        window.dispose();
        window.setUndecorated(true);
        window.setVisible(true);
        window.setSize(GUI.getFrameWidth(),GUI.getFrameHeight());
        window.setResizable(false);
        vc.setFullScreenWindow(window);

        if(dm != null && vc.isDisplayChangeSupported()){
            vc.setDisplayMode(dm);
        }
    }
}


