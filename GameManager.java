import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

//Command pattern
public class GameManager {
    private static Vector<Player> players;
    static PlayerComponent component;

    public GameManager() {
        players = new Vector<Player>(6);
        Player p = new Player(new Coordinate(300.0,400.0),"player1",Color.CYAN,KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        component = new PlayerComponent(p);
        p.addPlayerListener(component);
        players.add(p);
        new GUI(component);

    }

    public void tick(){
        for (Player p: players){
            p.move();
        }
    }

/*    public void addPlayerListener(PlayerListener listener){
        for (Player p : playerList)
            p.addPlayerListener(listener);
    }*/
    public static PlayerComponent createPlayer(){
        Player p2 = new Player(new Coordinate(400,200),"player2",Color.CYAN, KeyEvent.VK_Q, KeyEvent.VK_E);
        PlayerComponent pComp = new PlayerComponent(p2);
        players.add(p2);
        p2.addPlayerListener(pComp);

        return(pComp);
    }

    public static Vector<Player> getPlayers() {
        return players;
    }
}
