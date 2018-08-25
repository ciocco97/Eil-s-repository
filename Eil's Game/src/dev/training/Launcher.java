package dev.training;

import dev.conn.client.Client;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = 1400;//Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = 800;//Toolkit.getDefaultToolkit().getScreenSize().height;
        int PORT = 7777;
        String serverAddress = "localhost";
        Game game = new Game(title, width, height, PORT, serverAddress);
        game.start();
    }
    
}
