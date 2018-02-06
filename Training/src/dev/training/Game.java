package dev.training;

import dev.training.display.Display;

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
    }
    
    private void update() {
        
    }
    
    private void render() {
        
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
