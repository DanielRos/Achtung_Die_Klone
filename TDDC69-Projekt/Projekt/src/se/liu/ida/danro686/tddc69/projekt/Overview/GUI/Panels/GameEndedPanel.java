package se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.GUI;
import se.liu.ida.danro686.tddc69.projekt.Overview.GameManager;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static se.liu.ida.danro686.tddc69.projekt.Overview.GameManager.newRound;

public class GameEndedPanel extends JPanel{
    private final JLabel title = new JLabel("Game Ended!");
    private final Font titleFont = new Font("serif",Font.BOLD, 30);
    private final Font mainFont = new Font("serif",Font.BOLD, 20);
    private static GameEndedPanel reference;

    public GameEndedPanel(int width, int height) {
        reference = this;
        setSize(width, height);
        setBackground(Color.BLACK);
    }

    public void showPlayerScores() {
        if(getComponentCount() == 0){
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(10,4,30,5));
            panel.setBackground(Color.BLACK);
            title.setFont(titleFont);
            title.setForeground(Color.YELLOW);
            panel.add(title);

            ArrayList<Player> rankings = GameManager.sortByScores();

            ArrayList<Player> sortedPlayers = new ArrayList<Player>();
            for (Player p : rankings) {
                sortedPlayers.add(p);
            }
            for(Player p : sortedPlayers){
                JLabel jl = new JLabel("    " + p.getName() + "  " + p.getScore());
                jl.setFont(mainFont);
                jl.setForeground(p.getColor());
                panel.add(jl);
            }

            panel.add(createReplayButton());
            reference.add(panel);
            reference.updateUI();
        }
    }

    public void anotherGameEnded(){
        removeAll();
    }

    private JPanel createReplayButton() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JButton startButton = new JButton("Rematch!");

        ActionListener cl = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               newRound(true);
               ScorePanel.getInstance().updateScores();
               GUI.getInstance().setPanel("Start Game");
            }
        };

        startButton.addActionListener(cl);
        startButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(startButton);
        return panel;
    }

    public static GameEndedPanel getInstance(){
        return reference;
    }
}
