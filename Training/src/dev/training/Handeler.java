package dev.training;

import dev.training.gfx.GameCamera;
import dev.training.input.KeyManager;
import dev.training.worlds.World;

public class Handeler {
    
    private Game game;
    private World world;
    
    public Handeler(Game game) {
        this.game = game;
    }
    
    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    
    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public Game getGame() {
        return game;
    }
    
    public int getWidth() {
        return game.getWidth();
    }
    
    public int getHeight() {
        return game.getHeight();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
    
}
