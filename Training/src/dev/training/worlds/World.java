package dev.training.worlds;

import dev.training.Game;
import dev.training.Utils;
import dev.training.tiles.Tile;
import java.awt.Graphics;

public class World {
    
    private Game game;
    private int width, height, spawnX, spawnY;
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo
     */
    private int[][] tiles;
    
    /**
     * 
     * @param game
     * @param path il percorso per raggiungere il "World" che abbiamo salvato 
     * da qualche parte nel file system.
     */
    public World(Game game, String path) {
        this.game = game;
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
                getTile(x, y).render(g, (int) (spawnX + x * Tile.TILEWIDTH - game.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - game.getGameCamera().getyOffset()));
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
        height = Utils.parseInt(token[1]);
        spawnX = Utils.parseInt(token[2]);
        spawnY = Utils.parseInt(token[3]);
        
        tiles = new int[width][height];
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++)
                /**
                 * Se siamo alla terza riga e quarta colonna significa che 
                 * abbiamo già copiato un numero di elementi pari a: 2 * numero 
                 * totale di colonne + numero di colonne. si aggiunge 4 perchè i 
                 * primi 4 elementi di token sono utilizzati per altri scopi.
                 */
                tiles[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
        
    }
    
}
