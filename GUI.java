import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static int screenWidth;
    private static int screenHeight;
    private Screen s;

    JPanel cards;
    final static String SETUPPANEL = "Setup";
    final static String GAMEPANEL = "Start Game";
    private static GUI reference;

    public GUI(){
        super("Achtung Die Klone");
        reference = this;

        //chooseDisplayMode();
        screenWidth = 1280;
        screenHeight = 800;

        setSize(screenWidth,screenHeight);

        JPanel card1 = new SetupPanel(screenWidth, screenHeight);

        JPanel card2 = new GamePanel();
        cards = new JPanel(new CardLayout());
        cards.setLayout(new CardLayout());
        cards.add(card1, SETUPPANEL);
        cards.add(card2, GAMEPANEL);
        add(cards);

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

    public void setPanel(String changeTo)
    {
        CardLayout cl = (CardLayout)cards.getLayout();
        cl.show(cards, changeTo);

        GameManager.setState(changeTo, SETUPPANEL, GAMEPANEL);

    }
}
