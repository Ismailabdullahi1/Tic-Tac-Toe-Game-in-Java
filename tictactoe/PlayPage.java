package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PlayPage extends JPanel {
    private JFrame frame;
    private JLabel timerLabel;
    private JLabel spotsTakenLabel;
    private JLabel humanWinCountLabel;
    private JLabel botWinCountLabel;
    private int humanWinCount = 0;
    private int botWinCount = 0;
    private int spotsTaken = 0;
    private JButton[][] buttons;
    private boolean isPlayerTurn = true; // true for Player 1, false for bot
    private boolean isMultiplayer;
    private Timer gameTimer;
    private int timerSeconds = 0;
    private int boardSize;
    private ArrayList<String> gameHistory = new ArrayList<>();

    public PlayPage(JFrame frame, boolean isMultiplayer, int boardSize) {
        this.frame = frame;
        this.isMultiplayer = isMultiplayer;
        this.boardSize = boardSize;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255)); // Alice blue background

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(173, 216, 230)); // Light blue background
        headerPanel.setLayout(new GridLayout(1, 4)); // Adjusted grid layout

        // Timer label
        timerLabel = new JLabel("Timer: 00:00");
        styleHeaderLabel(timerLabel);
        headerPanel.add(timerLabel);

        // Spots taken label
        spotsTakenLabel = new JLabel("Spots Taken: 0");
        styleHeaderLabel(spotsTakenLabel);
        headerPanel.add(spotsTakenLabel);

        // Human win count label (Player 1 wins in multiplayer)
        humanWinCountLabel = new JLabel(isMultiplayer ? "Player 1 Wins: 0" : "Human Wins: 0");
        styleHeaderLabel(humanWinCountLabel);
        headerPanel.add(humanWinCountLabel);

        // Bot win count label (Player 2 wins in multiplayer)
        botWinCountLabel = new JLabel(isMultiplayer ? "Player 2 Wins: 0" : "Bot Wins: 0");
        styleHeaderLabel(botWinCountLabel);
        headerPanel.add(botWinCountLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Main game panel (board and controls)
        JPanel gamePanel = new JPanel();
        gamePanel.setBackground(new Color(240, 248, 255)); // Match main background
        gamePanel.setLayout(new GridLayout(boardSize, boardSize));

        buttons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }

        add(gamePanel, BorderLayout.CENTER);

        // Back button
        JButton backButton = createGradientButton("Back to Welcome", new Color(135, 206, 235), new Color(30, 144, 255)); // Light blue to dodger blue
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new WelcomePage(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setBackground(new Color(240, 248, 255));
        backButtonPanel.add(backButton);

        add(backButtonPanel, BorderLayout.SOUTH);

        startTimer();
    }

    // Method to style header labels
    private void styleHeaderLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
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

    // Method to check if there is a winner
    private boolean checkWin(String player) {
        // Check rows and columns
        for (int i = 0; i < boardSize; i++) {
            if (buttons[i][0].getText().equals(player)) {
                boolean win = true;
                for (int j = 1; j < boardSize; j++) {
                    if (!buttons[i][j].getText().equals(player)) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
            if (buttons[0][i].getText().equals(player)) {
                boolean win = true;
                for (int j = 1; j < boardSize; j++) {
                    if (!buttons[j][i].getText().equals(player)) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        // Check diagonals
        boolean diagonal1Win = true;
        boolean diagonal2Win = true;
        for (int i = 0; i < boardSize; i++) {
            if (!buttons[i][i].getText().equals(player)) diagonal1Win = false;
            if (!buttons[i][boardSize - i - 1].getText().equals(player)) diagonal2Win = false;
        }
        if (diagonal1Win || diagonal2Win) return true;
        return false;
    }

    // Method to check if there is a draw
    private boolean checkDraw() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to handle bot's move
    private void botMove() {
        // Simple AI for bot move
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    buttons[i][j].setText("O");
                    gameHistory.add("O placed at (" + i + "," + j + ")");
                    spotsTaken++;
                    spotsTakenLabel.setText("Spots Taken: " + spotsTaken);
                    if (checkWin("O")) {
                        botWinCount++;
                        botWinCountLabel.setText(isMultiplayer ? "Player 2 Wins: " + botWinCount : "Bot Wins: " + botWinCount);
                        gameHistory.add("Game result: O wins!");
                        saveGameHistory();
                        JOptionPane.showMessageDialog(this, "Bot wins!");
                        resetBoard();
                    } else if (checkDraw()) {
                        gameHistory.add("Game result: Draw");
                        saveGameHistory();
                        JOptionPane.showMessageDialog(this, "It's a draw!");
                        resetBoard();
                    }
                    return;
                }
            }
        }
    }

    // Method to reset the game board
    private void resetBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setText("");
            }
        }
        spotsTaken = 0;
        spotsTakenLabel.setText("Spots Taken: 0");
        timerSeconds = 0;
        timerLabel.setText("Timer: 00:00");
        isPlayerTurn = true;
        gameHistory.clear();
    }

    // Method to start the game timer
    private void startTimer() {
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerSeconds++;
                int minutes = timerSeconds / 60;
                int seconds = timerSeconds % 60;
                timerLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));
            }
        }, 1000, 1000);
    }

    // Method to save game history to a file
    private void saveGameHistory() {
        try (FileWriter writer = new FileWriter("game_history.txt", true)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            writer.write(formatter.format(date) + "\n");
            for (String move : gameHistory) {
                writer.write(move + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class for handling button clicks
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().isEmpty()) {
                if (isPlayerTurn) {
                    buttons[row][col].setText("X");
                    gameHistory.add("X placed at (" + row + "," + col + ")");
                    spotsTaken++;
                    spotsTakenLabel.setText("Spots Taken: " + spotsTaken);
                    if (checkWin("X")) {
                        humanWinCount++;
                        humanWinCountLabel.setText(isMultiplayer ? "Player 1 Wins: " + humanWinCount : "Human Wins: " + humanWinCount);
                        gameHistory.add("Game result: X wins!");
                        saveGameHistory();
                        JOptionPane.showMessageDialog(PlayPage.this, "Player 1 wins!");
                        resetBoard();
                    } else if (checkDraw()) {
                        gameHistory.add("Game result: Draw");
                        saveGameHistory();
                        JOptionPane.showMessageDialog(PlayPage.this, "It's a draw!");
                        resetBoard();
                    } else {
                        isPlayerTurn = false;
                        if (!isMultiplayer) {
                            botMove();
                            if (!checkWin("O") && !checkDraw()) {
                                isPlayerTurn = true;
                            }
                        }
                    }
                } else {
                    if (isMultiplayer) {
                        buttons[row][col].setText("O");
                        gameHistory.add("O placed at (" + row + "," + col + ")");
                        spotsTaken++;
                        spotsTakenLabel.setText("Spots Taken: " + spotsTaken);
                        if (checkWin("O")) {
                            botWinCount++;
                            botWinCountLabel.setText("Player 2 Wins: " + botWinCount);
                            gameHistory.add("Game result: O wins!");
                            saveGameHistory();
                            JOptionPane.showMessageDialog(PlayPage.this, "Player 2 wins!");
                            resetBoard();
                        } else if (checkDraw()) {
                            gameHistory.add("Game result: Draw");
                            saveGameHistory();
                            JOptionPane.showMessageDialog(PlayPage.this, "It's a draw!");
                            resetBoard();
                        } else {
                            isPlayerTurn = true;
                        }
                    }
                }
            }
        }
    }
}
