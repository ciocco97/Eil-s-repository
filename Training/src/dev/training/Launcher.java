package dev.training;

public class Launcher {

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        String title = "Training";
        int width = 1422;
        int height = 800;
        
        Game game = new Game(title, width, height);
        game.start();
    }
    
}
