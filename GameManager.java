import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

//Command pattern
public class GameManager {
    static ArrayList<Player> playerList = new ArrayList<Player>(6);
    static PlayerComponent component;

    public GameManager() {
        Player p = new Player(300,400,"player1",PixelColor.CYAN,KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        component = new PlayerComponent(p);
        p.addPlayerListener(component);
        playerList.add(p);
        new GUI(component);

    }

    public void tick(){
        for (Player p: playerList){
            p.move();
        }
    }

/*    public void addPlayerListener(PlayerListener listener){
        for (Player p : playerList)
            p.addPlayerListener(listener);
    }*/
    public static PlayerComponent createPlayer(){
        Player p2 = new Player(300,400,"player2",PixelColor.CYAN, KeyEvent.VK_Q, KeyEvent.VK_E);
        PlayerComponent pComp = new PlayerComponent(p2);
        playerList.add(p2);
        p2.addPlayerListener(pComp);

        return(pComp);
    }

    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }
}
