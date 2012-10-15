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

        displayPlayerScores();
    }

    public void displayPlayerScores() {
        Vector<PlayerComponent> playerComponents = GameManager.getpComponents();
        System.out.println("what up");
        for (PlayerComponent pc : playerComponents){
            Player p = pc.getOwner();
            JLabel jl = new JLabel("    " + p.getName() + "  " + p.getScore());
            jl.setFont(mainFont);
            jl.setForeground(p.getColor());
            add(jl);

        }
    }
    public void updateScores(){
        ArrayList<JLabel> list = new ArrayList<JLabel>();
        HashMap<Integer, JLabel> scoreToLabel = new HashMap<Integer, JLabel>();
        Integer[] scores = new Integer[6];
        for (Component c: getComponents()){
            remove(c);
        }
        getInstance().add(title);
        for (PlayerComponent pc : GameManager.getpComponents()){
            Player p = pc.getOwner();
            JLabel jl = new JLabel("    " + p.getName() + "     " + p.getScore());
            jl.setFont(mainFont);
            jl.setForeground(p.getColor());
            scoreToLabel.put(p.getScore(),jl);
        }
        for(JLabel jl : list){
            getInstance().add(jl);
        }

        getInstance().updateUI();

    }


    public static ScorePanel getInstance(){
        return panel;
    }


}
