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
    
    public static BufferedImage dirt, grass, stone, selected, charapter;
    public static BufferedImage chapter_soldier100, chapter_soldier50, chapter_soldier10, 
            chapter_archer100, chapter_archer50, chapter_archer10;
    
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sprite.png"));
//        selected = sheet.crop(580, 0, 20, 20);
        selected = sheet.crop(697, 478, 348, 348);
        grass = sheet.crop(600, 0, 20, 20);
//        grass = sheet.crop(1047, 825, 348, 348);
        dirt = sheet.crop(620, 0, 20, 20);
//        dirt = sheet.crop(1395, 825, 348, 348);
        stone = sheet.crop(640, 0, 20, 20);
//        stone = sheet.crop(697, 825, 348, 348);
//        rock = sheet.crop(width * 3, 0, width, height);
        
//        player = sheet.crop(0, height + 10, 450, 700);
//        charapter = sheet.crop(680, 0, 20, 20);
        chapter_soldier100 = chapter_soldier50 = chapter_soldier10 = sheet.crop(1491, 69, 348, 348);
        chapter_archer100 = chapter_archer50 = chapter_archer10 = sheet.crop(351, 825, 348, 348);
    }
}
