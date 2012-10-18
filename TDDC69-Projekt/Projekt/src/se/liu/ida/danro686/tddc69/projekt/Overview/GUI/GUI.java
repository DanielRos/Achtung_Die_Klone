package se.liu.ida.danro686.tddc69.projekt.Overview.GUI;

import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.GameEndedPanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.GamePanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GUI.Panels.SetupPanel;
import se.liu.ida.danro686.tddc69.projekt.Overview.GameManager;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static int frameWidth;
    private static int frameHeight;

    private final JPanel cards;

    // Dessa strängar kopplas till respektive panel och används som parametrar för att byta mellan paneler
    // samt skickas med som parametrar till setState() i se.liu.ida.danro686.tddc69.projekt.Overview.GameManager ändrar till ett nytt state
    private final static String SETUPPANEL = "Setup";
    private final static String GAMEPANEL = "Start Game";
    private final static String GAMEENDEDPANEL = "Game Ended";

    private static GUI reference;

    public GUI(){
        super("Achtung Die Klone");
        reference = this;

        // en optionpane där man kan välja mellan att använda fullskärm samt vilken resolution eller spela i ett
        // vanligt fönster
        chooseDisplayMode();

        // CardLayout används för att lätt kunna skifta mellan olika paneler. detta görs i setPanel() och den skiftar
        // även gameState
        JPanel card1 = new SetupPanel(frameWidth, frameHeight);
        JPanel card2 = new GamePanel();
        JPanel card3 = new GameEndedPanel(frameWidth,frameHeight);
        cards = new JPanel(new CardLayout());
        cards.setLayout(new CardLayout());
        cards.add(card1, SETUPPANEL);
        cards.add(card2, GAMEPANEL);
        cards.add(card3, GAMEENDEDPANEL);
        add(cards);
    }

    private void chooseDisplayMode(){
        DisplayMode choice;

        Object[] options = {"800x600", "1280x800", "1440x900", "windowed mode"};
        int optionChosen = JOptionPane.showOptionDialog(this,
                "What resolution do you wanna run the game in?",
                "Choose Display Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[3]);
        if(optionChosen==0){
            choice =  new DisplayMode(800,600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            frameWidth = 800;
            frameHeight = 600;
            run(choice);
        }
        else if (optionChosen==1){
            choice = new DisplayMode(1200,800, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            frameWidth = 1200;
            frameHeight = 800;
            run(choice);
        }
        else if (optionChosen==2){
            choice = new DisplayMode(1440,900, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            frameWidth = 1440;
            frameHeight = 900;
            run(choice);
        }
        else if (optionChosen == 3){
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
            frameWidth = 1280;
            frameHeight = 800;
            setSize(frameWidth,frameHeight);
        }
        else{System.exit(0);}


    }

    private void run(DisplayMode dm){
        Screen s = new Screen();
        s.setFullScreen(dm, this);
    }

    public static int getFrameWidth() {
        return frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }

    public static GUI getInstance(){
        return reference;
    }

    public void setPanel(String changeTo)
    {
        CardLayout cl = (CardLayout)cards.getLayout();
        cl.show(cards, changeTo);
        // när panelen byts ut måste även gameState ändras
        GameManager.setState(changeTo, SETUPPANEL, GAMEPANEL, GAMEENDEDPANEL);

    }
}
