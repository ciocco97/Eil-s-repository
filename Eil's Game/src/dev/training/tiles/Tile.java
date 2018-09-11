package dev.training.tiles;

import dev.training.Utils;
import dev.training.gfx.Assets;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
    
    public static char TEAM;
    
    // Costanti
    public static final int TILEWIDTH = 140, TILEHEIGHT = 140;
    public static final int SELECT = 20;
    public static final int ATTACK = 21;
    public static final int CHARAPTER_ID_SIZE = 4;
    public static final int GROUND_ID_SIZE = 2;
    public static final int ARROW_ID_SIZE = 3;
    public static final int ARROW_CHARAPTER_ID_SIZE = CHARAPTER_ID_SIZE + 1;
    
    public static final int TEAM_POSITION = 0;
    public static final int TIPOLOGY_POSITION = 1;
    public static final int HEALTH_POSITION = 2;
    public static final int STATUS_POSITION = 3;
    
    //codifica charapter: cifra0: team, cifra1: tipologia, cifra2: vita, cifra3:status di attacco
    public static final String SOLDIER_ID = "1";
    public static final String ARCHER_ID = "2";
    public static final String KING_ID = "3";
  
    public static final String FULL_HEALTH = "0";
    public static final String HALF_HEALTH = "1";
    public static final String LOW_HEALTH = "2";
    
    public static final String ON_ATTACK = "1";
    public static final String NOT_ON_ATTACK = "0";
    
    public static final int GRASS_TILE = 10;
    public static final int DIRT_TILE = 11;
    public static final int STONE_TILE = 12;
    
    public static final String UP = "1";
    public static final String UP_RIGHT = "2";
    public static final String RIGHT = "3";
    public static final String DOWN_RIGHT = "4";
    public static final String DOWN = "5";
    public static final String DOWN_LEFT = "6";
    public static final String LEFT = "7";
    public static final String UP_LEFT = "8";
    
    public static final int ALLIES_ID = 15;
    private static final int OPPONENTS_ID = 16;
     
    /**
     * Contiene ogni singolo tipo di Tile
     */
    public static Tile[] tiles = new Tile[2400];
    private static Tile alliesTile = new AlliesTile(ALLIES_ID);
    private static Tile opponentsTile = new OpponentsTile(OPPONENTS_ID);
    
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
    
    private static Tile arrowUp = new Tile(Assets.up, Utils.parseInt(UP));
    private static Tile arrowUpRight = new Tile(Assets.upRight, Utils.parseInt(UP_RIGHT));
    private static Tile arrowRight = new Tile(Assets.right, Utils.parseInt(RIGHT));
    private static Tile arrowDownRight = new Tile(Assets.downRight, Utils.parseInt(DOWN_RIGHT));
    private static Tile arrowDown = new Tile(Assets.down, Utils.parseInt(DOWN));
    private static Tile arrowDownLeft = new Tile(Assets.downLeft, Utils.parseInt(DOWN_LEFT));
    private static Tile arrowLeft = new Tile(Assets.left, Utils.parseInt(LEFT));
    private static Tile arrowUpLeft = new Tile(Assets.upLeft, Utils.parseInt(UP_LEFT));
    
    public static void render(Graphics g, int x, int y, String data) // owner serve per sapere chi sono i cattivi
    {
        int team = Utils.parseInt(data.charAt(TEAM_POSITION)+"");
        Tile tile = null;
        switch (data.length()) {
            case CHARAPTER_ID_SIZE:
                // è un charapter
                if (team == Utils.parseInt(TEAM + "")) {
                    tiles[ALLIES_ID].render(g, x, y); // stampo la tile degli alleati blu
                    tile = tiles[Utils.parseInt(data.substring(1))];
                } else {
                    tiles[OPPONENTS_ID].render(g, x, y); // stampo la tile degli avversari rossa
                    tile = tiles[Utils.parseInt(data.substring(1))];
                }
                break;
            // è solo un pezzo del mondo
            case GROUND_ID_SIZE:
                int IdentificationNumber = Utils.parseInt(data);
                tile = tiles[IdentificationNumber];
                break;
            case ARROW_ID_SIZE:
                int direction = Utils.parseInt(data.substring(0, 1));
                int ground = Utils.parseInt(data.substring(1));
                //            System.out.println("direction: " + direction + "ground: " + ground);
                tiles[ground].render(g, x, y);
                tile = tiles[direction];
                break;
            case ARROW_CHARAPTER_ID_SIZE:
                int direction_1 = Utils.parseInt(data.substring(0, 1));
                String charapter = data.substring(1);
                Tile.render(g, x, y, charapter);
                tile = tiles[direction_1];
                break;
            default:
                break;
        }
        if(tile == null) tile = stoneTile;
        tile.render(g, x, y);
    }
    
    public static boolean isSolid(String data) {
        int dataInt = Utils.parseInt(data);
        boolean ritorno = (dataInt == GRASS_TILE || dataInt == DIRT_TILE || dataInt == SELECT || dataInt == ATTACK) || data.length() == 3;
        return !ritorno;
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
