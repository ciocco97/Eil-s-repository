package dev.training.gfx;

import java.awt.image.BufferedImage;

/**
 * Classe utilizzata per rendere più veloce l'inport delle texture
 */
public class SpriteSheet {
    private BufferedImage sheet;
    
    /**
     * Costruttore
     * @param sheet Immagine
     */
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
    
    /**
     * Metodo che serve a sezionare un'immagine ed a ritornarne solamente una 
     * parte.
     * @param x
     * @param y
     * @param width
     * @param height
     * @return subImage dell'immagine salvata in sheet
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
