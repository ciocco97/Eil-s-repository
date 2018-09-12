
package dev.training.worlds;

import dev.conn.client.ClientUDP;
import dev.training.Handeler;
import dev.training.Utils;
import dev.training.tiles.Tile;
import java.awt.Graphics;
import java.util.ArrayList;

public class World {
    
    private String team; //penso non ci sia bisogno di commentare
    
    private Handeler handeler;
    /**
     * width rappresenta il numero di colonne di tiles mentre height il numero di righe
     * spawnX e spawnY sono sempre 0
     */
    private int width, height, spawnX, spawnY;
    
    private String actionGroundAttack = "a", actionCharapterMoovement = "m", 
            actionArcherAttack = "f", actionRequestInfo = "i";
    
    private boolean path, groundAttack, flyAttack, request;
    
    /**
     * Una matrice di ID che indicano come sono disposti i "Tile" all'interno 
     * del mondo.
     * Una matrice designa il mondo, la mappa di gioco, una la posizione degli 
     * omini e una la selezione dell'utente; infine toGo rappresenta ciò che 
     * verrà inviato al server
     */
    private int[][] world, selections;
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
        while((team = handeler.getGame().getClient().getTeam()) == null) {
            System.out.println("Attesa ricezione team");
        }
    }
    
    public void update() {
        reciveWorld();
        if (world!=null){
            selection();
            attack();
            request();
        }
               
    }
    
    public void request() {
        int x = handeler.getMouseManager().getxTile();
        int y = handeler.getMouseManager().getyTile();
        
        if(handeler.getKeyManager().request) {
            if(handeler.getMouseManager().isPressed) {
                if(!(x < 0 || y < 0 || x >= width || y >= height)) {
                    String IDWorld = "" + world[x][y];
                    if(isMyCharapter(IDWorld)) {
                        if(!request) {
                            request = true;
                            pathSteps.add(new Coordinate(x, y));
                            handeler.getGame().getClient().inviaPath(pathSteps, actionRequestInfo);
                        }
                    }
                }
            }
        } else if (request) {
            request = false;
            resetAttack();
        }
    }
    
    public void render(Graphics g) {
        /**
         * Queste variabili servono per iniziare a renderizzare il mondo 
         * solamente dove serve
         */
        update();
        System.out.println("in world: "+Tile.TILEHEIGHT);
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
                Tile.render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), 
                        (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()), ""+world[x][y]);
                
                renderSelectionAttack(x, y, g);
            }
        }
    }
    
    private void selection() {
        int x = handeler.getMouseManager().getxTile();
        int y = handeler.getMouseManager().getyTile();
        String worldID = null;
        try {
            worldID = "" + world[x][y];
        }catch(Exception e) {
            
        }
        Coordinate coordinate = new Coordinate(x, y);
        if(groundAttack || handeler.getKeyManager().attack) {
        // Caso in cui si è fuori dalla mappa
        } else if(x < 0 || y < 0 || x >= width || y >= height) {
            // Sbagliato
            resetSelection();
        // Casi in cui è stato premuto il mouse
        } else if(handeler.getMouseManager().isPressed) {
            // Caso in cui il tile su cui si è è un charapter selezionabile
            if((worldID).charAt(0) == Tile.TEAM 
                    && (worldID).length() == Tile.CHARAPTER_ID_SIZE) {
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
//                        System.out.println("Inizio selezione");
                        pathSteps.add(new Coordinate(x, y));
                    }
                }
                
            } else if(Tile.isSolid(worldID)) {
//                System.out.println("Sbagliato perché è solido");
                // Sbagliato
                resetSelection();
            // Caso in cui il tile premuto sia erba o terra
            } else {
                if(!path) {
                    // Sbagliato
//                    System.out.println("Premuto-selezione non iniziata");
                    resetSelection();
                } else {
                    // Giusto:  continua
//                    System.out.println("Premuto-path-grass");
                    selections[x][y] = Tile.SELECT;
                    if(!pathSteps.get(pathSteps.size() - 1).equal(coordinate)) {
                        pathSteps.add(new Coordinate(x, y));
                    }
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
        String worldID = null;
        try { worldID = "" + world[x][y]; } catch (Exception e) { }
        
        if(groundAttack) {
            attaccoSoldato(x, y, coordinate, worldID);
        } else if(flyAttack) {
            attaccoArcere(x, y, coordinate, worldID);
        } else {
            if(handeler.getKeyManager().attack && handeler.getMouseManager().isPressed) {
                if(!(x < 0 || y < 0 || x >= width || y >= height)) {
                    if(isMyCharapterType(worldID, Tile.ARCHER_ID)) {
                        System.out.println("Inizio attacco arcere");
                        flyAttack = true;
                        attackSteps.add(coordinate);
                        selezioneAttaccoArcere(x, y);
                        archerTileAttack = coordinate;
                    } else if(isMyCharapterType(worldID, Tile.SOLDIER_ID)) {
                        System.out.println("Inizio attacco soldato");
                        groundAttack = true;
                        attackSteps.add(coordinate);
                        selections[x][y] = Tile.ATTACK;
                    }
                }
            }
        }
        
    }
    
    private void attaccoSoldato(int x, int y, Coordinate coordinate, String worldID) {
        if(worldID != null) {
            if(handeler.getKeyManager().attack && handeler.getMouseManager().isPressed) {
                if(isMyCharapter(worldID)) {
                    if(attackSteps.size() != 1) {
//                        System.out.println("Reset Perché sono finito su un mio personaggio");
                        resetAttack();
                    }
                } else if(isEnemyCharapter(worldID)) {
//                    System.out.println("Path completato");
                    sendGroundAttack();
                } else if(Tile.isSolid(worldID)) {
//                    System.out.println("É solido");
                    resetAttack();
                } else {
                    if(!attackSteps.get(attackSteps.size() - 1).equal(coordinate)) {
//                        System.out.println("Aggiunte coordinate");
                        attackSteps.add(coordinate);
                    }
                    selections[x][y] = Tile.ATTACK;
                }
            } else {
                resetAttack();
            }
        } else resetAttack();
    }
    
    private Coordinate archerTileAttack;
    
    private void attaccoArcere(int x, int y, Coordinate coordinate, String worldID) {
        if(handeler.getKeyManager().attack && handeler.getMouseManager().isPressed) {
            if(worldID != null) {
                int selezionato = selections[x][y];
                if(isArrowSelection("" + selezionato)) {
                    System.out.println("Arrow selected");
                    selections = new int[width][height];
                    selezioneAttaccoArcere(archerTileAttack.getX(), archerTileAttack.getY());
                    selections[x][y] = Tile.ATTACK;
                    attackSteps.add(coordinate);
                } else if(isMyCharapter("" + world[x][y])) {
                    if(!coordinate.equal(archerTileAttack)) {
                        System.out.println("Non è il charapter iniziale");
                        resetAttack();
                    }
                }
            } else {  }
            
        } else {
            if(attackSteps.size() != 1)
                sendFlyAttack();
        }
    }
    
    private void selezioneAttaccoArcere(int x, int y) {
        selections[x][y] = Tile.ATTACK;
        String selezione;
        
        selezione = Tile.UP;
        selections[x][y - 1] = Utils.parseInt(selezione);
        
        selezione = Tile.UP_RIGHT;
        selections[x + 1][y - 1] = Utils.parseInt(selezione);
        
        selezione = Tile.RIGHT;
        selections[x + 1][y] = Utils.parseInt(selezione);
        
        selezione = Tile.DOWN_RIGHT;
        selections[x + 1][y + 1] = Utils.parseInt(selezione);
        
        selezione = Tile.DOWN;
        selections[x][y + 1] = Utils.parseInt(selezione);
        
        selezione = Tile.DOWN_LEFT;
        selections[x - 1][y + 1] = Utils.parseInt(selezione);
        
        selezione = Tile.LEFT;
        selections[x - 1][y] = Utils.parseInt(selezione);
        
        selezione = Tile.UP_LEFT;
        selections[x - 1][y - 1] = Utils.parseInt(selezione);
    }
    
    private boolean isMyCharapter(String ID) {
        boolean is;
        if(ID.length() == Tile.CHARAPTER_ID_SIZE) {
            String teamObject = "" + ID.charAt(Tile.TEAM_POSITION);
            is = teamObject.equals(this.team);
        } else is = false;
        return is;
    }
    
    private boolean isArrowSelection(String ID) {
        int point = Utils.parseInt(ID);
        int lowerRange = Utils.parseInt(Tile.UP);
        int upperRange = Utils.parseInt(Tile.UP_LEFT);
        return point >= lowerRange && point <= upperRange;
    }
    
    private boolean isArrow(String ID) {
        return ID.length() == Tile.ARROW_ID_SIZE;
    }
    
    private boolean isEnemyCharapter(String ID) {
        boolean is;
        if(ID.length() == Tile.CHARAPTER_ID_SIZE) {
            String teamObject = "" + ID.charAt(Tile.TEAM_POSITION);
            is = !teamObject.equals(this.team);
        } else is = false;
        return is;
    }
    
    private boolean isMyCharapterType(String ID, String type) {
        boolean isEqual;
        if(ID.length() == Tile.CHARAPTER_ID_SIZE) {
            String teamObject = "" + ID.charAt(Tile.TEAM_POSITION);
            String typologyObject = "" + ID.charAt(Tile.TIPOLOGY_POSITION);
//            System.out.println("Per " + ID + " controllo se teamObject (" + teamObject + ") == " + this.team + " e se typology (" + typologyObject + ") == " + type);
            isEqual = (teamObject.equals(this.team) && typologyObject.equals(type));
//            System.out.println("risultato: " + isEqual);
        } else { isEqual = false; }
        return isEqual;
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
        groundAttack = flyAttack = false;
        selections = new int[width][height];
        attackSteps = new ArrayList<>();
        archerTileAttack = null;
    }
    
    /**
     * Funzione che avvia l'invio dell'istruzione al server
     */
    private void sendPath() {
        System.out.println("Invio path: " + pathSteps.toString());
        handeler.getGame().getClient().inviaPath(pathSteps, actionCharapterMoovement);
        resetSelection();
    }
    
    private void sendGroundAttack() {
        System.out.println("Invio attack: " + attackSteps.toString());
        handeler.getGame().getClient().inviaPath(attackSteps, actionGroundAttack);
        resetAttack();
    }
    
    private void sendFlyAttack() {
        for(int i = 1; i < attackSteps.size() - 1; i++) {
            attackSteps.remove(i);
            i--;
        }
        System.out.println("Invio attack: " + attackSteps.toString());
        handeler.getGame().getClient().inviaPath(attackSteps, actionArcherAttack);
        
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
    
    
    private void renderSelectionAttack(int x, int y, Graphics g) {
        
        if(selections[x][y] != 0) {
            Tile.render(g, (int) (spawnX + x * Tile.TILEWIDTH - handeler.getGameCamera().getxOffset()), (int) (spawnY + y * Tile.TILEHEIGHT - handeler.getGameCamera().getyOffset()), ""+selections[x][y]);
        }
    
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
    public int[][] getWorld() { return world; }
    
    /**
     * Funzione che aggiorna il world da server prendendo una String da ClientUDP 
     * e poi convertendola in una matrice di interi
     */
    private void reciveWorld() {
        String updateMap = ClientUDP.map;
        if(updateMap != null) {
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
