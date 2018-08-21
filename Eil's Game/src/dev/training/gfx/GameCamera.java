
package dev.training.gfx;

import dev.training.Handeler;

public class GameCamera {
    
    private final int CAMERA_SPEED = 20;
    
    Handeler handeler;
    public float xOffset, yOffset;
    private int xMove, yMove;
    
    public GameCamera(Handeler handeler, float xOffset, float yOffset) {
        this.handeler = handeler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public void update() {
        getInput();
        move(xMove, yMove);
    }
    
    private void getInput() {
        xMove = 0;
        yMove = 0;
        
        if(handeler.getMouseManager().up)
            yMove = -CAMERA_SPEED;
        if(handeler.getMouseManager().down)
            yMove = CAMERA_SPEED;
        if(handeler.getMouseManager().left)
            xMove = -CAMERA_SPEED;
        if(handeler.getMouseManager().right)
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
