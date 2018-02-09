package dev.training.worlds;

import dev.training.Utils;
import dev.training.tiles.Tile;
import java.awt.Graphics;

public class World {
    
    private int width, height, spawnX, spawnY;
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo
     */
    private int[][] tiles;
    
    /**
     * 
     * @param path il percorso per raggiungere il "World" che abbiamo salvato 
     * da qualche parte nel file system.
     */
    public World(String path) {
        loadWorld(path);
    }
    
    public void update() {
        
    }
    
    public void render(Graphics g) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                /**
                 * Moltiplico la x e la y per larghezza e altezza in quanto, 
                 * per esempio, se voglio disegnare il Tile con cordinate 1, 3 
                 * vuol dire che ho già disegnato i precedenti e quindi dovrò 
                 * farlo partire da 1 * larghezza del Tile, 3 * altezza del 
                 * Tile.
                 */
                getTile(x, y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT);
            }
        }
    }
    
    public Tile getTile(int x, int y) {
        Tile t = Tile.tiles[tiles[x][y]];
        /**
         * Se nell'array di tutti i tipi di tiles cerco di accedere ad un tile 
         * che non ho settato, mi ritorna il Tile di default: dirtTile
         */
        if(t == null)
            return Tile.dirtTile;
        return t;
    }
    
    private void loadWorld(String path) {
        String file = Utils.loadFileAsStrig(path);
        String[] token = file.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[2]);
        spawnX = Utils.parseInt(token[3]);
        spawnY = Utils.parseInt(token[4]);
        
        tiles = new int[width][height];
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++)
                tiles[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
        
    }
    
}
