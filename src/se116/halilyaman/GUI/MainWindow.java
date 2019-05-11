package se116.halilyaman.GUI;

import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class MainWindow {

    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private JFrame mainFrame;
    private JPanel mainContainer;
    private ArrayList<JCheckBox> userInputContainer;
    private String musicName;
    private JFrame welcomeWindow;
    private boolean isSaved = false;
    private int[] instrumentKeys = new int[16];
    private boolean[] checkBoxState;

    // constructor
    public MainWindow(String musicName, JFrame welcomeWindow) {
        this.musicName = musicName;
        this.welcomeWindow = welcomeWindow;
        setUpMidi();
    }

    public ArrayList<JCheckBox> getUserInputContainer() {
        return this.userInputContainer;
    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    // build the fundamentals of a window
    public void buildWindow() {
        mainFrame = new JFrame("Music Maker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(
           BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
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

        JButton backButton = new JButton("<html><h1 style=\"color:#FFFFFF\">Back</h1></html>");
        backButton.setBackground(new Color(0,0,0));
        backButton.addActionListener(new BackButtonListener());

        // set the name of music to the top of the window
        JPanel musicNameViewer = new JPanel(new BorderLayout());
        JPanel musicNameContainer = new JPanel();
        JLabel musicNameLabel = new JLabel();
        if(musicName != null) {
            musicNameLabel.setText("<html><h2>"+ musicName +"</h2></html>");
            musicNameLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            musicNameContainer.add(musicNameLabel);
        }
        musicNameContainer.setBackground(new Color(151, 178, 169));
        musicNameViewer.add(BorderLayout.EAST, backButton);
        musicNameViewer.add(BorderLayout.CENTER, musicNameContainer);
        musicNameViewer.setBackground(new Color(200,0,0));
        mainContainer.add(BorderLayout.NORTH, musicNameViewer);

        // set buttons to the right side of window
        JPanel rightViewer = new JPanel();
        rightViewer.setLayout(new GridBagLayout());
        rightViewer.setBackground(new Color(10, 100, 200));

        Box rightButtonsContainer = new Box(BoxLayout.Y_AXIS);
        rightButtonsContainer.add(saveButton);
        rightButtonsContainer.add(loadButton);
        rightButtonsContainer.add(clearButton);
        rightViewer.add(rightButtonsContainer);
        mainContainer.add(rightViewer, BorderLayout.EAST);

        // set buttons to the very bottom of the window
        JPanel bottomViewer = new JPanel();
        bottomViewer.setLayout(new GridBagLayout());
        bottomViewer.setBackground(new Color(10, 100, 200));

        Box bottomButtonsContainer = new Box(BoxLayout.X_AXIS);
        bottomButtonsContainer.add(startButton);
        bottomButtonsContainer.add(stopButton);
        bottomButtonsContainer.add(tempoUpButton);
        bottomButtonsContainer.add(tempoDownButton);
        bottomViewer.add(bottomButtonsContainer);
        mainContainer.add(bottomViewer, BorderLayout.SOUTH);

        // set instrument names
        mainContainer.add(setInstrumentNames(), BorderLayout.WEST);

        // set input areas
        mainContainer.add(setInputArea(), BorderLayout.CENTER);

        // TODO: adjust max and min dimensions in order to frame's content
        Dimension maxDimension = new Dimension(1000, 650);
        Dimension minDimension = new Dimension(800, 500);
        mainFrame.setMaximumSize(maxDimension);
        mainFrame.setMinimumSize(minDimension);

        mainFrame.add(mainContainer);
        mainFrame.setSize(1300, 780);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private JPanel setInstrumentNames() {

        JPanel nameViewer = new JPanel(new GridBagLayout());
        nameViewer.setBackground(new Color(50, 200, 200));
        Box nameBox = new Box(BoxLayout.Y_AXIS);

        ArrayList<String> soundNameList = new ArrayList<>();

        try {

            String line;

            File instrumentsFilePath = new File("instruments.txt");
            FileInputStream fStream = new FileInputStream(instrumentsFilePath);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(fStream));

            int counter = 0;
            while((line = bReader.readLine()) != null) {
                String soundName = "";
                for(short i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == '&') {
                        String keyString = "";
                        for(int j = i+1; j < line.length(); j++) {
                            keyString += line.charAt(j);
                        }
                        instrumentKeys[counter] = Integer.parseInt(keyString);
                        break;
                    }
                    soundName += line.charAt(i);
                }
                soundNameList.add(soundName);
                counter++;
            }

        } catch(IOException ex) {
            ex.printStackTrace();
        }

        for(String soundName : soundNameList) {
            JLabel nameLabel = new JLabel();
            nameLabel.setText(soundName);
            nameLabel.setFont(new Font("Serif", Font.BOLD, 20));
            nameLabel.setForeground(new Color(0,51,51));
            nameLabel.setBorder(new EmptyBorder(0, 5,11,5));
            nameBox.add(nameLabel);
        }
        nameViewer.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        nameViewer.add(nameBox);

        return nameViewer;
    }

    private JPanel setInputArea() {

        JPanel inputAreaViewer = new JPanel(new BorderLayout());
        JPanel inputArea = new JPanel(new GridLayout(16, 16));

        userInputContainer = new ArrayList<>();

        for(int i = 0; i < 256; i++) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(false);
            checkBox.setBorder(new EmptyBorder(0,10,8,10));
            checkBox.setBackground(new Color(124, 137, 132));
            userInputContainer.add(checkBox);
            inputArea.add(new JPanel(new GridBagLayout()).add(userInputContainer.get(i)));
        }

        inputArea.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        inputArea.setBackground(new Color(124, 137, 132));
        inputAreaViewer.add(inputArea);

        return inputAreaViewer;
    }


    // ************* sound system ***************

    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buildTrackAndStart() {

        int[] trackList = null;

        // get rid of old track
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instrumentKeys[i];

            for(int j = 0; j < 16; j++) {
                JCheckBox jCheckBox = userInputContainer.get(j + i*16);
                if(jCheckBox.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        }

        track.add(makeEvent(192, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void makeTracks(int[] list) {
        for(int i = 0; i < 16; i++) {
            int key = list[i];
            if(key != 0) {
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        }
    }

    private MidiEvent makeEvent(int command, int channel, int data1, int data2, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage shortMessage = new ShortMessage();
            shortMessage.setMessage(command,channel, data1, data2);
            event = new MidiEvent(shortMessage, tick);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return event;
    }

    // ******** inner classes for adding interactivity to buttons ********

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    private class StopButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }

    private class TempoUpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * 1.03));
        }
    }

    private class TempoDownButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor * .97));
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            isSaved = true;
            checkBoxState = new boolean[256];
            for(int i = 0; i < 256; i++) {
                JCheckBox checkBox = (JCheckBox) userInputContainer.get(i);
                if(checkBox.isSelected()) {
                    checkBoxState[i] = true;
                }
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showSaveDialog(mainFrame);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileOutputStream fileOut = new FileOutputStream(file.getPath() + "\\" + musicName + ".ser");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOut);
                    objectOutputStream.writeObject(checkBoxState);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        }
    }

    private class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            boolean[] checkBoxState = null;

            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(mainFrame);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileInputStream fileIn = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileIn);
                    checkBoxState = (boolean[]) objectInputStream.readObject();
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
                for(int i = 0; i < 256; i++) {
                    JCheckBox checkBox = (JCheckBox) userInputContainer.get(i);
                    if(checkBoxState != null) {
                        if(checkBoxState[i]) {
                            checkBox.setSelected(true);
                        } else {
                            checkBox.setSelected(false);
                        }
                    }
                }
                sequencer.stop();
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(JCheckBox checkBox : userInputContainer) {
                checkBox.setSelected(false);
            }
            sequencer.stop();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();

            boolean[] tempCheckBoxState = new boolean[256];
            if(checkBoxState != null) {
                for(int i = 0; i < 256; i++) {
                    JCheckBox checkBox = (JCheckBox) userInputContainer.get(i);
                    if(checkBox.isSelected()) {
                        tempCheckBoxState[i] = true;
                    }
                }

                for(int i = 0; i < 256; i++) {
                    if(tempCheckBoxState[i] != checkBoxState[i]) {
                        isSaved = false;
                    }
                }
            }

            if(isSaved) {
                welcomeWindow.setVisible(true);
                mainFrame.dispose();
                mainFrame = null;
            } else {
                int choose = JOptionPane.showOptionDialog(
                   null,
                   "You didn't save! Are you sure?",
                   "Warning",
                   JOptionPane.YES_NO_OPTION,
                   JOptionPane.INFORMATION_MESSAGE,
                   null,
                   null,
                   null
                );
                if(choose == JOptionPane.YES_OPTION) {
                    welcomeWindow.setVisible(true);
                    mainFrame.dispose();
                    mainFrame = null;
                }
            }
        }
    }
}
