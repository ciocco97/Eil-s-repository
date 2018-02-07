package dev.training.states;

import dev.training.Game;
import dev.training.entities.creature.Player;
import java.awt.Graphics;

public class GameState extends State{
    
    private Player player;
    
    public GameState(Game game) {
        super(game);
        player = new Player(game, 100, 100);
    }

    @Override
    public void update() {
        player.update();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
    }
    
}
