import sun.security.jca.GetInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GUI extends JFrame {

    private static int screenWidth;
    private static int screenHeight;
    private Screen s;

    private JPanel currentPanel;
    private static GUI reference;

    public GUI(){
        super("Achtung Die Klone");
        reference = this;

        //chooseDisplayMode();
        screenWidth = 1280;
        screenHeight = 800;

        setSize(screenWidth,screenHeight);

        currentPanel = new SetupPanel(screenWidth, screenHeight);

        currentPanel.setLocation(0,0);
        getContentPane().add(currentPanel);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    public void chooseDisplayMode(){
        DisplayMode choice;

        Object[] options = {"800x600", "1280x800", "1440x900"};
        int optionChosen = JOptionPane.showOptionDialog(this,
                "What resolution do you wanna run the game in?",
                "Choose Display Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(optionChosen==0){
            choice =  new DisplayMode(800,600, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            screenWidth = 800;
            screenHeight = 600;
        }
        else if (optionChosen==1){
            choice = new DisplayMode(1200,800, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            screenWidth = 1200;
            screenHeight = 800;
        }
        else if (optionChosen==2){
            choice = new DisplayMode(1440,900, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            screenWidth = 1440;
            screenHeight = 900;
        }
        else{choice = null; System.exit(0);}

        run(choice);
    }
    public void createPlayers(){
        add(GameManager.createPlayer("Gargamel", Color.BLUE, KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT));

        add(GameManager.createPlayer("Smurfan", Color.MAGENTA, KeyEvent.VK_Q,KeyEvent.VK_E));

        add(GameManager.createPlayer("Tron", Color.GREEN, KeyEvent.VK_A,KeyEvent.VK_D));
    }

    public void run(DisplayMode dm){
        s = new Screen();
        s.setFullScreen(dm, this);
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static GUI getInstance(){
        return reference;
    }

    public JPanel getCurrentPanel()
    {
        return currentPanel;
    }

    public void setPanel(GamePanel panel)
    {
        getInstance().getContentPane().removeAll();
        getInstance().getContentPane().invalidate();
        currentPanel = panel;
        currentPanel.requestFocus();
        getInstance().setContentPane(currentPanel);
        validate();
        setVisible(true);

        createPlayers();
    }

}
