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
    Action leftAction;
    Action rightAction;
    Action actionReleased;
    ActionMap aMap;
    InputMap iMap;
    KeyStroke leftKey;
    KeyStroke rightKey;
    Player p;



    public PlayerComponent(Player p) {
        this.p = p;

        setSize(GUI.getScreenWidth(),GUI.getScreenHeight());
        bindKeys();
        setVisible(true);
        setOpaque(false);


        enumMap = new EnumMap<PixelColor, Color>(PixelColor.class);
        enumMap.put(PixelColor.BLUE, Color.BLUE);
        enumMap.put(PixelColor.CYAN, Color.CYAN);
        enumMap.put(PixelColor.RED, Color.RED);
        enumMap.put(PixelColor.GREEN, Color.GREEN);
        enumMap.put(PixelColor.ORANGE, new Color(255, 131, 10));
        enumMap.put(PixelColor.PURPLE, Color.MAGENTA);
        enumMap.put(PixelColor.BORDER, Color.YELLOW);
        enumMap.put(PixelColor.BLACK, Color.BLACK);

    }

    public void bindKeys(){
        iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        aMap = getActionMap();

        leftAction = new LeftAction();
        rightAction = new RightAction();
        actionReleased = new Released();

        iMap.put(KeyStroke.getKeyStroke(p.getLeftKey(), 0, false), "left_pressed");
        aMap.put("left_pressed", leftAction);

        iMap.put(KeyStroke.getKeyStroke(p.getLeftKey(), 0, true), "left_released");
        aMap.put("left_released", actionReleased );

        iMap.put(KeyStroke.getKeyStroke(p.getRightKey(), 0, false), "right_pressed");
        aMap.put("right_pressed", rightAction);

        iMap.put(KeyStroke.getKeyStroke(p.getRightKey(), 0, true), "right_released");
        aMap.put("right_released", actionReleased );
    }

    public void paintComponent(final Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //BufferedImage drawer = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);

        if(p.isAlive){
            insertCircle(g2);
            paintScreen(g2);
        }
        //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
    }

    public void insertCircle(Graphics2D g2) {
        // Loopar igenom alla coords i trail, från äldsta till nyaste.
        // Ritar ett linje-segment med typ drawline(from, to).
        // Hoppa över typ var 100-105e koordinat för hål

        for(double i= p.getX()-p.getRadius()*2; i < p.getX()+p.getRadius()*2 ;i++){
            ArrayList<Double> tst = new ArrayList<Double>();
            for(double j= p.getY()-p.getRadius()*2 ; j < p.getY()+p.getRadius()*2 ;j++){
                tst.add((calcDistanceTo(i,j)));

                //System.out.println(calcDistanceTo(i,j));
                //System.out.println(calcDistanceTo(i,j) <= p.getRadius());

                if( calcDistanceTo(i,j) <= p.getRadius()-0.5){
                    World.setPixel((int)i,(int)j,p.getColor());


                    //System.out.println(World.getPixel(i,j));

                }

            }

            System.out.println(tst);
        }
        System.out.println("\n\n");

    }

    public void paintScreen(Graphics2D g2){
        for(int i= 0; i < GUI.getScreenWidth() ;i++){

            for(int j= 0 ; j < GUI.getScreenHeight();j++){
                g2.setColor((Color)enumMap.get(World.getPixel(i, j)));
                g2.drawLine(i,j,i,j);

            }
        }

    }

    public double calcDistanceTo(double x,double y){
        double distance;
        distance = Math.sqrt( Math.pow(p.getX()-x,2) + Math.pow(p.getY()-y,2) );
        return distance;
    }

    @Override
    public void playerChanged() {
        repaint();

    }

    class LeftAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if (p.isAlive == true) {
                p.setDirection("L");
                System.out.println("LEFT PRESSED");
            } else{
                iMap.put(leftKey, "none");

            }
        }
    }

    class RightAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            if (p.isAlive == true) {
                p.setDirection("R");
                System.out.println("RIGHT PRESSED");
            }else{
                iMap.put(rightKey, "none");}
        }
    }

    class Released extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            p.setDirection("");
            System.out.println("RELEASED");

        }
    }

}


