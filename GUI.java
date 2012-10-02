import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static int screenWidth = 1280;
    private static int screenHeight = 900;

    private Screen s;
    private PlayerComponent pc;

    public GUI(PlayerComponent component){
        super("Achtung Die Klone");
        pc = component;
        setLayout(new BorderLayout());
        //chooseDisplayMode();
        setSize(1280,900);
        createPlayers();
        new World(screenWidth,screenHeight);
        System.out.printf("%d : %d\n",screenWidth,screenHeight);
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
        add(pc, BorderLayout.CENTER);

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


}
