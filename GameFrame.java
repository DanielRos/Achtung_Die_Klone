import javax.swing.*;
import java.awt.*;

public class GameFrame extends JPanel {

    public GameFrame() {
        setFocusable(true);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(new Color(0,0,0));
    }

}
