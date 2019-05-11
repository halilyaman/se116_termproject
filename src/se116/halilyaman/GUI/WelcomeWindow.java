package se116.halilyaman.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow {

   private JFrame welcomeFrame;
   private JPanel mainContainer;
   private String musicName;

   // build WelcomeWindow
   public void buildWelcomeWindow() {
      welcomeFrame = new JFrame();
      welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      mainContainer = new JPanel(new BorderLayout());
      mainContainer.setBackground(new Color(50, 70, 105));

      // set header
      JPanel headerViewer = new JPanel();
      headerViewer.setBorder(new EmptyBorder( 50,0,60,0));
      headerViewer.setBackground(new Color(50, 70, 80));
      JLabel header = new JLabel("Music Maker");
      header.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
      header.setBackground(new Color(200, 100, 40));
      header.setOpaque(true);
      headerViewer.add(header);
      mainContainer.add(BorderLayout.NORTH, headerViewer);

      // create buttons
      JButton newMusicButton = new JButton("<html><h1>New Music</h1></html>");
      newMusicButton.setForeground(new Color(0, 250, 250));
      newMusicButton.setBackground(new Color(50, 70, 105));
      newMusicButton.addActionListener(new NewMusicButtonListener());
      newMusicButton.setBorder(new EmptyBorder(15,0,15,0));
      JButton loadButton = new JButton("<html><h1>Load</h1></html>");
      loadButton.setForeground(new Color(0, 250, 250));
      loadButton.setBorder(new EmptyBorder(15,0,15,0));
      loadButton.setBackground(new Color(50, 70, 105));
      loadButton.addActionListener(new LoadButtonListener());
      JButton exitButton = new JButton("<html><h1>Exit</h1></html>");
      exitButton.setForeground(new Color(0, 250, 250));
      exitButton.setBorder(new EmptyBorder(15,0,15,0));
      exitButton.setBackground(new Color(50, 70, 105));
      exitButton.addActionListener(new ExitButtonListener());

      // set buttons
      Box buttonContainer = new Box(BoxLayout.Y_AXIS);
      buttonContainer.setBorder(new EmptyBorder(80, 410, 0, 410));
      buttonContainer.add(newMusicButton);
      buttonContainer.add(loadButton);
      buttonContainer.add(exitButton);
      mainContainer.add(BorderLayout.CENTER, buttonContainer);

      welcomeFrame.add(mainContainer);
      welcomeFrame.setSize(1000, 650);
      welcomeFrame.setLocationRelativeTo(null);
      welcomeFrame.setResizable(false);
      welcomeFrame.setVisible(true);
   }

   private class NewMusicButtonListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         musicName = JOptionPane.showInputDialog(welcomeFrame, "Music Name", null, JOptionPane.QUESTION_MESSAGE);
         if(musicName != null) {
            if(musicName.length() > 0) {
               MainWindow mainWindow = new MainWindow(musicName, welcomeFrame);
               mainWindow.buildWindow();
               welcomeFrame.dispose();
            }
         }
      }
   }

   private class LoadButtonListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
   }

   private class ExitButtonListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         System.exit(1);
      }
   }
}
