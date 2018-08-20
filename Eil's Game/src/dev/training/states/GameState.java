package dev.training.states;

import dev.training.Game;
import dev.training.worlds.World;
import java.awt.Graphics;

public class GameState extends State{
    private World world;
    
    public GameState(Game game) {
        super(game);
        world = new World(game, "res\\worlds\\world");
    }

    @Override
    public void update() {
        world.update();
        game.getGameCamera().update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }
    
}
