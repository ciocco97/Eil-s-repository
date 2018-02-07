package dev.training;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = 1920;
        int height = 1080;
        
        Game game = new Game(title, width, height);
        game.start();
    }
    
}
