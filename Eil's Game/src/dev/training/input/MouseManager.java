package dev.training.input;

import dev.training.Handeler;
import dev.training.tiles.Tile;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener{
    
    // Grandezza in pixel del frame
    int width, height;
    
    // Valori degli input
    public boolean up, down, left, right, isPressed;

    // margini della cornice in cui è catturato il movimento
    private int xLeft, xRight, yTop, yBottom;

     // Riga e colonna del Tile all'interno della matrice dei Tile "World"
    private int xTile, yTile;
    
    // Handeler
    private Handeler handeler;

     // Y_OFFSET è il numero di pixel che vengono "mangiati"
    private final int Y_OFFSET = 27;

    public MouseManager(int width, int height) { 
        
        this.width = width;
        this.height = height;
        
        // Inizializzazione dei valori di input
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.isPressed = false;
        
        // Calcolo del range cornice
        xLeft = width / 10;
        xRight = width - xLeft;
        yTop = (height + Y_OFFSET) / 8;
        yBottom = height + Y_OFFSET - yTop;
        System.out.println(xLeft + " " + xRight + " " + yTop + " " + yBottom);
        System.out.println(width + " " + height);
    }
    
    /**
     * Funzione super incasinata
     * @param x la posizione (sinistra/destra) di inizio del frame nello schermo
     * @param y la posizione (alto/basso) di inizio del frame nello schermo
     */
    public void update(int x, int y) {
        
        // Posizione del mouse all'interno dello schermo
        int xAbsolute = MouseInfo.getPointerInfo().getLocation().x;
        int yAbsolute = MouseInfo.getPointerInfo().getLocation().y;
        
        // Calcoliam la posizione relativa del mouse all'interno del frame
        int xRelative = (xAbsolute - x);
        int yRelative = (yAbsolute - y);
        
        // Se il mouse è fuori dal frame non c'è input
        if(xRelative < 0 || yRelative < 0 || xRelative > width || yRelative > height)
            up=down=left=right=false;
        else { left = xRelative < xLeft; right = xRelative > xRight;
            up = yRelative < yTop; down = yRelative > yBottom; }
        
        /**
         * Calcolo della posizione dell'ascissa Tile attraverso il rapprto tra 
         * l'ascissa assoluta e la grandezza di un Tile (stesso procedimento per 
         * l'ordinata)
         */
        yTile = (int) ((handeler.getGameCamera().getyOffset() + yRelative)/Tile.TILEHEIGHT);
        xTile = (int) ((handeler.getGameCamera().getxOffset() + xRelative)/Tile.TILEWIDTH);
        
    }

    /**
     * Funzione che ritorna l'ascissa della tile considerando il mondo come una 
     * tabella di tiles
     * @return xTile
     */
    public int getxTile() {
        return xTile;
    }
    
    public void setHandeler(Handeler handeler) {
        this.handeler = handeler;
    }

    /**
     * Funzione che ritorna l'ordinata della tile considerando il mondo come una 
     * tabella di tiles
     * @return yTile
     */
    public int getyTile() { return yTile; }

    @Override
    public void mouseClicked(MouseEvent me) { }

    @Override
    public void mousePressed(MouseEvent me) { this.isPressed = true; }

    @Override
    public void mouseReleased(MouseEvent me) { this.isPressed = false; }

    @Override
    public void mouseEntered(MouseEvent me) { }

    @Override
    public void mouseExited(MouseEvent me) { }
    
}
