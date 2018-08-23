package dev.training.tiles;

import dev.training.gfx.Assets;
import java.awt.image.BufferedImage;

public class Soldier10Tile extends Tile {

    public Soldier10Tile(int id) {
        super(Assets.chapter_soldier10, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
