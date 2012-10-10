import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupPanel extends JPanel {
    public SetupPanel(int width, int height) {
        setSize(width,height);
        setBackground(Color.BLACK);

        add(startButton());
        setVisible(true);
    }

    public JButton startButton(){
        JButton startButton = new JButton("Start Game");
        final ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameState state = new InGameState();
                GameManager.getInstance().setState(state);


            }
        };

        startButton.addActionListener(al);
        return startButton;
    }
}
