package dev.training.tiles;

import dev.training.gfx.Assets;

public class King100Tile extends Tile{

    public King100Tile(int id) {
        super(Assets.chapter_king100, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
