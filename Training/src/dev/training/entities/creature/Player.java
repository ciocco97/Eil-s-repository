package dev.training.entities.creature;

import dev.training.Game;
import dev.training.gfx.Assets;
import java.awt.Graphics;

public class Player extends Creature{
    
    /**
     * La variabile game ci consente l'accesso al KeyManager e a tutto ciò che 
     * riguarda l'input
     */
    private Game game;
    
    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;
    }

    /**
     * Modificare le variabili contenute nell'istanza del Player
     */
    @Override
    public void update() {
        int cost = 20;
        if(game.getKeyManager().up)
            y -= cost;
        if(game.getKeyManager().down)
            y += cost;
        if(game.getKeyManager().left)
            x -= cost;
        if(game.getKeyManager().right)
            x += cost;
    }

    /**
     * Si "disegna" sullo schermo
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.full, (int) x, (int) y, null);
    }
    
}
