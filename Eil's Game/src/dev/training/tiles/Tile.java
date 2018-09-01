package dev.training.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    // Costanti
    public static final int TILEWIDTH = 140, TILEHEIGHT = 140;
    public static final int SELECT = 20;
    public static final int ATTACK = 21;
    public static final int LOWER_BOUND_1 = 30;
    public static final int UPPER_BOUND_1 = 100;
    public static final int LOWER_BOUND_2 = 101;
    public static final int UPPER_BOUND_2 = 200;
    
    
    /**
     * Contiene ogni singolo tipo di Tile
     */
    public static Tile[] tiles = new Tile[256];
    
    private static Tile alliesTile = new AlliesTile(0);
    private static Tile opponentsTile = new OpponentsTile(1);
    
    private static Tile grassTile = new GrassTile(10);
    private static Tile dirtTile = new DirtTile(11);
    private static Tile rockTile = new RockTile(12);
    
    private static Tile selectedTile = new SelectedTile(SELECT);
    private static Tile attackTile = new AttackTile(ATTACK);
    
    private static Tile chapter_soldier100 = new Soldier100Tile(30);
    private static Tile chapter_soldier50 = new Soldier50Tile(31);
    private static Tile chapter_soldier10 = new Soldier10Tile(32);
    private static Tile chapter_archer100 = new Archer100Tile(33);
    private static Tile chapter_archer50 = new Archer50Tile(34);
    private static Tile chapter_archer10 = new Archer10Tile(35);
    private static Tile chapter_king100 = new King100Tile(90);
    private static Tile chapter_king50 = new King100Tile(91);
    private static Tile chapter_king10 = new King100Tile(92);
    
    private static Tile chapter_soldierOpponent100 = new Soldier100Tile(101);
    private static Tile chapter_soldierOpponent50 = new Soldier50Tile(102);
    private static Tile chapter_soldierOpponent10 = new Soldier10Tile(103);
    private static Tile chapter_archerOpponent100 = new Archer100Tile(110);
    private static Tile chapter_archerOpponent50 = new Archer50Tile(111);
    private static Tile chapter_archerOpponent10 = new Archer10Tile(112);
    private static Tile chapter_kingOpponent100 = new King100Tile(190);
    private static Tile chapter_kingOpponent50 = new King100Tile(191);
    private static Tile chapter_kingOpponent10 = new King100Tile(192);
    
    
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
