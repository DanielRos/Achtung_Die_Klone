import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.Stroke;

public class SnakePanel extends JPanel{

    private BufferedImage bImage;
    private final Graphics bImageGraphics ;
    private final int borderDistance = 10;

    public SnakePanel(int width,int height) {


        bImage = new BufferedImage(width-borderDistance*2,height-borderDistance*4, BufferedImage.TYPE_INT_ARGB);
        bImage = configureImage(bImage);
        bImageGraphics = bImage.getGraphics();
        Dimension d = new Dimension(width,height);
        setPreferredSize(d);
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
               Player p = pc.getOwner();
                c = p.getNextPixels(p.getCoordinate(),p.getAngle(),p.getSpeed(),p.getRadius());
                int colorOfNextPixel = 0;

                //System.out.println((int)c.getX() + " " + (int)c.getY());
                if (inBounds(c)) colorOfNextPixel = bImage.getRGB((int)c.getX(),(int)c.getY());


                int black = -16777216;
                int yellow = -256;

                if(colorOfNextPixel == black && inBounds(c)
                        || colorOfNextPixel == yellow && inBounds(c)){
                    change = pc.paintPlayer(bImageGraphics);
                    super.repaint(change);

                }

                else{
                    if (p.isAlive) System.out.println(pc.getOwner().getColor());
                    p.isAlive = false;
                    //System.out.println(pc.getOwner().getColor());
                    //System.out.println(bImage.getRGB((int)c.getX(),(int)c.getY()));
                }

            }

        }

    }

    private boolean inBounds(Coordinate c) {

        int x = (int)c.getX();
        int y = (int)c.getY();
        int bx = bImage.getWidth();
        int by = bImage.getHeight();

        return ( x > 0 &&
                y > 0 &&
                x < bx &&
                y < by );
    }


    public static SnakePanel getInstance(){
        return GUI.getFrame().getGamePanel().getSnakePanel();
    }

}

