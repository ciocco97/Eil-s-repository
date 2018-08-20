package dev.training.input;

import dev.training.gfx.GameCamera;
import dev.training.tiles.Tile;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener{
    public boolean up, down, left, right, isPressed;
    private int width, height, xLeft, xRight, yTop, yBottom, xTile, yTile;
    private GameCamera camera;
    
    final int Y_OFFSET = 27;

    public MouseManager(int width, int height) {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.isPressed = false;
        this.width = width;
        this.height = height + Y_OFFSET;
        
        // Calcolo del range cornice
        xLeft = width / 10;
        xRight = width - xLeft;
        yTop = height / 8;
        yBottom = height - yTop;
    }
    public void initMouseManager(int x, int y)
    {
        
    }
    
    public void update(int x, int y) {
        int xAbsolute = MouseInfo.getPointerInfo().getLocation().x;
        int yAbsolute = MouseInfo.getPointerInfo().getLocation().y;
        // Calcoliam la posizione relativa del mouse rispetto al frame
        int xRelative = (xAbsolute - x);
        int yRelative = (yAbsolute - y);
        if(xRelative<0 || yRelative<0 || xRelative>width || yRelative>height)
            //fuori dal frame
            up=down=left=right=false;
        else {
            left = xRelative < xLeft;
            right = xRelative > xRight;
            up = yRelative < yTop;
            down = yRelative > yBottom;
            
        }
        yTile = (int) ((GameCamera.yOffset + yRelative)/Tile.TILEHEIGHT);
        xTile = (int) ((GameCamera.xOffset + xRelative)/Tile.TILEWIDTH);
        
    }

    public int getxTile() {
        return xTile;
    }

    public int getyTile() {
        return yTile;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.isPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
}
