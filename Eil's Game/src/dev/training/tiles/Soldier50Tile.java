package dev.training.tiles;

import dev.training.gfx.Assets;
import java.awt.image.BufferedImage;

public class Soldier50Tile extends Tile{

    public Soldier50Tile(int id) {
        super(Assets.chapter_soldier50, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
