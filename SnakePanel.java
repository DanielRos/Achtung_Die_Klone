import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.Stroke;

public class SnakePanel extends JPanel{

    private BufferedImage bImage;
    private final Graphics bImageGraphics ;
    private final int borderDistance = 10;
    private boolean drawArrows = true;
    private static SnakePanel reference;

    public SnakePanel(int width,int height) {
        reference = this;
        bImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        bImage = configureImage(bImage);
        bImageGraphics = bImage.getGraphics();
        setSize(width, height);
        paintBorders();
        setVisible(true);
    }

    public BufferedImage configureImage(BufferedImage bImage) {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage img2 = gc.createCompatibleImage(bImage.getWidth()-20, bImage.getHeight()-38,Transparency.BITMASK);
        Graphics2D g = img2.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,bImage.getWidth(),bImage.getHeight());
        g.dispose();

        return img2;
    }


    public void paint(Graphics g){
        g.drawImage(bImage,0,0,null);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        for(PlayerComponent pc: GameManager.getpComponents()){
            Player p = pc.getOwner();
            g2.setColor(Color.YELLOW);
            g2.fillOval((int) p.getX() - p.getRadius(), (int) p.getY() - p.getRadius(), p.getRadius() * 2, p.getRadius() * 2);
            if(drawArrows) drawArrows(g2, p);
            repaint(new Rectangle(0,0,bImage.getWidth(),bImage.getHeight()));
        }
    }
    public void drawArrows(Graphics2D g2, Player p) {

        Coordinate from = p.getNextPixel(p.getCoordinate(),p.getAngle(), p.getRadius());
        Coordinate to = p.getNextPixel(p.getCoordinate(),p.getAngle(), p.getRadius()*4);
        g2.setColor(p.getColor());
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(from.getIntX(),from.getIntY(),to.getIntX(),to.getIntY());

    }

    public void repaint(){

        if(bImageGraphics != null){
            Rectangle change;
            Coordinate c;
            int black = -16777216;

            for(PlayerComponent pc: GameManager.getpComponents()){
                Player p = pc.getOwner();
                c = p.getNextPixel(p.getCoordinate(), p.getAngle(), p.getRadius());
                int colorOfNextPixel = 0;
                if (inBounds(c)) colorOfNextPixel = bImage.getRGB(c.getIntX(),c.getIntY());
                if(colorOfNextPixel == black && inBounds(c)){
                    change = pc.paintPlayer(bImageGraphics);
                    super.repaint(change);
                }
                else{
                    playerCollided(p);
                }
            }
        }
    }

    private void playerCollided(Player p){
        if (p.isAlive) {
            p.setAlive(false);
            for(PlayerComponent pComp: GameManager.getpComponents()){
                Player pl = pComp.getOwner();
                if (pl.isAlive){
                    pl.increaseScore();
                    ScorePanel.getInstance().updateScores();
                }
            }
        }
    }

    public void paintBorders(){
        Graphics2D g2 = (Graphics2D)bImageGraphics;
        int width = bImage.getWidth()-borderDistance;
        int height = bImage.getHeight()-borderDistance;

        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(borderDistance,borderDistance,width,borderDistance);
        g2.drawLine(width,borderDistance,width,height);
        g2.drawLine(borderDistance,height,width,height);
        g2.drawLine(borderDistance,borderDistance,borderDistance,height);
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

    public void clearField(){
        Graphics g = bImageGraphics;
        g.setColor(Color.BLACK);
        g.fillRect(0,0,bImage.getWidth(),bImage.getHeight());
        paintBorders();
    }

    public void setDrawArrows(boolean yesOrNo) {
        this.drawArrows = yesOrNo;
    }

    public static SnakePanel getInstance(){
        return reference;
    }

}

