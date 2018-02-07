package dev.training;

import dev.training.display.Display;
import dev.training.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

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
        Assets.init();
    }
    
    int x = 0;
    
    private void update() {
        x += 1;
        x = x % 1820;
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
        
        g.drawImage(Assets.full, x, 10, null);
        
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
        
        /**
         * Imposto fps e timePerUpdate per mettere una soglia di minimo tempo 
         * del processore e massima velocità di gioco.
         * Faccio in modo quindi che si possa entrare nell'if un numero limitato 
         * di volte al secondo.
         * Quindi:
         * fps è la variabile che indica il numero di volte che vogliamo 
         * chiamare i metodi di update e render al secondo
         */
        int fps = 60;
        /**
         * timePerUpdate è il massimo tempo in nanosecondi che abbiamo per 
         * eseguire i metodi di update e render in modo da raggiungere i 60 
         * frame al secondo
         */
        double timePerUpdate = 1000000000 / fps;
        
        /**
         * Delta rappresenta il quanto di tempo che abbiamo prima di poter 
         * chiamare ancora i metodi di update e render e facciamo in modo che 
         * vada da 0 a 1
         */
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        /**
         * Variabile che serve a contare fino ad un secondo
         */
        long timer = 0;
        /**
         * Variabile che conta il numero di volte in cui i metodi vengono 
         * chiamati
         */
        int update = 0;
        
        while(running) {
            /**
             * Tempo corrente
             */
            now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                update();

                render();
                update++;
                delta--;
            }
            
            /**
             * Ogni secondo visualizzo quante volte vengono chiamati i metodi di 
             * render e update
             */
            if(timer >= 1000000000) {
                System.out.println("Update and Frames: " + update);
                update = 0;
                timer = 0;
            }
            
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