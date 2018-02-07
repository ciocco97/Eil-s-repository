package dev.training.gfx;

import java.awt.image.BufferedImage;

/**
 * La classe Assets serve per importare poche immagini all'interno del progetto 
 * per renderlo molto meno pesante (tutte le texture possono essere aggiunte 
 * attraverso un'immagine dalle quali vengono estrapolate. Se non venisse 
 * utilizzata, ad ogni ciclo verrebbe caricata una nuova immagine appesantendo 
 * in modo massiccio il programma.
 */
public class Assets {
    
    private static final int width = 32, height = 32;
    
    public static BufferedImage player, dirt, grass, stone, tree, full, soldierf, soldierr;
    
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Bambino.png"));
        
        player = sheet.crop(100, 130, width, height);
        dirt = sheet.crop(100 + width, 130, width, height);
        grass = sheet.crop(149, 185, width, height);
        stone = sheet.crop(100 + width * 3, 130, width, height);
        tree = sheet.crop(100, 130 + width, width, height);
        full = sheet.crop(0, 0, 553, 740);
    }
}
