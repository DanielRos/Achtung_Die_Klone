import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;


public class PlayerComponent extends JComponent implements PlayerListener{

    private InputMap iMap;
    private int leftKey;
    private int rightKey;
    private KeyStroke pauseKey = KeyStroke.getKeyStroke( "SPACE" );
    private Player owner;
    private static PlayerComponent reference;

    public PlayerComponent(Player p) {
        this.owner = p;
        this.leftKey = owner.getLeftKey();
        this.rightKey = owner.getRightKey();
        reference = this;

        bindKeys();
        setVisible(true);

    }

    public void bindKeys(){
        iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap aMap = getActionMap();

        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();
        Action actionReleased = new Released();
        Action pauseUnpause = new PauseUnpause();

        iMap.put(KeyStroke.getKeyStroke(leftKey, 0, false), "left_pressed");
        aMap.put("left_pressed", leftAction);

        iMap.put(KeyStroke.getKeyStroke(leftKey, 0, true), "left_released");
        aMap.put("left_released", actionReleased);

        iMap.put(KeyStroke.getKeyStroke(rightKey, 0, false), "right_pressed");
        aMap.put("right_pressed", rightAction);

        iMap.put(KeyStroke.getKeyStroke(rightKey, 0, true), "right_released");
        aMap.put("right_released", actionReleased);

        iMap.put(pauseKey, "pause/unpause");
        aMap.put("pause/unpause", pauseUnpause);
    }

    public Rectangle paintPlayer(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int r = owner.getRadius();
        int x = (int) owner.getX() - r;
        int y = (int) owner.getY() - r;
        int lineThickness = r*2;
        g.setColor(owner.getColor());
        g.fillOval(x, y, lineThickness, lineThickness);

        return new Rectangle(x-lineThickness ,y - lineThickness, lineThickness*3,lineThickness*3);
    }

    public double calcDistanceTo(double x,double y){
        double distance;
        distance = Math.sqrt( Math.pow(owner.getX()-x,2) + Math.pow(owner.getY()-y,2) );
        return distance;
    }

    public Player getOwner(){
        return owner;
    }

    public static PlayerComponent getReference() {
        return reference;
    }

    @Override
    public void playerChanged() {
        SnakePanel.getInstance().repaint();
    }

    class LeftAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if (owner.isAlive == true) {
                owner.setDirection("L");
                System.out.println("LEFT PRESSED");
            } else{
                iMap.put(KeyStroke.getKeyStroke((char) leftKey), "none");

            }
        }
    }

    class RightAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if (owner.isAlive == true) {
                owner.setDirection("R");
                System.out.println("RIGHT PRESSED");
            }else{
                iMap.put(KeyStroke.getKeyStroke((char) rightKey), "none");}
        }
    }

    class Released extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            owner.setDirection("");
        }
    }

    class PauseUnpause extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if(GameManager.isPaused()) GameManager.setPaused(false);

            else GameManager.setPaused(true);
        }
    }
}


