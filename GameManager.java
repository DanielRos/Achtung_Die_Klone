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
    private static GameState setupState;
    private static GameState inGameState;
    private static GameState gameEndedState;
    private static Boolean gamePaused = true;
    private static int maxScore;
    private static GameManager reference;

    public GameManager() {
        this.reference = this;
        players = new Vector<Player>(6);
        pComponents = new Vector<PlayerComponent>(6);
        setupState = new SetupState();
        inGameState = new InGameState();
        currentState = setupState;
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

    public static void setState(String changes, String setup, String inGame){
        if (changes.equals(inGame)){
            currentState = inGameState;
        }
        else if(changes.equals(setup)){
            currentState = setupState;
        }
    }

    public static GameState getCurrentState() {
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

    public static int getPlayersAlive() {
        int alivePlayers = 0;
        for (Player p: players){
            if (p.isAlive) alivePlayers++;
        }
        return alivePlayers;
    }
    public Player getCurrentLeader(){
        int leaderPos = 0;
        for (int i = 0; i< players.size(); i++){
            if (players.get(i).getScore() > players.get(leaderPos).getScore()) {leaderPos = i;}
        }

        return players.get(leaderPos);
    }
    public static int getMaxScore(){
        return maxScore;
    }

    public class SetupState implements GameState{
        public void tick(){
           SetupPanel.getInstance().updateUI();
        }
    }

    public class InGameState implements GameState{

        public void tick() {

            Vector<Player> players = GameManager.getPlayers();

            if(GameManager.getInstance().getCurrentLeader().getScore()<GameManager.getMaxScore()){
                if(!GameManager.isPaused() && GameManager.getPlayersAlive() > 1){
                    SnakePanel.getInstance().setDrawArrows(false);
                    for (Player p: players){
                        p.move();

                    }
                }
                else if(GameManager.isPaused()){
                    SnakePanel.getInstance().setDrawArrows(true);
                }
                else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {}
                    for (Player p: players){
                        p.newRound();
                        GameManager.setPaused(true);
                        SnakePanel.getInstance().clearField();

                    }
                }
            }
            else{
                System.out.println("Game Over!");

            }
        }
    }
}

