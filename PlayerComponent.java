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
    private EnumMap enumMap;
    private Action leftAction;
    private Action rightAction;
    private Action actionReleased;
    private ActionMap aMap;
    private InputMap iMap;
    private KeyStroke leftKey;
    private KeyStroke rightKey;
    private static Player owner;
    private static PlayerComponent reference;


    public PlayerComponent(Player p) {
        this.owner = p;
        reference = this;
        //setSize(GUI.getScreenWidth(),GUI.getScreenHeight());
        bindKeys();
        //setVisible(true);
        setOpaque(false);

    }

    public void bindKeys(){
        iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        aMap = getActionMap();

        leftAction = new LeftAction();
        rightAction = new RightAction();
        actionReleased = new Released();

        iMap.put(KeyStroke.getKeyStroke(owner.getLeftKey(), 0, false), "left_pressed");
        aMap.put("left_pressed", leftAction);

        iMap.put(KeyStroke.getKeyStroke(owner.getLeftKey(), 0, true), "left_released");
        aMap.put("left_released", actionReleased );

        iMap.put(KeyStroke.getKeyStroke(owner.getRightKey(), 0, false), "right_pressed");
        aMap.put("right_pressed", rightAction);

        iMap.put(KeyStroke.getKeyStroke(owner.getRightKey(), 0, true), "right_released");
        aMap.put("right_released", actionReleased );
    }

    public static void paintPlayer(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //BufferedImage drawer = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);
        int x = (int) owner.getX() - owner.getRadius();
        int y = (int) owner.getY() - owner.getRadius();
        int lineThickness = owner.getRadius()*2;
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, lineThickness, lineThickness);

        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
       // return new Rectangle(x,y,lineThickness,lineThickness);
    }

    public double calcDistanceTo(double x,double y){
        double distance;
        distance = Math.sqrt( Math.pow(owner.getX()-x,2) + Math.pow(owner.getY()-y,2) );
        return distance;
    }

    public static Player getOwner(){
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
                iMap.put(leftKey, "none");

            }
        }
    }

    class RightAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if (owner.isAlive == true) {
                owner.setDirection("R");
                System.out.println("RIGHT PRESSED");
            }else{
                iMap.put(rightKey, "none");}
        }
    }

    class Released extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            owner.setDirection("");
            System.out.println("RELEASED");

        }
    }

}


