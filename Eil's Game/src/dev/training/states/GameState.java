package dev.training.states;

import dev.conn.client.ClientUDP;
import dev.training.Handeler;
import dev.training.worlds.World;
import java.awt.Graphics;

public class GameState extends State{
    private World world;
    private ClientUDP clientUDP;
    
    public GameState(Handeler handeler) {
        super(handeler);
        clientUDP = new ClientUDP();
        clientUDP.start();
        world = new World(handeler);
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
