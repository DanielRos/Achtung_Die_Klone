import javax.swing.*;

import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args){
        final GameManager gm = new GameManager();
        System.out.println("starting");

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
               gm.tick();

            }
        };

        final Timer clockTimer = new Timer(10, doOneStep);
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
// byt ut trail och coordinate mot en worldclass
// World ska ha en 2d array med alla pixlar
// man ska kunna välja storlek på fönstret, och möjligen cirkeln
// kanske fullscreen
// upplösningar 800/600, 1280/900, 1440/900

// för att veta var man ska rita upp cirkeln: <pythagoras sats> z = roten ur(x^2+y^2)
// Om z <= radien ----> rita ut pixeln