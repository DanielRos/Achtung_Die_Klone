import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private static int frameWidth;
    private static int frameHeight;
    private Screen s;

    JPanel cards;
    final static String SETUPPANEL = "Setup";
    final static String GAMEPANEL = "Start Game";
    final static String GAMEENDEDPANEL = "Game Ended";
    private static GUI reference;

    public GUI(){
        super("Achtung Die Klone");
        reference = this;

        //chooseDisplayMode();
        frameWidth = 1280;
        frameHeight = 800;

        setSize(frameWidth,frameHeight);

        JPanel card1 = new SetupPanel(frameWidth, frameHeight);
        JPanel card2 = new GamePanel();
        JPanel card3 = new GameEndedPanel(frameWidth,frameHeight);
        cards = new JPanel(new CardLayout());
        cards.setLayout(new CardLayout());
        cards.add(card1, SETUPPANEL);
        cards.add(card2, GAMEPANEL);
        cards.add(card3, GAMEENDEDPANEL);
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
            frameWidth = 800;
            frameHeight = 600;
        }
        else if (optionChosen==1){
            choice = new DisplayMode(1200,800, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            frameWidth = 1200;
            frameHeight = 800;
        }
        else if (optionChosen==2){
            choice = new DisplayMode(1440,900, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            frameWidth = 1440;
            frameHeight = 900;
        }
        else{choice = null; System.exit(0);}

        run(choice);
    }

    public void run(DisplayMode dm){
        s = new Screen();
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

        GameManager.setState(changeTo, SETUPPANEL, GAMEPANEL, GAMEENDEDPANEL);

    }
}
