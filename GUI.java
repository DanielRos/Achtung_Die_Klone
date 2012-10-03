import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static int screenWidth;
    private static int screenHeight;
    private Screen s;
    private PlayerComponent pc;
    private JPanel currentPanel;
    private static GUI reference;

    public GUI(PlayerComponent component){
        super("Achtung Die Klone");
        reference = this;
        pc = component;
        pc.setSize(screenWidth,screenHeight);
        setLayout(new BorderLayout());
        //chooseDisplayMode();
        screenWidth = 1280;
        screenHeight = 800;
        createPlayers();
        pack();
        setSize(screenWidth,screenHeight);
        currentPanel = new GamePanel();
        getContentPane().add(currentPanel,BorderLayout.WEST);


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

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
        add(pc);

        //add(GameManager.createPlayer(),BorderLayout.CENTER);
        //add(GameManager.createPlayer(),BorderLayout.CENTER);

    }
    public void run(DisplayMode dm){
        //getContentPane().setBackground(Color.BLACK);
        s = new Screen();
        s.setFullScreen(dm, this);
    }
    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static GUI getFrame(){
        return reference;
    }

    public GamePanel getGamePanel()
    {
        return (GamePanel) currentPanel;
    }

}
