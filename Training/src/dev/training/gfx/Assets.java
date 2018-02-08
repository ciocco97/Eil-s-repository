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
    
    private static final int width = 150, height = 150;
    
    public static BufferedImage player, dirt, grass, stone, tree, rock, vita, cammino;
    
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sprite.png"));
        SpriteSheet sheet1 = new SpriteSheet(ImageLoader.loadImage("/textures/Menu.png"));
        
        grass = sheet.crop(0, 0, width, height);
        dirt = sheet.crop(width, 0, width, height);
        stone = sheet.crop(width * 2, 0, width, height);
        rock = sheet.crop(width * 3, 0, width, height);
        player = sheet.crop(11, 174, 439, 329);
        
        vita = sheet1.crop(1030, 13, 730, 680);
        player = sheet1.crop(650, 225, 250, 120);
        cammino = sheet1.crop(450, 220, 190, 140);
    }
}
