package dev.training;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = 1400;//Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = 800;//Toolkit.getDefaultToolkit().getScreenSize().height;
        String serverAddress = "localhost";
        Game game = new Game(title, serverAddress, width, height);
        game.start();
    }
    
}
