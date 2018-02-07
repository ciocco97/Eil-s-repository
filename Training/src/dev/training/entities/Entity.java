package dev.training.entities;

import java.awt.Graphics;

public abstract class Entity {
    
    /**
     * Solamente le classi che estendono questa classe hanno accesso alle 
     * variabili protected
     */
    protected float x, y;
    
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void update();
    
    public abstract void render(Graphics g);
    
}
