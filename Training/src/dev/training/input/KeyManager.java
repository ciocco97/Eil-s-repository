package dev.training.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe che gestisce l'input da tastiera
 */
public class KeyManager implements KeyListener{
    
    private boolean[] keys;
    public boolean up, down, left, right;
    
    /**
     * Costruttore.
     */
    public KeyManager() {
        keys = new boolean[256];
    }
    
    /**
     * Metodo che aggiorna la variabili up, down, left e right tramite l'array 
     * keys (mmodificato dal metodo keyPressed()).
     */
    public void update() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
}
