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
    public static BufferedImage soldier100, soldier50, soldier10, 
                                archer100, archer50, archer10,
                                king100, king50, king10,
                                soldier100_attack, soldier50_attack, soldier10_attack,
                                archer100_attack, archer50_attack, archer10_attack,
                                up, upRight, right, downRight, down, downLeft, left, upLeft;
    
                                    
    
    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/Sprite.png"));
        selected = sheet.crop(160, 0, 20, 20);
        attack = sheet.crop(100, 0, 20, 20);
        alliesBg = sheet.crop(200, 0, 20, 20);
        opponentsBg = sheet.crop(180, 0, 20, 20);
        
        grass = sheet.crop(40, 0, 20, 20);
        dirt = sheet.crop(60, 0, 20, 20);
        stone = sheet.crop(80, 0, 20, 20);
        
        soldier100 = sheet.crop(0, 29, 350, 350);
        soldier50 = sheet.crop(351, 29, 350, 350);
        soldier10 = sheet.crop(702, 29, 350, 350);
        archer100 = sheet.crop(0, 380, 350, 350);
        archer50 = sheet.crop(351, 380, 350, 350);
        archer10 = sheet.crop(702, 380, 350, 350);
        king100 = king50 = king10 = sheet.crop(0, 731, 350, 350);
        
        soldier100_attack = sheet.crop(1053,29,350,350);
        soldier50_attack = sheet.crop(1404,29,350,350);
        soldier10_attack = sheet.crop(1755, 29, 350, 350);
        
        archer100_attack = sheet.crop(1053,380,350,350);
        archer50_attack = sheet.crop(1404,380,350,350);
        archer10_attack = sheet.crop(1755,380,350,350);
        
        
        up = sheet.crop(0, 1082, 350, 350);
        upRight = sheet.crop(351, 1082, 350, 350);
        right = sheet.crop(702, 1082, 350, 350);
        downRight = sheet.crop(1053, 1082, 350, 350);
        down = sheet.crop(1040, 1082, 350, 350);
        downLeft = sheet.crop(1755, 1082, 350, 350);
        left = sheet.crop(0, 1433, 350, 350);
        upLeft = sheet.crop(351, 1433, 350, 350);
        
        
        
        
        
    }
}
