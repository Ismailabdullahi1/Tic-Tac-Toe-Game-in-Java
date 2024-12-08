package tictactoe;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class TicTacToe {
    private static MusicPlayer musicPlayer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Add a WindowListener to stop music when the window is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                musicPlayer = new MusicPlayer();
                musicPlayer.playMusic("project/tictactoe/INNA - Bad Boys  Exclusive Online Video.wav");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (musicPlayer != null) {
                    musicPlayer.stopMusic();
                }
            }
        });

        frame.add(new WelcomePage(frame));
        frame.setVisible(true);
    }
}
