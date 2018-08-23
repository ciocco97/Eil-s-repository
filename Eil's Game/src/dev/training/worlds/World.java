
package dev.training.worlds;

import dev.training.Handeler;
import dev.training.Utils;
import dev.training.tiles.Tile;
import java.awt.Graphics;

public class World {
    
    private Handeler handeler;
    
    private Boolean wasMousePressed, enablePath;
    private int width, height, spawnX, spawnY, xMouseTile, yMouseTile, lastTileX, lastTileY;
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo.
     * Una matrice designa il mondo, la mappa di gioco, una la posizione degli 
     * omini e una la selezione dell'utente
     */
    private int[][] world, charapters, selections, toGo;
    
    // Costanti
    private final int START_PATH = 1;
    private final int PATH = 2;
    private final int END_PATH = 3;
    
    
    /**
     * Costruttore
     * @param handeler
     * @param path il percorso per raggiungere il "World" che abbiamo salvato 
     * da qualche parte nel file system.
     */
    public World(Handeler handeler, String path) {
        this.handeler = handeler;
        
        loadWorld(path);
        
        charapters = new int[width][height];
        selections = new int[width][height];
        // Inserisco un character all'interno della mappa
        charapters[3][4] = 1;
        
        wasMousePressed = enablePath = false;
    }
    
    /**
     * Funzione assolutamente da mettere a posto in quanto disordinata
     */
    public void update() {
        xMouseTile = handeler.getMouseManager().getxTile();
        yMouseTile = handeler.getMouseManager().getyTile();
        
        
        // Se esco dai bordi 
        if (!(    xMouseTile > 0 && 
                yMouseTile > 0 && 
                xMouseTile < width && 
                yMouseTile < height)) 
        {
            cleanSelections();
            return;
        }
            
        
        if (charapters[xMouseTile][yMouseTile] != 0 && handeler.getMouseManager().isPressed) enablePath=true;
        
        // Controllo per vedere se la creazione del path è possibile
        if (handeler.getMouseManager().isPressed && enablePath) {
            /**
             * Entrati in questo if sappiamo che stiamo tracciando un percorso:
             * la prima casella selezionata è stata un player
             */
            if (!wasMousePressed){
                selections[xMouseTile][yMouseTile] = START_PATH;
                wasMousePressed = true;
            }
            
            // In questo caso il percorso è enabled e siamo lungo il percorso, aggiungo le tile del percorso alla LinkedList
            else{
                if (xMouseTile!=lastTileX && yMouseTile!=lastTileY)
                    {
                        System.out.println("mamma");
                        selections[xMouseTile][yMouseTile] = PATH;
                        lastTileX=xMouseTile;
                        lastTileY=yMouseTile;
                    }
            }
                
            
        /**
         * Se il mouse non è più premuto ma il flag "mousePressed" è ancora true
         * Significa che il tile è l'ultimodel path
         */
        } else if (wasMousePressed) {
            selections[xMouseTile][yMouseTile] = END_PATH;
            wasMousePressed = false;
            cleanSelections();
            enablePath = false;
            lastTileX = -1;
            lastTileY = -1;
        }
        
    }
    /**
     * Funzione che crea la 
     */
    private void cleanSelections() {
        toGo=selections.clone();
        selections = new int[width][height];
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
    public void render(Graphics g) {
        /**
         * Queste variabili servono per iniziare a renderizzare il mondo 
         * solamente dove serve
         */
        update();
        int xStart = (int) Math.max(0, handeler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (handeler.getGameCamera().getxOffset() + handeler.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, handeler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (handeler.getGameCamera().getyOffset() + handeler.getHeight()) / Tile.TILEHEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++) {
            for(int x = xStart; x < xEnd; x++) {
                /**
                 * Moltiplico la x e la y per larghezza e altezza in quanto, 
                 * per esempio, se voglio disegnare il Tile con cordinate 1, 3 
                 * vuol dire che ho già disegnato i precedenti e quindi dovrò 
                 * farlo partire da 1 * larghezza del Tile, 3 * altezza del 
                 * Tile.
                 */
                getTile(x, y).render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
                /**
                 * Se il Tile è quello su cui è presente il mouse bisogna 
                 * disegnarci sopra il Tile selezione
                 */
                if (x == xMouseTile && y == yMouseTile)
                    Tile.selectedTile.render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
            }
        }
    }
    
    /**
     * Funzione che sceglie il Tile da visualizzare
     * @param x
     * @param y
     * @return 
     */
    public Tile getTile(int x, int y) {
        // Di default viene scelto il Tile in world
        Tile t = Tile.tiles[world[x][y]];
        if(charapters[x][y] != 0) t = Tile.playerTile;
        else if(selections[x][y] != 0) t = Tile.selectedTile;
        
        /**
         * Se nell'array di tutti i tipi di tiles cerco di accedere ad un tile 
         * che non ho settato, mi ritorna il Tile di default: dirtTile
         */
        if(t == null) t = Tile.dirtTile;
        return t;
    }
    
    /**
     * Crea la matrica world
     * @param path percorso del file in cui si trova il mondo codificato
     */
    private void loadWorld(String path) {
        String file = Utils.loadFileAsStrig(path);
        String[] token = file.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        spawnX = Utils.parseInt(token[2]);
        spawnY = Utils.parseInt(token[3]);
        
        world = new int[width][height];
        
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
