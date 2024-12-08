package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JPanel {
    private JFrame frame;

    public WelcomePage(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Set background color
        setBackground(new Color(135, 206, 235)); // Sky blue background

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Tic-Tac-Toe", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(255, 105, 180)); // Hot pink text
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        add(welcomeLabel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(135, 206, 235));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Play button
        JButton playButton = createGradientButton("Play", new Color(255, 165, 0), new Color(255, 69, 0)); // Orange to red gradient

        // Settings button
        JButton settingsButton = createGradientButton("Settings", new Color(0, 128, 0), new Color(0, 255, 127)); // Green to spring green gradient

        buttonPanel.add(playButton);
        buttonPanel.add(settingsButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isMultiplayer = SettingsPage.getGameMode().equals("Multiplayer vs Another Human");
                String boardSizeString = SettingsPage.getBoardSize();
                int boardSize = SettingsPage.getBoardSizeAsInt();
                transitionTo(new PlayPage(frame, isMultiplayer, boardSize));
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transitionTo(new SettingsPage(frame));
            }
        });
    }

    // Method to create gradient buttons
    private JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    // Method to handle page transition
    private void transitionTo(JPanel newPage) {
        frame.getContentPane().removeAll();
        frame.add(newPage);
        frame.revalidate();
        frame.repaint();
    }

    // Static method to update settings
    public static void updateSettings() {
        // Update welcome page based on settings
        // You can perform any required actions here
    }
}
