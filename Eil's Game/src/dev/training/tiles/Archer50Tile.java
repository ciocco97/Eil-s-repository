package dev.training.tiles;

import dev.training.gfx.Assets;
import java.awt.image.BufferedImage;

public class Archer50Tile extends Tile{

    public Archer50Tile(int id) {
        super(Assets.chapter_archer50, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
