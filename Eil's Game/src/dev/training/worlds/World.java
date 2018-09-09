
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
     * selezionabili, nonché i "buoni", e quali quelli non selezionabili
     */
    private int lowerBound_allies, upperBound_allies;
    private int lowerBound_opponents, upperBound_opponents;
    // Stacco tra gli omini propri e quelli avversari
    private final int DELTA_OMINI = 100;
    
    private boolean path, attack;
    
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo.
     * Una matrice designa il mondo, la mappa di gioco, una la posizione degli 
     * omini e una la selezione dell'utente; infine toGo rappresenta ciò che 
     * verrà inviato al server
     */
    private int[][] world, selections;                                    // Da togliere (abcd)
    private ArrayList<Coordinate> pathSteps, attackSteps;
    
    /**
     * Costanti che servono a definire i separatori utilizzati per codificare la 
     * matrice di interi in String da parte del server
     */
    private final String SEPARATORE_COLONNE = "-";
    private final String SEPARATORE_RIGHE = " ";
    
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
        attackSteps = new ArrayList<>();
    }
    
    public void update() {
        this.reciveWorld();
        selection();
        attack();
        
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
                
                renderSfondo(x, y, g);
                /**
                 * Moltiplico la x e la y per larghezza e altezza in quanto, 
                 * per esempio, se voglio disegnare il Tile con cordinate 1, 3 
                 * vuol dire che ho già disegnato i precedenti e quindi dovrò 
                 * farlo partire da 1 * larghezza del Tile, 3 * altezza del 
                 * Tile.
                 */
                getTile(x, y).render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
                
                renderSelectionAttack(x, y, g);
            }
        }
    }
    
    private void selection() {
        int x = handeler.getMouseManager().getxTile();
        int y = handeler.getMouseManager().getyTile();
        Coordinate coordinate = new Coordinate(x, y);
        
        if(attack) {
        // Caso in cui si è fuori dalla mappa
        } else if(x < 0 || y < 0 || x >= width || y >= height) {
            // Sbagliato
            resetSelection();
        // Casi in cui è stato premuto il mouse
        } else if(handeler.getMouseManager().isPressed) {
            // Caso in cui il tile su cui si è è un charapter selezionabile
            if(world[x][y] >= lowerBound_allies && world[x][y] < upperBound_allies) {
                // Se si stava già tracciando un percorso e si seleziona un charapter
                if(path) {
                    // Sbagliato (Se non hai appena iniziato altrimenti non si fa niente)
                    if(pathSteps.size() != 1) {
                        resetSelection();
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
                resetSelection();
            // Caso in cui il tile premuto sia erba o terra
            } else {
                if(!path) {
                    // Sbagliato
                    resetSelection();
                } else {
                    // Giusto:  continua
                    selections[x][y] = Tile.SELECT;
                    if(!pathSteps.get(pathSteps.size() - 1).equal(coordinate))
                        pathSteps.add(new Coordinate(x, y));
                }
            }
        } else if(path) {
            // Giusto: invia
            sendPath();
        // Caso in cui il mouse non è stato premuto e non si stava tracciando un percorso
        } else {
            // Sbagliato
            resetSelection();
        }
    }
    
    private void attack() {
        int x = handeler.getMouseManager().getxTile();
        int y = handeler.getMouseManager().getyTile();
        
        Coordinate coordinate = new Coordinate(x, y);
        // Caso in cui si è fuori dalla mappa
        if(x < 0 || y < 0 || x >= width || y >= height) {
            resetAttack();
        // Casi in cui è stato premuto il mouse
        } else if(handeler.getMouseManager().isPressed && handeler.getKeyManager().attack) {
            // Caso in cui il tile su cui si è è un charapter selezionabile
            if(world[x][y] >= lowerBound_allies && world[x][y] < upperBound_allies) {
                // Se si stava già tracciando un attacco e si seleziona un charapter
                if(attack) {
                    // Sbagliato (Se non hai appena iniziato altrimenti non si fa niente)
                    if(attackSteps.size() != 1) {
                        resetAttack();
                    }
                // Unico caso in cui si può iniziare a tracciare un percorso
                } else {
                    // Giusto: inizio
                    attack = true;
                    selections[x][y] = Tile.ATTACK;
                    if(attackSteps.isEmpty()) {
                        attackSteps.add(new Coordinate(x, y));
                    }
                }
            /**
             * Caso in cui il tile selezionato può essere sia roccia che un 
             * giocatore alleato che un giocatore avversario
             */
            } else if(Tile.tiles[world[x][y]].isSolid()) {
                // Giusto: invia
                if(world[x][y] >= lowerBound_opponents && world[x][y] <= upperBound_opponents)
                    if(!attackSteps.isEmpty())
                        sendAttack();
                // Sbagliato
                resetAttack();
            // Caso in cui il tile premuto sia erba o terra
            } else {
                if(!attack) {
                    // Sbagliato
                    resetAttack();
                } else {
                    // Giusto:  continua
                    selections[x][y] = Tile.ATTACK;
                    if(!attackSteps.get(attackSteps.size() - 1).equal(coordinate))
                        attackSteps.add(new Coordinate(x, y));
                }
            }
        } else if(attack) {
            // Caso in cui si vuole fermare il giocatore all'attacco
            if(attackSteps.size() == 1)
                sendAttack();
            // Sbagliato
            else
                resetAttack();
        }
    }
    
    /**
     * Funzione che resetta gli elementi della selezione
     */
    private void resetSelection() {
        path = false;
        selections = new int[width][height];
        pathSteps = new ArrayList<>();
    }
    
    private void resetAttack() {
        attack = false;
        selections = new int[width][height];
        attackSteps = new ArrayList<>();
    }
    
    /**
     * Funzione che avvia l'invio dell'istruzione al server
     */
    private void sendPath() {
        System.out.println("Invio path: " + pathSteps.toString());
        handeler.getGame().getClient().inviaPath(pathSteps);
        resetSelection();
    }
    
    private void sendAttack() {
        System.out.println("Invio attack: " + attackSteps.toString());
        resetAttack();
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
    
    private void renderSfondo(int x, int y, Graphics g) {
        if(world[x][y] >= lowerBound_allies && world[x][y] <= upperBound_allies)
            Tile.tiles[0].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
        if(world[x][y] >= lowerBound_opponents && world[x][y] <= upperBound_opponents)
            Tile.tiles[1].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
    }
    
    private void renderSelectionAttack(int x, int y, Graphics g) {
        /**
         * Se il tile preso in considerazione è presente nella matrice 
         * selections significa che bisogna disegnarci sopra il Tile 
         * selezione
         */
        if (selections[x][y] == Tile.SELECT)
            Tile.tiles[selections[x][y]].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
        /**
         * Se sul tile preso i nconsiderazione bisogna disegnarci sopra 
         * il tile attack
         */
        else if (selections[x][y] == Tile.ATTACK)
            Tile.tiles[selections[x][y]].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
        /**
         * Se il Tile è quello su cui è presente il mouse bisogna 
         * disegnarci sopra il Tile selezione
         */
        else if(x == handeler.getMouseManager().getxTile() && y == handeler.getMouseManager().getyTile())
            Tile.tiles[Tile.SELECT].render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()));
                
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
    public int[][] getWorld() { return world; }
    
    /**
     * Funzione che indica qual'è il range dei propri giocatori e il range di 
     * quelli avversari
     * @param lb
     * @param ub 
     */
    public void setCharaptersBounds(int lb, int ub) {
        if(lb == Tile.LOWER_BOUND_1) {
            this.lowerBound_allies = Tile.LOWER_BOUND_1; this.upperBound_allies = Tile.UPPER_BOUND_1;
            this.lowerBound_opponents = Tile.LOWER_BOUND_2; this.upperBound_opponents = Tile.UPPER_BOUND_2;
        } else {
            this.lowerBound_allies = Tile.LOWER_BOUND_2; this.upperBound_allies = Tile.UPPER_BOUND_2;
            this.lowerBound_opponents = Tile.LOWER_BOUND_1; this.upperBound_opponents = Tile.UPPER_BOUND_1;
        }
    }
    
    /**
     * Crea la matrica world
     * @param path percorso del file in cui si trova il mondo codificato
     */
    private void loadWorld(String path) {                                       // Da togliere
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
    
    /**
     * Funzione che aggiorna il world da server prendendo una String da ClientUDP 
     * e poi convertendola in una matrice di interi
     */
    private void reciveWorld() { 
        String updateMap = ClientUDP.map;
        if(updateMap != null) {
            System.out.println("non è null");
            if(world == null) {
                world = new int[width][height];
            }
            String[] colonne = updateMap.split(SEPARATORE_COLONNE);
            String[] elementi;
            for(int i = 0; i < width; i++) {
                elementi = colonne[i].split(SEPARATORE_RIGHE);
                for(int j = 0; j < height; j++) {
                    world[i][j] = Utils.parseInt(elementi[j]);
                }
            }
        }
    }
    
}
