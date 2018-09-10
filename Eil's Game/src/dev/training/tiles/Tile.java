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
    public static final int LOWER_BOUND_1 = 30;
    public static final int UPPER_BOUND_1 = 100;
    public static final int LOWER_BOUND_2 = 101;
    public static final int UPPER_BOUND_2 = 200;
    
    
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
    public static Tile[] tiles = new Tile[9999];
    private static Tile alliesTile = new AlliesTile(0);
    private static Tile opponentsTile = new OpponentsTile(1);
    
    private static Tile grassTile = new GrassTile(GRASS_TILE);
    private static Tile dirtTile = new DirtTile(DIRT_TILE);
    private static Tile rockTile = new StoneTile(STONE_TILE);
    
    private static Tile selectedTile = new SelectedTile(SELECT);
    private static Tile attackTile = new AttackTile(ATTACK);
    
    private static Tile soldierAttack100 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + FULL_HEALTH + ON_ATTACK));
    private static Tile soldierAttack50 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + HALF_HEALTH + ON_ATTACK));
    private static Tile soldierAttack10 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + LOW_HEALTH + ON_ATTACK));
    
    private static Tile archerAttack100 = new Tile(Assets.chapter_king100, Utils.parseInt(ARCHER_ID + FULL_HEALTH + ON_ATTACK));
    private static Tile archerAttack50 = new Tile(Assets.chapter_soldier100, Utils.parseInt(ARCHER_ID + HALF_HEALTH + ON_ATTACK));
    private static Tile archerAttack10 = new Tile(Assets.chapter_soldier100, Utils.parseInt(ARCHER_ID + LOW_HEALTH + ON_ATTACK));
    
    private static Tile soldierRest100 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile soldierRest50 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile soldierRest10 = new Tile(Assets.chapter_soldier100, Utils.parseInt(SOLDIER_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    private static Tile archerRest100 = new Tile(Assets.chapter_king100, Utils.parseInt(ARCHER_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile archerRest50 = new Tile(Assets.chapter_soldier100, Utils.parseInt(ARCHER_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile archerRest10 = new Tile(Assets.chapter_soldier100, Utils.parseInt(ARCHER_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    private static Tile kingAttack100 = new Tile(Assets.chapter_soldier100, Utils.parseInt(KING_ID + FULL_HEALTH + NOT_ON_ATTACK));
    private static Tile kingAttack50 = new Tile(Assets.chapter_soldier100, Utils.parseInt(KING_ID + HALF_HEALTH + NOT_ON_ATTACK));
    private static Tile kingAttack10 = new Tile(Assets.chapter_soldier100, Utils.parseInt(KING_ID + LOW_HEALTH + NOT_ON_ATTACK));
    
    public static char TEAM;
    public static void render(Graphics g, int x, int y, String data) // owner serve per sapere chi sono i cattivi
    {
        int team = Utils.parseInt(data.charAt(0)+"");
        System.out.println("team: "+team);
        System.out.println("invece il nosto è:" + TEAM);
        Tile tile = null;
        if (data.length()==4){ // è un charapter
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
        tile.render(g, x, y);
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
    
    public boolean isSolid() {
        return !(id == GRASS_TILE || id == DIRT_TILE || id==SELECT || id==ATTACK);
                    
    }
    
    public int getId() {
        return id;
    }
    
   
    
            
}
