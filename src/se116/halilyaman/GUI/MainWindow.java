package se116.halilyaman.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MainWindow {

    private JFrame mainFrame;
    private JPanel mainContainer;
    private ArrayList<JCheckBox> userInputContainer;
    private String musicName;
    private JFrame welcomeWindow;
    private boolean isSaved = false;

    // constructor
    public MainWindow(String musicName, JFrame welcomeWindow) {
        this.musicName = musicName;
        this.welcomeWindow = welcomeWindow;
    }

    // build the fundamentals of a window
    public void buildWindow() {
        mainFrame = new JFrame("Music Maker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContainer.setBackground(new Color(50, 70, 105));

        // create buttons
        JButton startButton = new JButton("<html><h4>Play</h4></html>");
        startButton.addActionListener(new StartButtonListener());

        JButton stopButton = new JButton("<html><h4>Pause</h4></html>");
        stopButton.addActionListener(new StopButtonListener());

        JButton tempoUpButton = new JButton("<html><h4>Tempo Up</h4></html>");
        tempoUpButton.addActionListener(new TempoUpButtonListener());

        JButton tempoDownButton = new JButton("<html><h4>Tempo Down</h4></html>");
        tempoDownButton.addActionListener(new TempoDownButtonListener());

        JButton saveButton = new JButton("<html><h2>Save</h2></html>");
        saveButton.addActionListener(new SaveButtonListener());

        JButton loadButton = new JButton("<html><h2>Load</h2></html>");
        loadButton.addActionListener(new LoadButtonListener());

        JButton clearButton = new JButton("<html><h2>Clear</h2></html>");
        clearButton.addActionListener(new ClearButtonListener());

        JButton backButton = new JButton("<html><h2>Back</h2></html>");
        backButton.addActionListener(new BackButtonListener());

        // set the name of music to the top of the window
        JPanel musicNameViewer = new JPanel(new BorderLayout());
        JPanel musicNameContainer = new JPanel();
        JLabel musicNameLabel = new JLabel("<html><h2>"+ musicName +"</h2></html>");
        musicNameContainer.add(musicNameLabel);
        musicNameContainer.setBackground(Color.yellow);
        musicNameViewer.add(BorderLayout.WEST, backButton);
        musicNameViewer.add(BorderLayout.CENTER, musicNameContainer);
        musicNameViewer.setBackground(Color.yellow);
        mainContainer.add(BorderLayout.NORTH, musicNameViewer);

        // set buttons to the right side of window
        JPanel rightViewer = new JPanel();
        rightViewer.setLayout(new GridBagLayout());
        rightViewer.setBackground(new Color(51, 0, 102));

        Box rightButtonsContainer = new Box(BoxLayout.Y_AXIS);
        rightButtonsContainer.add(saveButton);
        rightButtonsContainer.add(loadButton);
        rightButtonsContainer.add(clearButton);
        rightViewer.add(rightButtonsContainer);
        mainContainer.add(rightViewer, BorderLayout.EAST);

        // set buttons to the very bottom of the window
        JPanel bottomViewer = new JPanel();
        bottomViewer.setLayout(new GridBagLayout());
        bottomViewer.setBackground(new Color(100, 0, 0));

        Box bottomButtonsContainer = new Box(BoxLayout.X_AXIS);
        bottomButtonsContainer.add(startButton);
        bottomButtonsContainer.add(stopButton);
        bottomButtonsContainer.add(tempoUpButton);
        bottomButtonsContainer.add(tempoDownButton);
        bottomViewer.add(bottomButtonsContainer);
        mainContainer.add(bottomViewer, BorderLayout.SOUTH);

        // set sound names to the left side of the window
        mainContainer.add(setSoundNames(), BorderLayout.WEST);

        // set CheckBoxes on the middle of the window
        mainContainer.add(BorderLayout.CENTER, setInputArea());

        // TODO: adjust max and min dimensions in order to frame's content
        Dimension maxDimension = new Dimension(1000, 650);
        Dimension minDimension = new Dimension(800, 500);
        mainFrame.setMaximumSize(maxDimension);
        mainFrame.setMinimumSize(minDimension);

        mainFrame.add(mainContainer);
        mainFrame.setSize(1000, 650);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private JPanel setSoundNames() {

        JPanel soundNameViewer = new JPanel(new GridBagLayout());
        soundNameViewer.setBackground(new Color(51, 153, 255));
        soundNameViewer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        Box soundNameContainer = new Box(BoxLayout.Y_AXIS);
        ArrayList<String> soundNameList = new ArrayList<>();

        try {

            String line;

            File instrumentsFilePath = new File("instruments.txt");
            FileInputStream fStream = new FileInputStream(instrumentsFilePath);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(fStream));

            while((line = bReader.readLine()) != null) {
                String soundName = "";
                for(short i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == ',') {
                        break;
                    }
                    soundName += line.charAt(i);
                }
                soundNameList.add(soundName);
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }

        for(String i : soundNameList) {
            JLabel label = new JLabel(i);
            label.setFont(new Font("Serif", Font.BOLD, 20));
            label.setForeground(new Color(0,51,51));
            label.setBorder(new EmptyBorder(0,10,6,10));
            soundNameContainer.add(label);
        }
        soundNameViewer.add(soundNameContainer);

        return soundNameViewer;
    }

    private JPanel setInputArea() {

        JPanel inputAreaViewer = new JPanel();
        JPanel inputArea = new JPanel(new GridLayout(16, 16));

        userInputContainer = new ArrayList<>();

        for(int i = 0; i < 256; i++) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(false);
            checkBox.setBorder(new EmptyBorder(0,10,8,10));
            userInputContainer.add(checkBox);
            inputArea.add(userInputContainer.get(i));
        }

        inputArea.setBackground(new Color(88,100,100));
        inputArea.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        inputAreaViewer.add(inputArea);
        inputAreaViewer.setBorder(new EmptyBorder(47, 0, 0, 0));
        inputAreaViewer.setBackground(new Color(204,102,0));

        return inputAreaViewer;
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TempoUpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TempoDownButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isSaved = true;
        }
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isSaved) {
                welcomeWindow.setVisible(true);
                mainFrame.dispose();
                mainFrame = null;
            } else {
                int choose = JOptionPane.showOptionDialog(null, "You didn't save! Are you sure?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(choose == JOptionPane.YES_OPTION) {
                    welcomeWindow.setVisible(true);
                    mainFrame.dispose();
                    mainFrame = null;
                }
            }
        }
    }
}
