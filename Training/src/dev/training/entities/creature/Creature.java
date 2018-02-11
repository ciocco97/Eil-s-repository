package dev.training.entities.creature;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.entities.Entity;
import dev.training.tiles.Tile;

/**
 * Classe astratta che rappresenta tutte le Entity con vita che popolano il 
 * gioco.
 */
public abstract class Creature extends Entity {
    
    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 270,
            DEFAULT_CREATURE_HEIGHT = 194;
    
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
        moveX();
        moveY();
    }
    
    public void moveX() {
        /**
         * Vediamo quali angoli di bound dobbiamo prendere per verificare la 
         * collision
         */
        if(xMove > 0) { // Ci si sta muovendo a destra
            
            /**
             * x + xMove è dove stiamo cercando di muoverci; poi aggiungendo 
             * bounds.x troviamo dove inizia il bound per il Player nel "futuro" 
             * ma, dato che ci stiamo muovendo verso destra, dobbiamo verificare 
             * il lato di destra e quindi aggiungiamo bounds.width; infine, dato 
             * che dobbiamo verificare se la collision è con un tile -> 
             * dividiamo per la larghezza del tile
             */
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
                x += xMove;
            
        }else if(xMove < 0) { // Ci si sta muovendo a sinistra
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
                x += xMove;
        }
    }
    
    public void moveY() {
        if(yMove < 0) {
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEWIDTH;
            
            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) && 
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
                y += yMove;
        } else if(yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEWIDTH;
            
            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) && 
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
                y += yMove;
        }
    }
    
    protected boolean collisionWithTile(int x, int y) {
        return handeler.getWorld().getTile(x, y).isSolid();
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
