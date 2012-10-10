import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel{
    private SnakePanel snakePanel;
    private ScorePanel scorePanel;
    private static GamePanel reference;
    private final int scorePanelWidth;

    public GamePanel() {
        reference = this;
        setBackground(Color.BLACK);
        int width = GUI.getScreenWidth();
        int height = GUI.getScreenHeight();
        scorePanelWidth = width/5;
        setSize(width, height);
        setLayout(new BorderLayout(0,0));

        snakePanel = new SnakePanel(width-scorePanelWidth,height);
        add(snakePanel, BorderLayout.CENTER);
        scorePanel = new ScorePanel(scorePanelWidth, height);

        add(scorePanel, BorderLayout.WEST);

    }

    public SnakePanel getSnakePanel() {
        return snakePanel;
    }

    public static GamePanel getGamePanel() {
        return reference;
    }
}
