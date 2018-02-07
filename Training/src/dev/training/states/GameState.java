package dev.training.states;

import dev.training.gfx.Assets;
import java.awt.Graphics;

public class GameState extends State{
    
    public GameState() {
        
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.full, 0, 0, null);
    }
    
}
