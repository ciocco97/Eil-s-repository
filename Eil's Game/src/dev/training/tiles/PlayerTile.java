
package dev.training.tiles;

import dev.training.gfx.Assets;

public class PlayerTile extends Tile{
    
    public PlayerTile(int id) {
        super(Assets.charapter, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
}
