package dev.training.states;

import dev.training.Game;
import dev.training.entities.creature.Player;
import dev.training.tiles.Tile;
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
        Tile.tiles[0].render(g, 0, 0);
        Tile.tiles[1].render(g, Tile.TILEWIDTH, 0);
        Tile.tiles[2].render(g, 0, Tile.TILEHEIGHT);
        player.render(g);
    }
    
}
