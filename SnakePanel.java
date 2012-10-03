import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class SnakePanel extends JPanel{

    private BufferedImage bImage;
    private Graphics bImageGraphics ;
    private boolean drawBorders = false;
    private Vector<Player> players;
    private int count = 0;

    public SnakePanel(int width,int height) {


        players = GameManager.getPlayers();
        bImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        bImage = configureImage(bImage, false);
        bImageGraphics = bImage.getGraphics();
        Dimension d = new Dimension(width,height);
        setSize(width,height);
        setPreferredSize(d);
        System.out.println("Snake: " + getWidth());
        setVisible(true);

    }

    public static BufferedImage configureImage(BufferedImage bImage, boolean created) {

        if(!created){
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();

            boolean isTransparent = bImage.getColorModel().hasAlpha();

            BufferedImage img2 = gc.createCompatibleImage(bImage.getWidth(), bImage.getHeight(),Transparency.OPAQUE);
            Graphics2D g = img2.createGraphics();
            g.drawImage(bImage, 0, 0, null);
            g.setColor(Color.RED);
            //System.out.println(isTransparent);
            //System.out.println(bImage.getMinX() + " " + bImage.getMinY() + " " + bImage.getWidth() + " " + bImage.getHeight());
            //System.out.println();
            g.setColor(Color.ORANGE);
            //g.fillRect(0,0,100,100);
            g.fillOval(0,0,PlayerComponent.getOwner().getRadius()*2,PlayerComponent.getOwner().getRadius()*2);
            g.dispose();

            System.out.println("frame: "+GUI.getFrame().getWidth());
            System.out.println("gamePanel: "+GamePanel.getGamePanel().getWidth());
            //System.out.println(getInstance().getWidth());
            System.out.println("playerComponent: "+PlayerComponent.getReference().getWidth());
            System.out.println(bImage.getWidth()+ "< width  pixel som man inte kan se > " + bImage.getRaster().getHeight() );
            return img2;

        } else{
            Graphics g = bImage.getGraphics();
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(bImage,0,0,null);
            g2.setColor(Color.CYAN);
            if(getInstance().count % 10 > 5 && getInstance().count %10 < 10 ) g2.setColor(Color.RED);
            g2.fillRect(0,0,100,100);
            getInstance().count++;
            g2.dispose();
            return bImage;
        }
    }


    public void paint(Graphics g){
        g.drawImage(bImage,0,0,null);

    }

    public void repaint(){
        if(bImageGraphics != null){
            PlayerComponent.paintPlayer(bImageGraphics);
            System.out.println( bImage.getRGB((int) PlayerComponent.getOwner().getX()+20, (int) PlayerComponent.getOwner().getY()+20));
            //System.out.println(PlayerComponent.getOwner().getX());
            //if(count > 200)configureImage(bImage,true);
           // System.out.println(count);
            //count++;
            super.repaint();
        }
    }


    public Graphics getPanelGraphics(){
        return bImageGraphics;
    }

    public BufferedImage getImage() {
        return bImage;
    }

    public static SnakePanel getInstance(){
        return GUI.getFrame().getGamePanel().getSnakePanel();
    }

}

