package dev.training;

import dev.training.display.Display;
import dev.training.gfx.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Per fare in modo che questa classe venga eseguita come un thread dobbiamo 
 * aggiungere alla dichiarazione della classe "implements Runnable"
 */
public class Game implements Runnable{
    
    private Display display;
    
    public String title;
    public int width, height;
    
    private boolean running = false;
    private Thread thread;
    
    /**
     * Si può pensare ad una variabile di tipo BufferStrategy come ad un modo 
     * che il computer ha di disegnare "cose" sullo schermo e un modo di dire al 
     * computer come dovrebbe disegnarle.
     * Un buffer può essere infatti pensato come una schermata del computer che 
     * ancora non puoi vedere. Maggiore è il numero di buffer (schermate che 
     * verranno rappresentate in futuro) meno possibilità ci sono di avere lag.
     */
    private BufferStrategy bs;
    /**
     * Si può pensare ad un oggetto di tipo Graphics come ad un pennello in 
     * grado di disegnare immagini sul canvas
     */
    private Graphics g;
    
    private BufferedImage testImage;
    
    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    /**
     * Questo metodo serve ad inizializzare tutta la grafica del gioco
     */
    private void init() {
        display = new Display(title, width, height);
        testImage = ImageLoader.loadImage("/textures/Bambino.png");
    }
    
    private void update() {
        
    }
    
    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        /**
         * Se bs è nullo significa che è la prima volta che apriamo il gioco e 
         * quindi dobbiamo creare un nuovo buffer. Il buffer sarà di grandezza 3
         */
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        /**
         * Con questa operazione si pulisce una porzione di scermo: il sistema 
         * di riferimento di java parte da in alto a sinistra per cui il frame, 
         * possiamo dire, viene rappresentato dal quarto quadrante del sistema
         * di riferimento cartesiano la direzione dell'asse delle y è top-down)
         */
        g.clearRect(0, 0, width, height);
        /**
         * Inizio disegno
         */
        
        g.drawImage(testImage, 20, 20, null);
        
        /**
         * Fine disegno
         * switch buffer con display
         */
        // switch buffer con display
        bs.show();
        /**
         * Incolla il disegno sul display
         */
        g.dispose();
    }
    
    /**
     * Ogni volta che viene implementata una classe "Runnable", bisogna che 
     * all'interno sia presente il metodo pubblico "run".
     * Nel nostro caso rappresenta il "Game Loop" che si svolge in due fasi:
     * 1 - Aggiornamento delle variabili, della posizione di oggetti, etc...
     * 2 - Rendering
     */
    @Override
    public void run() {
        init();
        
        while(running) {
            update();
            
            render();
        }
        
        stop();
    }
    
    /**
     * Tutte le volte che vogliamo interagire in modo diretto al thread 
     * attraverso una funzione utilizziamo synchronized, in parole povere, 
     * per non creare casini
     */
    public synchronized void start() {
        if(running) // Se il gioco è già in esecuzione
            return;
        running = true;
        /**
         * La classe implementa Runnable per cui possiamo "attaccarla" ad un 
         * thread
         */
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
