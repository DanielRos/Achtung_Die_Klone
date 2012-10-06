import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    private SnakePanel snakePanel;
    private static GamePanel reference;

    public GamePanel() {
        reference = this;


        int width = GUI.getScreenWidth();
        int height = GUI.getScreenHeight();
        setSize(GUI.getScreenWidth(),GUI.getScreenHeight());
        snakePanel = new SnakePanel(width,height);
        add(snakePanel,BorderLayout.WEST);
    }

    public SnakePanel getSnakePanel() {
        return snakePanel;
    }

    public static GamePanel getGamePanel() {
        return reference;
    }
}
