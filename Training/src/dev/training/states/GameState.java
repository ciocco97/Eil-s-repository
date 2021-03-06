package dev.training.states;

import dev.training.Game;
import dev.training.entities.creature.Player;
import dev.training.worlds.World;
import java.awt.Graphics;

public class GameState extends State{
    
    private Player player;
    private World world;
    
    public GameState(Game game) {
        super(game);
        player = new Player(game, 100, 100);
        world = new World(game, "res\\worlds\\world");
    }

    @Override
    public void update() {
        world.update();
        player.update();
        game.getGameCamera().update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }
    
}
