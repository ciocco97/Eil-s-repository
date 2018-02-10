package dev.training.entities.creature;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.entities.Entity;

/**
 * Classe astratta che rappresenta tutte le Entity con vita che popolano il 
 * gioco.
 */
public abstract class Creature extends Entity {
    
    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 300,
            DEFAULT_CREATURE_HEIGHT = 405;
    
    protected int health;
    protected float speed;
    protected float xMove, yMove;
    
    /**
     * Costruttore.
     * @param handeler
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public Creature(Handeler handeler, float x, float y, int width, int height) {
        super(handeler, x, y, width, height);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }
    
    /**
     * Metodo volto a modificare il punto di render della creatura: muovere la 
     * creatura rispetto all'origine.
     */
    public void move() {
        x += xMove;
        y += yMove;
    }

    public int getHealth() {
        return health;
    }

    public float getSpeed() {
        return speed;
    }

    public float getxMove() {
        return xMove;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
