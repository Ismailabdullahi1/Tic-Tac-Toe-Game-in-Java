package tictactoe;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPage extends JPanel {
    private JFrame frame;
    private JComboBox<String> gameModeComboBox;
    private JComboBox<String> boardSizeComboBox;
    private static String gameMode = "Single Player"; // Default value
    private static String boardSize = "3x3"; // Default value
    private JCheckBox matchTimerCheckBox;
    private JCheckBox boardInfoCheckBox;
    private JCheckBox playerCounterCheckBox;
    private static boolean matchTimerEnabled = false; // Default value
    private static boolean boardInfoEnabled = false; // Default value
    private static boolean playerCounterEnabled = false; // Default value

    public SettingsPage(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Set background color
        setBackground(new Color(255, 239, 213)); // Papaya whip background

        // Title label
        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(255, 20, 147)); // Deep pink color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Settings panel
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(6, 2, 10, 10));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        settingsPanel.setBackground(new Color(255, 239, 213)); // Papaya whip background

        // Game mode selection
        JLabel gameModeLabel = new JLabel("Select Game Mode:");
        gameModeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gameModeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        settingsPanel.add(gameModeLabel);

        gameModeComboBox = new JComboBox<>(new String[]{"Single Player", "Multiplayer vs Another Human"});
        gameModeComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        gameModeComboBox.setSelectedItem(gameMode);
        settingsPanel.add(gameModeComboBox);

        // Board size selection
        JLabel boardSizeLabel = new JLabel("Select Board Size:");
        boardSizeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        boardSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        settingsPanel.add(boardSizeLabel);

        boardSizeComboBox = new JComboBox<>(new String[]{"Default", "3x3", "4x4", "5x5", "6x6"});
        boardSizeComboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        boardSizeComboBox.setSelectedItem(boardSize);
        settingsPanel.add(boardSizeComboBox);

        // Match Timer setting
        JLabel matchTimerLabel = new JLabel("Match Timer:");
        matchTimerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        matchTimerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        settingsPanel.add(matchTimerLabel);

        matchTimerCheckBox = new JCheckBox("Enable");
        matchTimerCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        matchTimerCheckBox.setSelected(matchTimerEnabled);
        settingsPanel.add(matchTimerCheckBox);

        // Board Info setting
        JLabel boardInfoLabel = new JLabel("Board Info:");
        boardInfoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        boardInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        settingsPanel.add(boardInfoLabel);

        boardInfoCheckBox = new JCheckBox("Enable");
        boardInfoCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        boardInfoCheckBox.setSelected(boardInfoEnabled);
        settingsPanel.add(boardInfoCheckBox);

        // Player Counter setting
        JLabel playerCounterLabel = new JLabel("Player Counter:");
        playerCounterLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        playerCounterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        settingsPanel.add(playerCounterLabel);

        playerCounterCheckBox = new JCheckBox("Enable");
        playerCounterCheckBox.setFont(new Font("Arial", Font.PLAIN, 18));
        playerCounterCheckBox.setSelected(playerCounterEnabled);
        settingsPanel.add(playerCounterCheckBox);

        // Add Settings panel to center
        add(settingsPanel, BorderLayout.CENTER);

        // Save and Back button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Adjust horizontal gap between buttons
        buttonPanel.setBackground(new Color(255, 239, 213)); // Papaya whip background

        // Save button
        JButton saveButton = createGradientButton("Save", new Color(50, 205, 50), new Color(0, 128, 0)); // Lime green to green gradient
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save settings
                String selectedGameMode = (String) gameModeComboBox.getSelectedItem();
                String selectedBoardSize = (String) boardSizeComboBox.getSelectedItem();
                boolean selectedMatchTimer = matchTimerCheckBox.isSelected();
                boolean selectedBoardInfo = boardInfoCheckBox.isSelected();
                boolean selectedPlayerCounter = playerCounterCheckBox.isSelected();

                setGameMode(selectedGameMode);
                setBoardSize(selectedBoardSize);
                setMatchTimerEnabled(selectedMatchTimer);
                setBoardInfoEnabled(selectedBoardInfo);
                setPlayerCounterEnabled(selectedPlayerCounter);

                // Show popup message
                JOptionPane.showMessageDialog(frame, "Settings saved. You can now play the game!");

                // Example usage
                System.out.println("Selected Game Mode: " + getGameMode());
                System.out.println("Selected Board Size: " + getBoardSizeAsInt());
                System.out.println("Match Timer Enabled: " + isMatchTimerEnabled());
                System.out.println("Board Info Enabled: " + isBoardInfoEnabled());
                System.out.println("Player Counter Enabled: " + isPlayerCounterEnabled());
            }
        });
        buttonPanel.add(saveButton);

        // Back button
        JButton backButton = createGradientButton("Back", new Color(255, 69, 0), new Color(255, 165, 0)); // Red to orange gradient
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transitionTo(new WelcomePage(frame));
            }
        });
        buttonPanel.add(backButton);

        // History button
        JButton historyButton = createGradientButton("Game History", new Color(0, 0, 255), new Color(0, 191, 255)); // Blue to deep sky blue gradient
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transitionTo(new HistoryPage(frame));
            }
        });
        buttonPanel.add(historyButton);

        // Reset to Default button
        JButton resetButton = createGradientButton("Reset to Default", new Color(255, 0, 0), new Color(139, 0, 0)); // Red to dark red gradient
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetToDefaultSettings();
            }
        });
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);
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

    // Method to get the selected board size as an integer
    public static int getBoardSizeAsInt() {
        switch (boardSize) {
            case "Default":
                return 3; // Default board size
            case "3x3":
                return 3;
            case "4x4":
                return 4;
            case "5x5":
                return 5;
            case "6x6":
                return 6;
            default:
                return 3; // Default value
        }
    }

    // Method to handle page transition
    private void transitionTo(JPanel newPage) {
        frame.getContentPane().removeAll();
        frame.add(newPage);
        frame.revalidate();
        frame.repaint();
    }

    // Reset to default settings
    private void resetToDefaultSettings() {
        setGameMode("Single Player");
        setBoardSize("3x3");
        setMatchTimerEnabled(false);
        setBoardInfoEnabled(false);
        setPlayerCounterEnabled(false);

        gameModeComboBox.setSelectedItem(gameMode);
        boardSizeComboBox.setSelectedItem(boardSize);
        matchTimerCheckBox.setSelected(matchTimerEnabled);
        boardInfoCheckBox.setSelected(boardInfoEnabled);
        playerCounterCheckBox.setSelected(playerCounterEnabled);

        // Show popup message
        JOptionPane.showMessageDialog(frame, "Settings reset to default values.");
    }

    // Getters and setters for game mode and board size
    public static String getGameMode() {
        return gameMode;
    }

    public static void setGameMode(String gameMode) {
        SettingsPage.gameMode = gameMode;
    }

    public static String getBoardSize() {
        return boardSize;
    }

    public static void setBoardSize(String boardSize) {
        SettingsPage.boardSize = boardSize;
    }

    public static boolean isMatchTimerEnabled() {
        return matchTimerEnabled;
    }

    public static void setMatchTimerEnabled(boolean enabled) {
        SettingsPage.matchTimerEnabled = enabled;
    }

    public static boolean isBoardInfoEnabled() {
        return boardInfoEnabled;
    }

    public static void setBoardInfoEnabled(boolean enabled) {
        SettingsPage.boardInfoEnabled = enabled;
    }

    public static boolean isPlayerCounterEnabled() {
        return playerCounterEnabled;
    }

    public static void setPlayerCounterEnabled(boolean enabled) {
        SettingsPage.playerCounterEnabled = enabled;
    }
}
