package dev.training.states;

import dev.training.Game;
import dev.training.entities.creature.Player;
import dev.training.tiles.Tile;
import dev.training.worlds.World;
import java.awt.Graphics;
import java.util.LinkedList;

public class GameState extends State{
    
    private LinkedList <Player> ps;
    
    public GameState(Game game) {
        super(game);
        ps = new LinkedList<>();
        for(int i = 0; i < 20; i += 2) {
            for(int j = 0; j < 4; j += 2) {
                ps.add(new Player(game, i * 60, j * 60));
            }
        }
    }

    @Override
    public void update() {
        for(Player p: ps)
            p.update();
    }

    @Override
    public void render(Graphics g) {
        for(Player p: ps)
            p.render(g);
    }
    
}
