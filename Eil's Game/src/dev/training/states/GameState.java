package dev.training.states;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.worlds.World;
import java.awt.Graphics;

public class GameState extends State{
    private World world;
    
    public GameState(Handeler handeler) {
        super(handeler);
        world = new World(handeler, "res\\worlds\\world");
        handeler.setWorld(world);
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
    }
    
}
