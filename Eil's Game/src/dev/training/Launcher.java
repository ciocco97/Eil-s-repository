package dev.training;

import java.awt.Toolkit;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        String serverAddress = "25.82.144.168";
        Game game = new Game(title, serverAddress, width, height);
        game.start();
    }
    
}
