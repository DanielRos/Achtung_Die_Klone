import java.util.Vector;

public class InGameState extends GameManager implements GameState{
    public InGameState() {
        GUI.getInstance().setPanel(new GamePanel());
    }

    public void tick() {
        Vector<Player> players = GameManager.getPlayers();
        if(getCurrentLeader().getScore()<30){
            if(!GameManager.isPaused() && getPlayersAlive() > 1){
                SnakePanel.getInstance().setDrawArrows(false);
                for (Player p: players){
                    p.move();
                }
            }
            else if(isPaused()){
                SnakePanel.getInstance().setDrawArrows(true);
            }
            else {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {}
                for (Player p: players){
                    p.newRound();
                    setPaused(true);
                    SnakePanel.getInstance().clearField();
                }
            }
        }
        else{
            System.out.println("Game Over!");
        }
    }
}