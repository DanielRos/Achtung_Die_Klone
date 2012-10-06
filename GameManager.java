import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

//Command pattern
public class GameManager {
    private static Vector<Player> players;
    private static Vector<PlayerComponent> pComponents;

    public GameManager() {
        players = new Vector<Player>(6);
        pComponents = new Vector<PlayerComponent>(6);
        new GUI();
    }

    public void tick(){
        for (Player p: players){
            p.move();
        }
    }

    public static PlayerComponent createPlayer(String name, Color color, int leftKey, int rightKey){
        Player player = new Player(name,color, leftKey, rightKey);
        PlayerComponent pComp = new PlayerComponent(player);
        players.add(player);
        player.addPlayerListener(pComp);
        pComponents.add(pComp);

        return(pComp);
    }

    public static Vector<Player> getPlayers() {
        return players;
    }

    public static Vector<PlayerComponent> getpComponents() {
        return pComponents;
    }
}
