package dev.training.states;

import dev.training.Handeler;
import dev.training.entities.creature.Player;
import dev.training.worlds.World;
import java.awt.Graphics;

/**
 * Classe che contiene e gestisce tutti gli "Oggetti" del gioco.
 */
public class GameState extends State{
    
    private Player player;
    private World world;
    
    /**
     * Costruttore che istanzia tutti gli elementi necessari al gioco.
     * @param handeler 
     */
    public GameState(Handeler handeler) {
        super(handeler);
        world = new World(handeler, "res\\worlds\\world");
        handeler.setWorld(world);
        player = new Player(handeler, 200, 200);
    }

    @Override
    public void update() {
        world.update();
        player.update();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player.render(g);
    }
    
}
