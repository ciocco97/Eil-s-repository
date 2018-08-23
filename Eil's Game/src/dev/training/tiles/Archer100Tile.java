package dev.training.tiles;

import dev.training.gfx.Assets;

public class Archer100Tile extends Tile{

    public Archer100Tile(int id) {
        super(Assets.chapter_archer100, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
