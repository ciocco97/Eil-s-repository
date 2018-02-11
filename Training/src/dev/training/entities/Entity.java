package dev.training.entities;

import dev.training.Game;
import dev.training.Handeler;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Classe per gli elementi animati del gioco
 */
public abstract class Entity {
    
    /**
     * Solamente le classi che estendono questa classe hanno accesso alle 
     * variabili protected
     */
    protected Handeler handeler;
    protected float x, y;
    protected int width, height;
    protected Rectangle bounds;;
    
    /**
     * Costruttore.
     * @param handeler
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Entity(Handeler handeler, float x, float y, int width, int height) {
        this.handeler = handeler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        bounds = new Rectangle(0, 0, width, height);
    }
    
    public abstract void update();
    
    public abstract void render(Graphics g);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
