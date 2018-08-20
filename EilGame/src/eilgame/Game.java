package eilgame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Rappresenta il cuore del gioco in quanto da qua vengono gestiti tutte le 
 * altre classi ed i loro metodi
 * Per fare in modo che questa classe venga eseguita come un thread dobbiamo 
 * aggiungere alla dichiarazione della classe "implements Runnable"
 */
public class Game implements Runnable{
    
    private Display display;
    
    public String title;
    private int width, height;
    
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
    
    /**
     * States 
     */
    private State gameState;
    private State menuState;
    
    /**
     * Input
     */
    private KeyManager keyManager;
    
    /**
     * Camera
     */
    private GameCamera gameCamera;
    
    /**
     * Handeler
     */
    private Handeler handeler;
    
    /**
     * Costruttore della classe Game
     * @param title Il nome che verrà visualizzato nell'header del frame
     * @param width La larghezza del frame principale
     * @param height L'altezza del frame principale
     */
    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }
    
    /**
     * Metodo chiamato all'interno del metodo run() come prima istruzione; serve 
     * a inizializzare i principali componenti del gioco.
     */
    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();
        
        handeler = new Handeler(this);
        gameCamera = new GameCamera(handeler, 0, 0);
        
        gameState = new GameState(handeler);
        menuState = new MenuState(handeler);
        State.setState(gameState);
    }
    
    private void update() {
        keyManager.update();
        
        /**
         * Se è il programma ha inizializzato uno stato e quindi possiamo 
         * lavorarci sopra
         */
        if(State.getState() != null)
            State.getState().update();
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
        
        
        if(State.getState() != null)
            State.getState().render(g);
        
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
     * Metodo che verrà eseguito in ogni momento dal lancio del gioco; qua 
     * vengono definiti un insieme di parametri tra cui la "velocità" di gioco;
     * Nel nostro caso rappresenta il "Game Loop" che si svolge in due fasi:
     * 1 - Aggiornamento delle variabili, della posizione di oggetti, etc
     * 2 - Rendering.
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
     * Utilizzato per fornire un'interfaccia alla classe Player ma anche a tutte 
     * quelle che usano l'input da tastiera.
     * @return 
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    /**
     * 
     * @return gameCamera
     */
    public GameCamera getGameCamera() {
        return gameCamera;
    }
    
    /**
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * 
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Metodo per l'avvio di run().
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
    
    /**
     * Metodo per terminare la chiamate di run().
     */
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
