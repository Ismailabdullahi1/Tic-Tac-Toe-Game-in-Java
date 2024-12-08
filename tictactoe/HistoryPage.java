package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HistoryPage extends JPanel {
    private JFrame frame;
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public HistoryPage(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Set background color
        setBackground(new Color(240, 255, 255)); // Azure background

        // History label
        JLabel historyLabel = new JLabel("Game History", SwingConstants.CENTER);
        historyLabel.setFont(new Font("Arial", Font.BOLD, 32));
        historyLabel.setForeground(new Color(30, 144, 255)); // Dodger blue color
        add(historyLabel, BorderLayout.NORTH);

        JTextArea historyArea = new JTextArea();
        historyArea.setFont(new Font("Arial", Font.PLAIN, 18));
        historyArea.setEditable(false);
        historyArea.setBackground(new Color(240, 255, 255));

        loadHistoryFromDatabase(historyArea);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createGradientButton("Back to Welcome", new Color(135, 206, 235), new Color(30, 144, 255)); // Light blue to dodger blue
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(new WelcomePage(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton deleteButton = createGradientButton("Delete History", new Color(255, 69, 0), new Color(255, 165, 0)); // Red to orange gradient
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteHistoryFromDatabase();
                loadHistoryFromDatabase(historyArea); // Reload history after deletion
            }
        });

        

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setBackground(new Color(240, 255, 255));
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);
        

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadHistoryFromDatabase(JTextArea historyArea) {
        historyArea.setText(""); // Clear the text area before loading data
        connectToDatabase();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM game_history");

            while (rs.next()) {
                String gameInfo = rs.getString("game_info");
                String timestamp = rs.getString("timestamp");
                historyArea.append(gameInfo + " - " + timestamp + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading game history from database.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeDatabaseResources();
        }
    }

    private void deleteHistoryFromDatabase() {
        connectToDatabase();

        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM game_history");
            JOptionPane.showMessageDialog(frame, "History deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting game history from database.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeDatabaseResources();
        }
    }

    private void saveHistoryToFile(String history) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("game_history.txt"));
            writer.write(history);
            JOptionPane.showMessageDialog(this, "Game history saved to file 'game_history.txt' successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving game history to file.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tictactoedb", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeDatabaseResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
