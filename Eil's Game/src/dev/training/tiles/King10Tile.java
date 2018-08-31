package dev.training.tiles;

import dev.training.gfx.Assets;

public class King10Tile extends Tile{

    public King10Tile(int id) {
        super(Assets.chapter_king10, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
