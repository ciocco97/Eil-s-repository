package dev.training.entities;

import dev.training.Game;
import java.awt.Graphics;

/**
 * Classe per gli elementi animati del gioco
 */
public abstract class Entity {
    
    /**
     * Solamente le classi che estendono questa classe hanno accesso alle 
     * variabili protected
     */
    protected Game game;
    protected float x, y;
    protected int width, height;
    
    /**
     * Costruttore.
     * @param game
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Entity(Game game, float x, float y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
