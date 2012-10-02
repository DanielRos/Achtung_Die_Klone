import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class View extends JComponent {
    Action leftAction;
    Action rightAction;
    Action actionReleased;
    ActionMap aMap;
    InputMap iMap;
    KeyStroke leftKey;
    KeyStroke rightKey;

    int circleSize = 10;

    public View() {

        setSize(new Dimension(800,600));
        setVisible(true);
        setDoubleBuffered(true);
        //bindKeys();

    }

    /*
    public void bindKeys(){
        iMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        aMap = getActionMap();

        leftAction = new LeftAction();
        rightAction = new RightAction();
        actionReleased = new Released();

        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left_pressed");
        aMap.put("left_pressed", leftAction);

        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left_released");
        aMap.put("left_released", actionReleased );

        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right_pressed");
        aMap.put("right_pressed", rightAction);

        iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right_released");
        aMap.put("right_released", actionReleased );
    }

    public Dimension getPrefferedSize(){
        Dimension dim = new Dimension(840,600);
        return dim;
    }
    public void paintComponent(final Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.YELLOW);
        g2.fillOval((int) p.getX()-circleSize/2, (int) p.getY()-circleSize/2, circleSize, circleSize);
        g2.drawLine((int)p.startPosX,(int)p.startPosY,(int)p.getX(),(int)p.getY());

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
    */
}
