import javax.swing.*;

import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args){
        final GameManager gm = new GameManager();
        System.out.println("starting");

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
    public static double random(double nr){
        return (Math.random() * nr);
    }

}
// fixa positionering av players
// fixa sortering i scorepanel
// lägg till PowerUps
// gör ordentliga hål
// finputsa kod
// lägg till endgame panel
// lägg till skott