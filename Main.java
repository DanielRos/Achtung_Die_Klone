
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Main {

    public static void main(String[] args){
        final GameManager gm = new GameManager();

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               gm.getCurrentState().tick();
            }
        };

        final Timer clockTimer = new Timer(15, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    public static int random(int nr){
        return (int)(Math.random() * nr);
    }

    public static int random(int from, int to){
        return (int)(Math.random() * (to-from)) + from;
    }

    public static double random(double from, double to){
        return (Math.random() * (to-from)) + from;
    }
}

// scorePanel ska visa maxScore och uppdateras från början
// finputsa kod
