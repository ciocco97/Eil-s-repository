
package dev.training.gfx;

import dev.training.Game;
import dev.training.entities.Entity;

/**
 * Classe che, concettualmente, rappresenta la fisìnestra di gioco renderizzata.
 */
public class GameCamera {
    
    private Game game;
    private float xOffset, yOffset;
    
    /**
     * Costruttore
     * @param game
     * @param xOffset
     * @param yOffset 
     */
    public GameCamera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    /**
     * La finestra di gioco viene centrata su un'entità
     * @param e Entità su ciu si vuole centrare la camera
     */
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - game.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - game.getHeight() / 2 + e.getHeight() / 2;
    }
    
    /**
     * Metodo per muovere la camera
     * @param xAmt
     * @param yAmt 
     */
    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
