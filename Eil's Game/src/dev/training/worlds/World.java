package dev.training.worlds;

import dev.training.Game;
import dev.training.Utils;
import dev.training.input.MouseManager;
import dev.training.tiles.Tile;
import java.awt.Graphics;

public class World {
    
    private Game game;
    private Boolean mousePressed;
    private int width, height, spawnX, spawnY, xMouseTile, yMouseTile;
    private MouseManager mouseManager;
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo.
     * Una matrice designa il mondo, la mappa di gioco, una la posizione degli 
     * omini e una la selezione dell'utente
     */
    private int[][] world, charapters, selections, tiles, toGo;
    
    /**
     * 
     * @param game
     * @param path il percorso per raggiungere il "World" che abbiamo salvato 
     * da qualche parte nel file system.
     */
    public World(Game game, String path) {
        this.game = game;
        mousePressed = false;
        this.mouseManager = game.getMouseManager();
        loadWorld(path);
    }
    
    public void update() {
        xMouseTile = mouseManager.getxTile();
        yMouseTile = mouseManager.getyTile();
        if (mouseManager.isPressed)
        {
            
            if (!mousePressed){
            selections[xMouseTile][yMouseTile] = 1;
            mousePressed=true;
            }
            else
            selections[xMouseTile][yMouseTile] = 2;
        }else if (mousePressed)
        {
            selections[xMouseTile][yMouseTile] = 3;
            mousePressed=false;
            cleanSelections();
        }
        
    }
    /**
     * metodo che gestisce le caselle selezionate quando viene premuto il mouse
     */
    private void mousePressed(){
        
    }
    private void cleanSelections()
    {
        toGo=selections.clone();
        selections = new int[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    private void getSelectedTile()
    {
    }
    public void render(Graphics g) {
        /**
         * Queste variabili servono per iniziare a renderizzare il mondo 
         * solamente dove serve
         */
        update();
        int xStart = (int) Math.max(0, game.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (game.getGameCamera().getxOffset() + game.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, game.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (game.getGameCamera().getyOffset() + game.getHeight()) / Tile.TILEHEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++) {
            for(int x = xStart; x < xEnd; x++) {
                /**
                 * Moltiplico la x e la y per larghezza e altezza in quanto, 
                 * per esempio, se voglio disegnare il Tile con cordinate 1, 3 
                 * vuol dire che ho già disegnato i precedenti e quindi dovrò 
                 * farlo partire da 1 * larghezza del Tile, 3 * altezza del 
                 * Tile.
                 */
                getTile(x, y).render(g, (int) (spawnX + x * Tile.TILEWIDTH - game.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - game.getGameCamera().getyOffset()));
            }
        }
    }
    private void tilesGenerator()
    {
        
    }
    public Tile getTile(int x, int y) {
        Tile t;
        
        if(charapters[x][y] != 0) {
            return Tile.playerTile;
        } else {
            if(x == xMouseTile && y == yMouseTile)
            {
                t = Tile.rockTile;
            }
            if(selections[x][y] != 0) {
                t = Tile.selectedTile;
            } else {
                
            }
        }
        
        /**
         * Controllo per vedere se il tile è o non è selezionato e se la 
         * selezione prevede solamente il passaggio del mouse o anche la 
         * pressione
         */
        if(x == xMouseTile && y == yMouseTile)
            t = Tile.rockTile;
        
        else t = Tile.tiles[world[x][y]];
        /**
         * Se nell'array di tutti i tipi di tiles cerco di accedere ad un tile 
         * che non ho settato, mi ritorna il Tile di default: dirtTile
         */
        if(t == null)
            t = Tile.dirtTile;
        
        return t;
    }
    
    private void loadWorld(String path) {
        String file = Utils.loadFileAsStrig(path);
        String[] token = file.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        spawnX = Utils.parseInt(token[2]);
        spawnY = Utils.parseInt(token[3]);
        
        world = new int[width][height];
        charapters = new int[width][height];
        tiles = new int[width][height];
        selections = new int[width][height];
        charapters[3][4] = 1;
        
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++)
                /**
                 * Se siamo alla terza riga e quarta colonna significa che 
                 * abbiamo già copiato un numero di elementi pari a: 2 * numero 
                 * totale di colonne + numero di colonne. si aggiunge 4 perchè i 
                 * primi 4 elementi di token sono utilizzati per altri scopi.
                 */
                world[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
        
    }
    
}
