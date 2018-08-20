package dev.training.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    /**
     * Contiene ogni singolo tipo di Tile
     */
    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile selectedTile = new SelectedTile(3);
    public static Tile playerTile = new PlayerTile(4);
    
    public static final int TILEWIDTH = 140, TILEHEIGHT = 140;
    
    protected BufferedImage texture;
    protected final int id;
    
    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;
        
        tiles[id] = this;
    }
    
    public void update() {
        
    }
    
    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    
    public boolean isSolid() {
        return false;
    }
    
    public int getId() {
        return id;
    }
    
}
