package dev.training.input;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

public class MouseManager {
    public boolean up, down, left, right;
    private int width, height, xLeft, xRight, yTop, yBottom;

    public MouseManager(int width, int height) {
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.width = width;
        this.height = height + 27;
        
        // Calcolo del range cornice
        xLeft = width / 10;
        xRight = width - xLeft;
        yTop = height / 8;
        yBottom = height - yTop;
    }
    
    public void update(int x, int y){
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
        System.out.println("yrelative: " + yRelative + " yFrame: " + height);
    }
}
