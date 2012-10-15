import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;
import java.util.Vector;


public class SetupPanel extends JPanel implements ActionListener{

    private JLabel title = new JLabel("Setup");
    private Font titleFont = new Font("serif",Font.BOLD, 30);
    private Font mainFont = new Font("serif",Font.BOLD, 20);
    private int NumberOfPlayers;
    private static SetupPanel reference;
    private Boolean readyToGo = false;
    private String[] colors = new String[]{"Grey", "Orange", "Pink", "Red", "Green", "Cyan"};
    private String[] keyBindings = new String[]{"left-right", "Q-E", "num1-num3", "Z-C", "V-N", "U-O", "J-L", "A-D" };
    private Vector<String[]> availableKeys = new Vector<String[]>();

    private HashMap<String,Color> colorHashMap;
    private HashMap<Integer, Color> playersColorMap;
    private HashMap<String, Integer> playersLeftKeyMap;
    private HashMap<Integer, Integer> savePlayerLeftKey;
    private HashMap<String, Integer> playersRightKeyMap;
    private HashMap<Integer, Integer> savePlayerRightKey;
    private HashMap<Integer, String> playersNameMap;


    public SetupPanel(int width, int height) {
        reference = this;
        setLayout(new GridLayout(10,4,30,5));
        setSize(width, height);
        setBackground(Color.BLACK);
        availableKeys.add(keyBindings);
        add(createTitle());
        add(createPlayerComboBox());

        setVisible(true);

        createHashMaps();

    }

    private Container createPlayerComboBox() {
        Container panel = new JPanel();
        panel.setBackground(Color.BLACK);
        String[] nrOfPlayers = {"2","3","4","5","6"};

        JComboBox playerNrBox = new JComboBox(nrOfPlayers);
        playerNrBox.addActionListener(this);
        JLabel label = new JLabel("Choose amount of players:");
        label.setForeground(Color.YELLOW);
        label.setFont(mainFont);
        panel.add(label);
        panel.add(playerNrBox);
        return panel;
    }

