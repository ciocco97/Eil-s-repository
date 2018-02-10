package dev.training.display;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display {
    private JFrame frame; // Finestra
    private Canvas canvas; // Grafica nella finestra
    
    private String title;
    private int width, height;
    
    /**
     * Costruttore della finestra.
     * @param title
     * @param width
     * @param height 
     */
    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        
        createDisplay();
    }
    
    /**
     * Chiamato direttamente dal costruttore, il metodo serve a creare un frame 
     * non resizable ed applicargli un canvas non focussable.
     */
    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        
        /**
         * Questa operazione ci permette di essere sicuri che il frame venga
         * chiuso quando si preme la X della finestra e che quindi non continui 
         * ad essere eseguito in background il programma a seguito della sua 
         * chiusura
         * !IMPORTANTE
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        
        /**
         * Questa operazione permette di far comparire la frame al centro dello 
         * schermo
         */
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
        
        canvas = new Canvas();
        /**
         * L'oggetto viene dimensionato attraverso un oggetto Dimension il cui 
         * costruttore prende come argomento larghezza e altezza.
         * Vengono attuati tre dimensionamenti in modo da essere sicuri che il 
         * canvas rimanga sempre della stessa grandezza
         */
        Dimension d = new Dimension(width, height);
        canvas.setPreferredSize(d);
        canvas.setMaximumSize(d);
        canvas.setMinimumSize(d);
        /**
         * In modo tale che non venga selezionato il canvas ma il Frame: al 
         * frame è infatti applicato un listener ma, se questo non è 
         * selezionato, il listener non funziona.
         */
        canvas.setFocusable(false);
        
        frame.add(canvas);
        frame.pack(); // In modo da essere sicuri da vedere tutto il canvas
    }
    
    /**
     * 
     * @return canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * 
     * @return frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
