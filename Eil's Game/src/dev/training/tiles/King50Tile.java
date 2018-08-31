package dev.training.tiles;

import dev.training.gfx.Assets;

public class King50Tile extends Tile {

    public King50Tile(int id) {
        super(Assets.chapter_king50, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
