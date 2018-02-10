
package dev.training.gfx;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.entities.Entity;

/**
 * Classe che, concettualmente, rappresenta la fisìnestra di gioco renderizzata.
 */
public class GameCamera {
    
    private final int CAMERA_SPEED = 20; // Non tutorial
    
    private Handeler handeler;
    private float xOffset, yOffset;
    private int xMove, yMove; // Non tutorial
    
    /**
     * Costruttore
     * @param game
     * @param xOffset
     * @param yOffset 
     */
    public GameCamera(Handeler handeler, float xOffset, float yOffset) {
        this.handeler = handeler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    /**
     * La finestra di gioco viene centrata su un'entità
     * @param e Entità su ciu si vuole centrare la camera
     */
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - handeler.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handeler.getHeight() / 2 + e.getHeight() / 2;
    }
    
    /**
     * Metodo che muove la Camera.
     */
    public void update() { // Non tutorial
        getInput();
        move(xMove, yMove);
    }
    
    /**
     * Metodo che controlla l'input da tastiera per poi muovere la camera.
     */
    private void getInput() { // Non tutorial
        xMove = 0;
        yMove = 0;
        
        if(handeler.getKeyManager().up)
            yMove = -CAMERA_SPEED;
        if(handeler.getKeyManager().down)
            yMove = CAMERA_SPEED;
        if(handeler.getKeyManager().left)
            xMove = -CAMERA_SPEED;
        if(handeler.getKeyManager().right)
            xMove = CAMERA_SPEED;
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
