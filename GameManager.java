import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Delayed;

//Command pattern
public class GameManager {
    private static Vector<Player> players;
    private static Vector<PlayerComponent> pComponents;
    private static GameState currentState;
    private static Boolean gamePaused = true;
    private static int maxScore;
    private static GameManager reference;

    public GameManager() {
        this.reference = this;
        players = new Vector<Player>(6);
        pComponents = new Vector<PlayerComponent>(6);
        currentState = new SetupState();
        new GUI();
    }

    public static PlayerComponent createPlayer(String name, Color color, int leftKey, int rightKey){
        Player player = new Player(name,color, leftKey, rightKey);
        PlayerComponent pComp = new PlayerComponent(player);
        players.add(player);
        player.addPlayerListener(pComp);
        pComponents.add(pComp);
        maxScore += 10;
        return(pComp);
    }

    public static Vector<Player> getPlayers() {
        return players;
    }

    public static void setState(GameState state){
        currentState = state;
    }

    public static GameState getState() {
        return currentState;
    }

    public static Vector<PlayerComponent> getpComponents() {
        return pComponents;
    }
    public static Boolean isPaused(){
        return gamePaused;
    }
    public static void setPaused(boolean paused){
        gamePaused = paused;
    }
    public static GameManager getInstance(){
        return reference;
    }

    public int getPlayersAlive() {
        int alivePlayers = 0;
        for (Player p: players){
            if (p.isAlive) alivePlayers++;
        }
        return alivePlayers;
    }
    public Player getCurrentLeader(){
        int leaderPos = 0;
        for (int i = 0; i< players.size(); i++){
            if (players.get(i).getScore() > leaderPos) leaderPos = i;
        }

        return players.get(leaderPos);
    }

    public class SetupState implements GameState{
        public void tick(){
            System.out.println("setup!");
        }
    }
    public class GameEndedState implements GameState{
        public void tick(){

        }
    }

}

