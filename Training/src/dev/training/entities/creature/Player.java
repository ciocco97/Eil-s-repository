package dev.training.entities.creature;

import dev.training.gfx.Assets;
import java.awt.Graphics;

public class Player extends Creature{
    
    public Player(float x, float y) {
        super(x, y);
    }

    /**
     * Modificare le variabili contenute nell'istanza del Player
     */
    @Override
    public void update() {
        
    }

    /**
     * Si "disegna" sullo schermo
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, (int) x, (int) y, null);
    }
    
}
