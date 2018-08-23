package dev.training.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    // Costanti
    public static final int TILEWIDTH = 140, TILEHEIGHT = 140, SELECT = 20;
    
    /**
     * Contiene ogni singolo tipo di Tile
     */
    public static Tile[] tiles = new Tile[256];
    private static Tile grassTile = new GrassTile(10);
    private static Tile dirtTile = new DirtTile(11);
    private static Tile rockTile = new RockTile(12);
    private static Tile selectedTile = new SelectedTile(SELECT);
    private static Tile playerTile = new PlayerTile(4);
    private static Tile chapter_soldier100 = new Soldier100Tile(30);
    private static Tile chapter_soldier50 = new Soldier50Tile(31);
    private static Tile chapter_soldier10 = new Soldier10Tile(32);
    private static Tile chapter_archer100 = new Archer100Tile(33);
    private static Tile chapter_archer50 = new Archer50Tile(34);
    private static Tile chapter_archer10 = new Archer10Tile(35);
    
    
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
