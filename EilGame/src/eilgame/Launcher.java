package eilgame;

/**
 * Questa è la classe che viene utilizzata al lancio del programma: qua vengono 
 * settati i valori riguardanti la grandezza del frame principale
 */
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
