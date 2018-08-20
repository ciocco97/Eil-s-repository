package dev.training;

import java.awt.Toolkit;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = 1400;//Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = 800;//Toolkit.getDefaultToolkit().getScreenSize().height;
        
        Game game = new Game(title, width, height);
        game.start();
    }
    
}
