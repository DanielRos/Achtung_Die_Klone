package se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels;

import se.liu.ida.danro686.tddc69.projekt.Overview.GameManager;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ScorePanel extends JPanel {

    private final Font mainFont = new Font("serif",Font.BOLD, 20);
    private static ScorePanel reference;

    public ScorePanel(int width, int height) {
        reference = this;
        Dimension d = new Dimension(width, height);
        setPreferredSize(d);
        setLayout(new GridLayout(1,1));

        setBackground(Color.BLACK);
        setVisible(true);
    }

    public void updateScores(){
        removeAll();
        Container panel = new JPanel();
        panel.setBackground(Color.BLACK);
        addTitleAndDescription(panel);
        addSortedPlayerLabels(panel);
        add(panel);
        updateUI();
    }

    public void addTitleAndDescription(Container panel){
        JLabel title = new JLabel("Score");
        Font titleFont = new Font("serif", Font.BOLD, 35);
        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);
        title.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel description = new JLabel("First player to reach " + GameManager.getMaxScore());
        JLabel description2 = new JLabel("points is the winner!");
        JLabel description3 = new JLabel("Name                    Score");

        description.setFont(mainFont);
        description2.setFont(mainFont);
        description3.setFont(mainFont);
        description.setForeground(Color.YELLOW);
        description2.setForeground(Color.YELLOW);
        description3.setForeground(Color.YELLOW);
        description.setLayout(new FlowLayout(FlowLayout.CENTER));
        description2.setLayout(new FlowLayout(FlowLayout.CENTER));
        description3.setLayout(new FlowLayout(FlowLayout.CENTER));

        panel.add(title, title.getLayout());
        panel.add(description, description.getLayout());
        panel.add(description2, description2.getLayout());
        panel.add(new JLabel("   "));
        panel.add(description3);
    }

    public void addSortedPlayerLabels(Container panel){
        ArrayList<Player> rankings = GameManager.sortByScores();
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        for (Player ranking : rankings) {
            sortedPlayers.add(ranking);
        }

        for(Player p : sortedPlayers){
            JLabel jl = new JLabel(p.getName() + "                    " + p.getScore());
            jl.setFont(mainFont);
            jl.setForeground(p.getColor());
            jl.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.add(jl, jl.getLayout());
        }
    }

    public static ScorePanel getInstance(){
        return reference;
    }


}
