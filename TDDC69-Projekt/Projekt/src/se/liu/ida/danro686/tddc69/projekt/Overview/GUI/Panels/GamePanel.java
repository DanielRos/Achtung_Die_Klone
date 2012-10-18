package se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.GUI;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel{
    private final SnakePanel snakePanel;
    private static int scorePanelWidth = 0;

    public GamePanel() {
        setBackground(Color.BLACK);
        int width = GUI.getFrameWidth();
        int height = GUI.getFrameHeight();
        scorePanelWidth = width/5;
        setSize(width, height);
        setLayout(new BorderLayout(0,0));

        snakePanel = new SnakePanel(width-scorePanelWidth,height);
        add(snakePanel, BorderLayout.CENTER);
        ScorePanel scorePanel = new ScorePanel(scorePanelWidth, height);

        add(scorePanel, BorderLayout.WEST);

    }

    public static Dimension getGameArea(){
        return new Dimension(GUI.getFrameWidth()-scorePanelWidth,GUI.getFrameHeight());
    }
}
