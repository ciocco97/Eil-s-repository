package dev.training;

import java.awt.Toolkit;

public class Launcher {

    public static void main(String[] args) {
        String title = "Training";

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        String serverAddress = "localhost";
        Game game = new Game(title, serverAddress, width, height);
        game.start();
    }
    
}
