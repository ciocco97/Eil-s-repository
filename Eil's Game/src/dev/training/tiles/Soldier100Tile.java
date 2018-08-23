package dev.training.tiles;

import dev.training.gfx.Assets;

public class Soldier100Tile extends Tile {

    public Soldier100Tile(int id) {
        super(Assets.chapter_soldier100, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
