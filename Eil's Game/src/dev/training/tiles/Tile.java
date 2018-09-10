package dev.training.tiles;

import dev.training.Utils;
import dev.training.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    // Costanti
    public static final int TILEWIDTH = 140, TILEHEIGHT = 140;
    public static final int SELECT = 20;
    public static final int ATTACK = 21;
    public static final int CHARAPTER_ID_SIZE = 4;
    public static final int GROUNT_ID_SIZE = 2;
    public static final int ARROW_ID_SIZE = 3;
    
    
    //codifica charapter: cifra0: team, cifra1: tipologia, cifra2: vita, cifra3:status di attacco
    private static final String SOLDIER_ID = "1";
    private static final String ARCHER_ID = "2";
    private static final String KING_ID = "3";
  
    private static final String FULL_HEALTH = "0";
    private static final String HALF_HEALTH = "1";
    private static final String LOW_HEALTH = "2";
    
    private static final String ON_ATTACK = "1";
    private static final String NOT_ON_ATTACK = "0";
    
    private static final int GRASS_TILE = 10;
    private static final int DIRT_TILE = 11;
    private static final int STONE_TILE = 12;
    
    
    
       
    /**
     * Contiene ogni singolo tipo di Tile
     */
    public static Tile[] tiles = new Tile[2400];
    private static Tile alliesTile = new AlliesTile(0);
    private static Tile opponentsTile = new OpponentsTile(1);
    
    private static Tile grassTile = new GrassTile(GRASS_TILE);
    private static Tile dirtTile = new DirtTile(DIRT_TILE);
    private static Tile stoneTile = new StoneTile(STONE_TILE);
    
    private static Tile selectedTile = new SelectedTile(SELECT);
    private static Tile attackTile = new AttackTile(ATTACK);
    
    private static Tile soldierAttack100 = new Tile(Assets.soldier100_attack, Utils.parseInt(SOLDIER_ID + FULL_HEALTH + ON_ATTACK));
    private static Tile soldierAttack50 = new Tile(Assets.soldier50_attack, Utils.parseInt(SOLDIER_ID + HALF_HEALTH + ON_ATTACK));
    private static Tile soldierAttack10 = new Tile(Assets.soldier10_attack, Utils.parseInt(SOLDIER_ID + LOW_HEALTH + ON_ATTACK));
    
    private static Tile archerAttack100 = new Tile(Assets.archer100_attack, Utils.parseInt(ARCHER_ID + FULL_HEALTH + ON_ATTACK));
    private static Tile archerAttack50 = new Tile(Assets.archer50_attack, Utils.parseInt(ARCHER_ID + HALF_HEALTH + ON_ATTACK));
    private static Tile archerAttack10 = new Tile(Assets.archer10_attack, Utils.parseInt(ARCHER_ID + LOW_HEALTH + ON_ATTACK));
    
    private static Tile soldierRest100 = new Tile(Assets.soldier100, Utils.parseInt(SOLDIER_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile soldierRest50 = new Tile(Assets.soldier50, Utils.parseInt(SOLDIER_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile soldierRest10 = new Tile(Assets.soldier10, Utils.parseInt(SOLDIER_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    private static Tile archerRest100 = new Tile(Assets.archer100, Utils.parseInt(ARCHER_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile archerRest50 = new Tile(Assets.archer50, Utils.parseInt(ARCHER_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile archerRest10 = new Tile(Assets.archer10, Utils.parseInt(ARCHER_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    private static Tile king100 = new Tile(Assets.king100, Utils.parseInt(KING_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile king50 = new Tile(Assets.king50, Utils.parseInt(KING_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile kingk10 = new Tile(Assets.king10, Utils.parseInt(KING_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    public static char TEAM;
    
    public static void render(Graphics g, int x, int y, String data) // owner serve per sapere chi sono i cattivi
    {
        int team = Utils.parseInt(data.charAt(0)+"");
        Tile tile = null;
        if (data.length() == CHARAPTER_ID_SIZE){ // è un charapter
            if (team == Utils.parseInt(TEAM+""))
            {
                tiles[0].render(g, x, y); // stampo la tile degli alleati blu
                tile = tiles[Utils.parseInt(data.substring(1))];
            }
            else 
            {
                tiles[1].render(g, x, y); // stampo la tile degli avversari rossa
                tile = tiles[Utils.parseInt(data.substring(1))];
            }  
        }
        else // è solo un pezzo del mondo
        {
            int IdentificationNumber = Utils.parseInt(data);
            tile = tiles[IdentificationNumber];
        }
        if(tile == null) tile = stoneTile;
        tile.render(g, x, y);
    }
    
    public static boolean isSolid(String data) {
        int dataInt = Utils.parseInt(data);
        return !(dataInt == GRASS_TILE || dataInt == DIRT_TILE || dataInt == SELECT || dataInt == ATTACK);
    }
    
   
        
        
    
    
    protected BufferedImage texture;
    protected final int id;
    
    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;
        
        tiles[id] = this;
    }
    
    public void update() {
        
    }
    
    private void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    
    public int getId() {
        return id;
    }
    
   
    
            
}
