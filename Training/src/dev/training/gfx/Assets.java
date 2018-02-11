package dev.training.gfx;

import java.awt.image.BufferedImage;

/**
 * La classe Assets serve per importare poche immagini all'interno del progetto 
 * per renderlo molto meno pesante (tutte le texture possono essere aggiunte 
 * attraverso un'immagine dalle quali vengono estrapolate). Se non venisse 
 * utilizzata, ad ogni ciclo verrebbe caricata una nuova immagine appesantendo 
 * in modo massiccio il programma.
 */
public class Assets {
    
    private static final int width = 150, height = 150;
    
    public static BufferedImage player, dirt, grass, stone, tree, rock;
    public static BufferedImage pu, pd, pl, pr;
    
    /**
     * Metodo che carica tutte le immagini da utilizzare nel gioco
     */
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sprite.png"));
        
        grass = sheet.crop(600, 0, 20, 20);
        dirt = sheet.crop(620, 0, 20, 20);
        stone = sheet.crop(640, 0, 20, 20);
        rock = sheet.crop(width * 3, 0, width, height);
        player = sheet.crop(0, height + 10, 450, 700);
        
        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Pitoto.png"));
        dirt = grass;
        player = sheet.crop(3577, 151, 960, 660);
        pu = sheet.crop(2500, 155, 960, 660);
        pd = sheet.crop(8, 179, 960, 660);
        pl = sheet.crop(1698, 74, 660, 960);
        pr = sheet.crop(997, 88, 660, 960);
    }
}
