
package dev.training.gfx;

import dev.training.Game;
import dev.training.entities.Entity;

public class GameCamera {
    
    private final int CAMERA_SPEED = 20;
    
    private Game game;
    private float xOffset, yOffset;
    private int xMove, yMove;
    
    public GameCamera(Game game, float xOffset, float yOffset) {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - game.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - game.getHeight() / 2 + e.getHeight() / 2;
    }
    
    public void update() {
        getInput();
        move(xMove, yMove);
    }
    
    private void getInput() {
        xMove = 0;
        yMove = 0;
        
        if(game.getKeyManager().up)
            yMove = -CAMERA_SPEED;
        if(game.getKeyManager().down)
            yMove = CAMERA_SPEED;
        if(game.getKeyManager().left)
            xMove = -CAMERA_SPEED;
        if(game.getKeyManager().right)
            xMove = CAMERA_SPEED;
    }
    
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
