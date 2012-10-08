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
    private KeyStroke leftKey;
    private KeyStroke rightKey;
    private Player owner;
    private static PlayerComponent reference;



    public PlayerComponent(Player p) {
        this.owner = p;
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

        iMap.put(KeyStroke.getKeyStroke(owner.getLeftKey(), 0, false), "left_pressed");
        aMap.put("left_pressed", leftAction);

        iMap.put(KeyStroke.getKeyStroke(owner.getLeftKey(), 0, true), "left_released");
        aMap.put("left_released", actionReleased);

        iMap.put(KeyStroke.getKeyStroke(owner.getRightKey(), 0, false), "right_pressed");
        aMap.put("right_pressed", rightAction);

        iMap.put(KeyStroke.getKeyStroke(owner.getRightKey(), 0, true), "right_released");
        aMap.put("right_released", actionReleased);
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

        Coordinate nextPixel = owner.getNextPixels(owner.getCoordinate(),owner.getAngle(),owner.getSpeed(), owner.getRadius());
        g.setColor(Color.RED);
       // g.drawLine((int)nextPixel.getX() ,(int)nextPixel.getY(),(int)nextPixel.getX(),(int)nextPixel.getY());
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));

        return new Rectangle(x-1 ,y - 1, lineThickness*2,lineThickness*2);
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


