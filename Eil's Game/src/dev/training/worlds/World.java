
package dev.training.worlds;

import dev.conn.client.ClientUDP;
import dev.training.Handeler;
import dev.training.Utils;
import dev.training.tiles.Tile;
import java.awt.Graphics;
import java.util.ArrayList;

public class World {
    
    private Handeler handeler;
    /**
     * width rappresenta il numero di colonne di tiles mentre height il numero di righe
     * spawnX e spawnY sono sempre 0
     */
    private int width, height, spawnX, spawnY;
    
    /**
     * Variabili settate da remoto che indicano al client quali sono i charapter 
     * selezionabili, nonché i "buoni"
     */
    private int lowerBound, upperBound;
    
    private boolean path;
    
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo.
     * Una matrice designa il mondo, la mappa di gioco, una la posizione degli 
     * omini e una la selezione dell'utente; infine toGo rappresenta ciò che 
     * verrà inviato al server
     */
    private int[][] world, selections, abcd;                                    // Da togliere (abcd)
    private ArrayList<Coordinate> pathSteps;
    
    /**
     * Costanti che servono a definire i separatori utilizzati per codificare la 
     * matrice di interi in String da parte del server
     */
    private final String SEPARATORE_RIGHE = "-";
    private final String SEPARATORE_COLONNE = " ";
    
    /**
     * Costruttore
     * @param handeler
     * @param width
     * @param height
     */
    public World(Handeler handeler, int width, int height) {
        this.handeler = handeler;
        this.width = width;
        this.height = height;
        world = null;
        
//        world = new int[width][height];                                         // Da togliere
//        loadWorld("res//worlds/world");                                         // Da togliere
        selections = new int[width][height];
        pathSteps = new ArrayList<>();
    }
    
    public void update() {
        reciveWorld();
        selection();
    }
    
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
                if (selections[x][y] != 0)
                    Tile.tiles[selections[x][y]].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
                if(x == handeler.getMouseManager().getxTile() && y == handeler.getMouseManager().getyTile())
                    Tile.tiles[Tile.SELECT].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
            }
        }
    }
    
    private void selection() {
        int x = handeler.getMouseManager().getxTile();
        int y = handeler.getMouseManager().getyTile();
        Coordinate coordinate = new Coordinate(x, y);
        // Caso in cui si è fuori dalla mappa
        if(x < 0 || y < 0 || x > width || y > height) {
            // Sbagliato
            reset();
        // Casi in cui è stato premuto il mouse
        } else if(handeler.getMouseManager().isPressed) {
            // Caso in cui il tile su cui si è è un charapter
            if(world[x][y] > lowerBound && world[x][y] < upperBound) {
                // Se si stava già tracciando un percorso e si seleziona un charapter
                if(path) {
                    // Sbagliato (Se non hai appena iniziato altrimenti non si fa niente)
                    if(pathSteps.size() != 1) {
                        reset();
                    }
                // Unico caso in cui si può iniziare a tracciare un percorso
                } else {
                    // Giusto: inizio
                    path = true;
                    selections[x][y] = Tile.SELECT;
                    if(pathSteps.isEmpty()) {
                        pathSteps.add(new Coordinate(x, y));
                    }
                }
            } else if(Tile.tiles[world[x][y]].isSolid()) {
                // Sbagliato
                reset();
            // Caso in cui il tile premuto sia erba o terra
            } else {
                if(!path) {
                    // Sbagliato
                    reset();
                } else {
                    // Giusto:  continua
                    selections[x][y] = Tile.SELECT;
                    if(!pathSteps.get(pathSteps.size() - 1).equal(coordinate))
                        pathSteps.add(new Coordinate(x, y));
                }
            }
        } else if(path) {
            // Giusto: invia
            invia();
        // Caso in cui il mouse non è stato premuto e non si stava tracciando un percorso
        } else {
            // Sbagliato
            reset();
        }
    }
    
    /**
     * Funzione che resetta gli elementi della selezione
     */
    private void reset() {
        path = false;
        selections = new int[width][height];
        pathSteps = new ArrayList<>();
    }
    
    /**
     * Funzione che comincia l'invio dell'istruzione al server
     */
    private void invia() {
        if(pathSteps.size() > 1) {
            System.out.println("Invio di: " + pathSteps.toString());
            handeler.getGame().getClient().inviaPath(pathSteps);
        }
        reset();
    }
    
    /**
     * Funzione che sceglie il Tile da visualizzare
     * @param x
     * @param y
     * @return 
     */
    public Tile getTile(int x, int y) {
        Tile t = Tile.tiles[world[x][y]];
        
        /**
         * Se nell'array di tutti i tipi di tiles cerco di accedere ad un tile 
         * che non ho settato, mi ritorna il Tile di default: dirtTile
         */
        if(t == null) t = Tile.tiles[11];
        return t;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
    public int[][] getWorld() { return world; }
    
    public void setCharaptersBounds(int lb, int ub) {
        this.lowerBound = lb;
        this.upperBound = ub;
    }
    
    /**
     * Crea la matrica world
     * @param path percorso del file in cui si trova il mondo codificato
     */
    private void loadWorld(String path) {                                       // Da togliere
        String file = Utils.loadFileAsStrig(path);
        String[] token = file.split("\\s+");
        width = Utils.parseInt(token[0]);                                       // Importante: per adesso qua vengono settati numero righe e numero colonne
        height = Utils.parseInt(token[1]);
        spawnX = Utils.parseInt(token[2]);
        spawnY = Utils.parseInt(token[3]);
        
        abcd = new int[width][height];
        
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++)
                /**
                 * Se siamo alla terza riga e quarta colonna significa che 
                 * abbiamo già copiato un numero di elementi pari a: 2 * numero 
                 * totale di colonne + numero di colonne. si aggiunge 4 perchè i 
                 * primi 4 elementi di token sono utilizzati per altri scopi.
                 */
                abcd[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
        
    }
    
    /**
     * Funzione che aggiorna il world da server prendendo una String da ClientUDP 
     * e poi convertendola in una matrice di interi
     */
    private void reciveWorld() {                                                      // Da cambiare perchè bypassa l'update remoto          
        String updateMap = ClientUDP.map;
        if(updateMap != null) {
            if(world == null) {
                System.out.println("Creazione matrice");
                world = new int[width][height];
            }
            String[] righeMappa = updateMap.split(SEPARATORE_RIGHE);
            String[] rigaSplittata;
            for(int i = 0; i < righeMappa.length; i++) {
                rigaSplittata = righeMappa[i].split(SEPARATORE_COLONNE);
                for (int j = 0; j < rigaSplittata.length; j++) {
                    world[i][j] = Utils.parseInt(rigaSplittata[j]);
                }
            }
        }
    }
    
}
