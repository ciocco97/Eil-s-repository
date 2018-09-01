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
    
    // Bg sta per backGround
    public static BufferedImage dirt, grass, stone, selected, attack, alliesBg, opponentsBg;
    public static BufferedImage chapter_soldier100, chapter_soldier50, chapter_soldier10, 
            chapter_archer100, chapter_archer50, chapter_archer10,
            chapter_king100, chapter_king50, chapter_king10;
    
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sprite.png"));
        selected = sheet.crop(160, 0, 20, 20);
        attack = sheet.crop(100, 0, 20, 20);
        alliesBg = sheet.crop(200, 0, 20, 20);
        opponentsBg = sheet.crop(180, 0, 20, 20);
        
        grass = sheet.crop(40, 0, 20, 20);
        dirt = sheet.crop(60, 0, 20, 20);
        stone = sheet.crop(80, 0, 20, 20);
        
        chapter_soldier100 = sheet.crop(0, 29, 350, 350);
        chapter_soldier50 = sheet.crop(351, 29, 350, 350);
        chapter_soldier10 = sheet.crop(702, 29, 350, 350);
        chapter_archer100 = sheet.crop(0, 380, 350, 350);
        chapter_archer50 = sheet.crop(351, 380, 350, 350);
        chapter_archer10 = sheet.crop(702, 380, 350, 350);
        chapter_king100 = chapter_king50 = chapter_king10 = sheet.crop(0, 731, 350, 350);
        
    }
}
