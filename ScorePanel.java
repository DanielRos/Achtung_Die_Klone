import javax.swing.*;
import java.awt.*;
import java.util.*;


public class ScorePanel extends JPanel {

    private Font titleFont = new Font("serif",Font.BOLD, 30);
    private Font mainFont = new Font("serif",Font.BOLD, 20);
    private static ScorePanel panel;
    private JLabel title = new JLabel("       Score Panel");

    public ScorePanel(int width, int height) {

        panel = this;
        Dimension d = new Dimension(width, height);
        setPreferredSize(d);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);

        setBackground(Color.BLACK);
        add(title);
        setVisible(true);

    }

    public void updateScores(){

        for (Component c: getComponents()){
            remove(c);
        }
        getInstance().add(title);
        ArrayList<Player> rankings = GameManager.sortByScores();
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        for (Player ranking : rankings) {
            sortedPlayers.add(ranking);
        }
        for(Player p : sortedPlayers){
            JLabel jl = new JLabel("    " + p.getName() + "  " + p.getScore());
            jl.setFont(mainFont);
            jl.setForeground(p.getColor());
            panel.add(jl);
        }
        panel.updateUI();
    }

    public static ScorePanel getInstance(){
        return panel;
    }


}
