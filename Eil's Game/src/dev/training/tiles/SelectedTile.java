/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.training.tiles;

import dev.training.gfx.Assets;

/**
 *
 * @author Alessandro
 */
public class SelectedTile extends Tile {
    public SelectedTile(int id) {
        super(Assets.selected, id);
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
}
