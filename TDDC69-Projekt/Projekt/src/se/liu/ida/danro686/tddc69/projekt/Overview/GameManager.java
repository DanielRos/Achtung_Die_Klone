package se.liu.ida.danro686.tddc69.projekt.Overview;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.GUI;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.GameEndedPanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.SetupPanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.SnakePanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.PlayerComponent;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.Player;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.PowerUp;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.powerUps.ClearFieldPowerUp;
import se.liu.ida.danro686.tddc69.projekt.Overview.Models.PowerUp.powerUps.SpeedPowerUp;

import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

public class GameManager {
    private static Vector<Player> players;
    private static Vector<PlayerComponent> pComponents;
    private static Class[] powerUpTypes;
    private static Vector<PowerUp> powerups;
    private static GameState currentState;
    private static GameState setupState;
    private static GameState inGameState;
    private static GameState gameEndedState;
    private static Boolean gamePaused = true;
    private static int maxScore;
    private static int powerUpCounter;
    private static final int powerUpDelay = 600;

    // se.liu.ida.danro686.tddc69.projekt.Overview.GameManager håller reda på översikten av spelet. Den skapar spelare samt spelarkomponenter, powerups
    // och det som behövs för att styra en spelomgång
    public GameManager() {
        players = new Vector<Player>(6);
        pComponents = new Vector<PlayerComponent>(6);
        powerUpTypes = new Class[]{SpeedPowerUp.class, ClearFieldPowerUp.class};
        powerups = new Vector<PowerUp>();
        setupState = new SetupState();
        inGameState = new InGameState(this);
        gameEndedState = new GameEndedState();
        currentState = setupState;
        powerUpCounter = powerUpDelay;

        //skapar en ny frame samt de olika paneler som behövs
        new GUI();
    }

    public static PlayerComponent createPlayer(String name, Color color, int leftKey, int rightKey){
        Player player = new Player(name,color, leftKey, rightKey);
        PlayerComponent pComp = new PlayerComponent(player);
        players.add(player);
        player.addPlayerListener(pComp);
        pComponents.add(pComp);
        maxScore = (players.size()-1)*10 ;

        return(pComp);
    }

    public static void newRound(Boolean resetScores){
        for (Player p: players){
            p.newRound();
            powerups.removeAllElements();
            powerUpCounter = powerUpDelay;
            setPaused(true);
            SnakePanel.getInstance().clearField();
            if(resetScores)p.resetScore();
        }
    }

    public static Vector<PowerUp> getPowerUps(){
        return powerups;
    }

    public static void setState(String changes, String setup, String inGame, String gameEnded){
        if (changes.equals(inGame)){
            currentState = inGameState;
        }
        else if(changes.equals(setup)){
            currentState = setupState;
        }
        else if(changes.equals(gameEnded)){
            currentState = gameEndedState;
        }
    }

    public GameState getCurrentState() {
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

    private static int getPlayersAlive() {
        int alivePlayers = 0;
        for (Player p: players){
            if (p.isAlive) alivePlayers++;
        }
        return alivePlayers;
    }

    void managePowerUps(){
        powerUpCounter--;
        if(powerUpCounter < 1){
            addPowerUp();
            powerUpCounter = powerUpDelay;
        }
    }

    void addPowerUp(){
        int randomNr = Utility.random(powerUpTypes.length);
        Class puClass = powerUpTypes[randomNr];
        try {
            powerups.add((PowerUp) puClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    Player getCurrentLeader(){
        int leaderPos = 0;
        for (int i = 0; i< players.size(); i++){
            if (players.get(i).getScore() > players.get(leaderPos).getScore()) {leaderPos = i;}
        }

        return players.get(leaderPos);
    }

    public static ArrayList<Player> sortByScores() {
        ArrayList<Player> playerList = getPlayersInArray();
        final ArrayList<Integer> scores = getAllScores();
        TreeMap<Integer,ArrayList<Player>> treeMap = new TreeMap<Integer,ArrayList<Player>>();
        for (int i=0; i<players.size(); i++) {
            Integer key = - scores.get(i);
            if (treeMap.get(key)==null)
                treeMap.put(key, new ArrayList<Player>());
            treeMap.get(key).add(playerList.get(i));
        }
        ArrayList<Player> sortedPlayerList = new ArrayList<Player>(playerList.size());
        for (ArrayList<Player> set: treeMap.values()) {
            sortedPlayerList.addAll(set);
        }
        return sortedPlayerList;
    }

    private static ArrayList<Integer> getAllScores(){
        ArrayList<Integer> scores = new ArrayList<Integer>();
        for(Player p : players){
            scores.add(p.getScore());
        }
        return scores;
    }

    private static ArrayList<Player> getPlayersInArray(){
        ArrayList<Player> aList = new ArrayList<Player>(players.size());

        for(Player p : players){
            aList.add(p);
        }

        return aList;
    }

    public static int getMaxScore(){
        return maxScore;
    }


    public interface GameState {
        public void tick() throws InterruptedException;
    }

    private class InGameState implements GameState{
        private final GameManager gm;
        public InGameState(GameManager gm) {
            this.gm = gm;
        }

        public void tick() throws InterruptedException {
            if(getCurrentLeader().getScore()<getMaxScore()){
                if(!isPaused() && getPlayersAlive() > 1){
                    SnakePanel.getInstance().setDrawArrows(false);
                    for (Player p: players){
                        p.move();

                        //kollar ifall spelaren kört på en powerup
                        for (PowerUp pu : powerups){
                            if(pu.HitByPlayer(p.getX()+p.getRadius(),p.getY()+p.getRadius())){
                                pu.setOwner(p);
                                pu.activate();
                                powerups.remove(pu);
                                break;
                            }
                        }
                    }
                    gm.managePowerUps();
                }
                else if(isPaused()){
                    SnakePanel.getInstance().setDrawArrows(true);
                }
                else {
                    Thread.sleep(1500);
                    newRound(false);
                }
            }
            else{
                GUI.getInstance().setPanel("Game Ended");
                GameEndedPanel.getInstance().anotherGameEnded();
            }
        }
    }
    private class SetupState implements GameState{
        public void tick(){
            SetupPanel.getInstance().updateUI();
        }
    }

    private class GameEndedState implements GameState{

        public void tick(){
            GameEndedPanel.getInstance().showPlayerScores();
        }
    }
}

