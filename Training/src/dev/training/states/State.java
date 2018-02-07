package dev.training.states;

import java.awt.Graphics;

/**
 * Questa classe rappresenta ciò che hanno in comune gli state di manu, di 
 * impostazioni e di gioco
 */
public abstract class State {
    
    public abstract void update();
    public abstract void render(Graphics g);
    
}
