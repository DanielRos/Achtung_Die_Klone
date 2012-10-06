import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.Stroke;

public class SnakePanel extends JPanel{

    private BufferedImage bImage;
    private Graphics bImageGraphics ;
    private boolean drawBorders = false;
    private int borderDistance;


    public SnakePanel(int width,int height) {

        borderDistance = 10;

        bImage = new BufferedImage(width-borderDistance*2,height-borderDistance*4, BufferedImage.TYPE_INT_ARGB);
        bImage = configureImage(bImage);
        bImageGraphics = bImage.getGraphics();
        Dimension d = new Dimension(width,height);
        setSize(width,height);
        setPreferredSize(d);
        System.out.println("Snake: " + getWidth());
        drawBorders = true;
        setVisible(true);

    }

    public static BufferedImage configureImage(BufferedImage bImage) {

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();

            BufferedImage img2 = gc.createCompatibleImage(bImage.getWidth(), bImage.getHeight(),Transparency.BITMASK);
            Graphics2D g = img2.createGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0,0,bImage.getWidth(),bImage.getHeight());

            g.dispose();

            return img2;
    }


    public void paint(Graphics g){
        g.drawImage(bImage,0,0,null);

    }

    public void repaint(){
        if(bImageGraphics != null){
            Rectangle change;
            Coordinate c;
            for(PlayerComponent pc: GameManager.getpComponents()){
                c = pc.getOwner().getNextPixels();

                System.out.println((int)c.getX() + " " + (int)c.getY());
                int colorOfNextPixel = bImage.getRGB((int)c.getX(),(int)c.getY());

                System.out.println(inBounds(c));

                if(colorOfNextPixel == -16777216 && inBounds(c) == true){
                    change = pc.paintPlayer(bImageGraphics);
                    super.repaint(change);
                }

                else{pc.getOwner().isAlive = false;
                }

            }

        }

    }

    private boolean inBounds(Coordinate c) {
        Boolean inside= true;
        int x = (int)c.getX();
        int y = (int)c.getY();
        if( x < 1 && y < 1 && x > bImage.getWidth() - 1 && y > bImage.getHeight() - 1)inside = false;
        return inside;
    }


    public static SnakePanel getInstance(){
        return GUI.getFrame().getGamePanel().getSnakePanel();
    }

}

