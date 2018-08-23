package dev.training.tiles;

import dev.training.gfx.Assets;
import java.awt.image.BufferedImage;

public class Archer10Tile extends Tile {

    public Archer10Tile(int id) {
        super(Assets.chapter_archer10, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
