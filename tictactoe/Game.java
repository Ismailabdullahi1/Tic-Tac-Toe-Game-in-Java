package tictactoe;

public class Game {
    private static boolean matchTimerEnabled;
    private static boolean boardInfoEnabled;
    private static boolean playerCounterEnabled;
    private static MusicPlayer musicPlayer;

    public Game() {
        // Initialize settings from SettingsPage
        matchTimerEnabled = SettingsPage.isMatchTimerEnabled();
        boardInfoEnabled = SettingsPage.isBoardInfoEnabled();
        playerCounterEnabled = SettingsPage.isPlayerCounterEnabled();

        // Initialize music player
        musicPlayer = new MusicPlayer();
    }

    public static void startMatchTimer() {
        if (matchTimerEnabled) {
            // Implement the match timer logic here
            System.out.println("Match timer started.");
        } else {
            System.out.println("Match timer is disabled.");
        }
    }

    public static void updateBoardInfo() {
        if (boardInfoEnabled) {
            // Implement the board info logic here
            System.out.println("Board info updated.");
        } else {
            System.out.println("Board info is disabled.");
        }
    }

    public static void updatePlayerCounter() {
        if (playerCounterEnabled) {
            // Implement the player counter logic here
            System.out.println("Player counter updated.");
        } else {
            System.out.println("Player counter is disabled.");
        }
    }

    public static void resetPlayerCounter() {
        if (playerCounterEnabled) {
            // Implement the player counter reset logic here
            System.out.println("Player counter reset.");
        } else {
            System.out.println("Player counter reset is disabled.");
        }
    }

    public void playBackgroundMusic() {
        // Replace with your music file path relative to your project directory
        String musicFilePath = "project/tictactoe/INNA - Bad Boys  Exclusive Online Video.wav";
        musicPlayer.playMusic(musicFilePath);
    }

    public void stopBackgroundMusic() {
        musicPlayer.stopMusic();
    }

    // Example methods that may call the above methods
    public void playGame() {
        startMatchTimer();
        // Game logic here
        updateBoardInfo();
        updatePlayerCounter();
        playBackgroundMusic(); // Start background music
    }

    public void endGame() {
        // Logic to end the game
        stopBackgroundMusic(); // Stop background music
    }
}