    private Container createTitle() {
        Container panel = new JPanel();
        panel.setBackground(Color.BLACK);
        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);
        panel.add(title);
        return panel;
    }

    public Container createStartButton(){
        Container panel = new JPanel();
        panel.setBackground(Color.BLACK);
        JButton startButton = new JButton("Start Game");

        ActionListener cl = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.getInstance().setPanel("Start Game");
                for(int i = 0; i<NumberOfPlayers; i++){
                    System.out.println(playersNameMap);
                    System.out.println(playersColorMap);
                    System.out.println(savePlayerLeftKey);
                    System.out.println(savePlayerRightKey);
                    GUI.getInstance().add(GameManager.createPlayer(playersNameMap.get(i),
                            playersColorMap.get(i), savePlayerLeftKey.get(i), savePlayerRightKey.get(i)));
                }
            }
        };

        startButton.addActionListener(cl);
        startButton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(startButton);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();

        //ifall spelarantalet ändras tas de gamla spelarna bort och ersätts med det nya antalet spelare;
        if(readyToGo){
            for(int i = NumberOfPlayers; i >= 0; i-- ){
                reference.remove(2+i);
                readyToGo = false;
                playersLeftKeyMap.clear();
                playersRightKeyMap.clear();
                playersColorMap.clear();
                playersNameMap.clear();
            }
        }

        NumberOfPlayers = cb.getSelectedIndex()+2;

        for(int i= 0; i < NumberOfPlayers;i++){
            Container c = createPlayerConfig(i);

            reference.add(c);
        }

        if(!readyToGo)reference.add(createStartButton());
        System.out.println(NumberOfPlayers);
        readyToGo = true;
    }

    public Container createPlayerConfig(int pos) {
        Container panel = new JPanel();
        final Integer position = pos;
        panel.setBackground(Color.BLACK);
        String defaultName = "Player " + (pos+1);
        JTextField name = new JTextField(defaultName,10);
        playersNameMap.put(pos, name.getText());

        name.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update(e);
            }

            private void update(DocumentEvent e){
                Document d = e.getDocument();
                System.out.println(d.getLength());
                int length = d.getLength();
                try {
                    playersNameMap.put(position, d.getText(0,length));
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
                System.out.println(playersNameMap +" " + playersColorMap);
            }

        });

        JComboBox ColorCB = new JComboBox(colors);
        ColorCB.setForeground(colorHashMap.get(colors[pos]));
        ColorCB.setSelectedItem(colors[pos]);

        playersColorMap.put(pos,colorHashMap.get(colors[pos]));
        ActionListener ColorBoxListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String color = (String)cb.getSelectedItem();
                Color playerColor = colorHashMap.get(color);
                playersColorMap.put(position,playerColor);
                cb.setForeground(playerColor);

            }
        };
        ColorCB.addActionListener(ColorBoxListener);

        JComboBox KeyBindingsCB = new JComboBox(keyBindings);

        KeyBindingsCB.setSelectedItem(keyBindings[pos]);
        savePlayerLeftKey.put(pos, playersLeftKeyMap.get(keyBindings[pos]));
        savePlayerRightKey.put(pos,playersRightKeyMap.get(keyBindings[pos]));

        ActionListener KeyBindingsListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String keys = (String)cb.getSelectedItem();
                savePlayerLeftKey.put(position, playersLeftKeyMap.get(keys));
                savePlayerRightKey.put(position,playersRightKeyMap.get(keys));

                System.out.println( "left: " + savePlayerLeftKey+ "right: " + savePlayerRightKey);
                System.out.println(playersLeftKeyMap.get(keys) + " " + playersRightKeyMap.get(keys));
            }
        };
        KeyBindingsCB.addActionListener(KeyBindingsListener);

        panel.add(name);
        panel.add(ColorCB);
        panel.add(KeyBindingsCB);
        return panel;
    }

    public void createHashMaps(){
        colorHashMap = new HashMap<String, Color>();
        colorHashMap.put("Grey",Color.GRAY);
        colorHashMap.put("Orange", Color.ORANGE);
        colorHashMap.put("Pink", Color.MAGENTA);
        colorHashMap.put("Red", Color.RED);
        colorHashMap.put("Green", Color.GREEN);
        colorHashMap.put("Cyan", Color.CYAN);

        playersLeftKeyMap = new HashMap<String, Integer>();
        playersLeftKeyMap.put("left-right", KeyEvent.VK_LEFT);
        playersLeftKeyMap.put("Q-E", KeyEvent.VK_Q);
        playersLeftKeyMap.put("A-D", KeyEvent.VK_A);
        playersLeftKeyMap.put("Z-C", KeyEvent.VK_Z);
        playersLeftKeyMap.put("V-N", KeyEvent.VK_V);
        playersLeftKeyMap.put("U-O", KeyEvent.VK_U);
        playersLeftKeyMap.put("J-L", KeyEvent.VK_J);
        playersLeftKeyMap.put("num1-num3", KeyEvent.VK_NUMPAD1);

        playersRightKeyMap = new HashMap<String, Integer>();
        playersRightKeyMap.put("left-right", KeyEvent.VK_RIGHT);
        playersRightKeyMap.put("Q-E", KeyEvent.VK_E);
        playersRightKeyMap.put("A-D", KeyEvent.VK_D);
        playersRightKeyMap.put("Z-C", KeyEvent.VK_C);
        playersRightKeyMap.put("V-N", KeyEvent.VK_N);
        playersRightKeyMap.put("U-O", KeyEvent.VK_O);
        playersRightKeyMap.put("J-L", KeyEvent.VK_L);
        playersRightKeyMap.put("num1-num3", KeyEvent.VK_NUMPAD3);

        playersColorMap = new HashMap<Integer, Color>();
        playersNameMap = new HashMap<Integer, String>();

        savePlayerLeftKey = new HashMap<Integer, Integer>();
        savePlayerRightKey = new HashMap<Integer, Integer>();
    }

    public static SetupPanel getInstance() {
        return reference;
    }
}

