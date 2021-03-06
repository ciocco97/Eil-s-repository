package server;

import Models.Archer;
import Models.Arrow;
import Models.Charapter;
import Models.King;
import Models.Soldier;
import Utils.Coordinate;
import Utils.Move;
import Utils.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;




public class Game {
    
    private int width, height, king1ID, king2ID;
    private int[][] world, ground;
    public static int[][] victoryMap, looserMap;
    private LinkedList<Charapter> charapters;
    private ArrayList<Move> moves;
    private LinkedList<Arrow> arrows;
    private int maxTeamID;
    private long startTime;
    private boolean gameTrigger;
    
    public static final int TIME_OF_GAME_SETUP = 30;
    
    
    public Game()
    {
        charapters = new LinkedList();
        moves = new ArrayList();
        arrows = new LinkedList();
        gameTrigger = false;
        
    }
    public void loadWorld(String path) {
        startTime = System.currentTimeMillis();
        victoryMap = loadFinalMaps(path+"winnerWorld");
        looserMap = loadFinalMaps(path+"looserWorld");
        String fileWorld = Utils.loadFileAsStrig(path + "world");
        String fileCharapters = Utils.loadFileAsStrig(path + "charapters");
        String[] token = fileWorld.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        world = new int[width][height];
        ground = new int[width][height];
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++){
                /**
                 * Se siamo alla terza riga e quarta colonna significa che 
                 * abbiamo già copiato un numero di elementi pari a: 2 * numero 
                 * totale di colonne + numero di colonne. si aggiunge 4 perchè i 
                 * primi 4 elementi di token sono utilizzati per altri scopi.
                 */
                world[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
                ground[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
            }
            for (int i = 0; i<height; i++)
                world[(width/2)][i] = 12;    
        
            token = fileCharapters.split("\\s+");
            width = Utils.parseInt(token[0]);
            height = Utils.parseInt(token[1]);
            maxTeamID = Utils.parseInt(token[2])/2;
            Random rand = new Random();
            king1ID = rand.nextInt(maxTeamID) +1;
            king2ID = rand.nextInt(maxTeamID+1) + maxTeamID ;
            boolean archerOrSoldier = true;
             for(int y = 0; y < height; y++) 
                for(int x = 0; x < width; x++){
                    int ID = Utils.parseInt(token[x + (y * width) + 4]);
                    //un if un po strano spero si capisca, in caso è per capire di chi è quel particolare personaggio
                   
                    int owner = ID<maxTeamID?1:2;
                    if (ID != 0){
                        if (ID == king1ID || ID == king2ID)
                            charapters.add(new King (owner, ID, new Coordinate(x,y)));
                        else
                        {
                        if (archerOrSoldier){
                            charapters.add(new Archer(owner, ID, new Coordinate(x,y)));
                        }
                        else
                            charapters.add(new Soldier(owner, ID, new Coordinate(x,y)));
                        }
                    }  
                    archerOrSoldier = !archerOrSoldier;
                }
    }
    
    private int [][] loadFinalMaps(String path)
    {
        String fileWorld = Utils.loadFileAsStrig(path);
        int map[][];
        String[] token = fileWorld.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        map = new int[width][height];
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++){
                /**
                 * Se siamo alla terza riga e quarta colonna significa che 
                 * abbiamo già copiato un numero di elementi pari a: 2 * numero 
                 * totale di colonne + numero di colonne. si aggiunge 4 perchè i 
                 * primi 4 elementi di token sono utilizzati per altri scopi.
                 */
                map[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
            }
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void update(int tick)
    {
        
        long deltaTime = (System.currentTimeMillis() - startTime) / 1000;
        if (deltaTime > TIME_OF_GAME_SETUP && !gameTrigger){ // tempo in secondi
            for (int i = 0; i<height; i++)
                world[(width/2)][i] = ground[(width/2)][i];
                gameTrigger = true;
        }
        if (tick==1)
            {moveCharapter(new int[]{2,0,0});}      
        else if (tick==3)
            {moveCharapter(new int[]{2,1,0}); shoot();checkAttacks();}
        else if (tick==5)
            {moveCharapter(new int[]{2,0,0}); }
        else if (tick==7)
            {moveCharapter(new int[]{2,1,3}); shoot();checkAttacks();}
        for (Arrow arrow:arrows)
            arrow.tick();
        checkArrows();
        checkVictory();
        
        
    }
    public int[][] getMap()
    {
        int[][] map = new int[width][height];
        
        //riempio con il mondo
        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++){
                map[i][j] = world[i][j];
                
            }
        //inserisco gli ID dei vari personaggi
        for (Charapter charap:charapters)
        {
            int x=charap.getCoordinate().getX();
            int y=charap.getCoordinate().getY();
            int ID = Utils.getIdFromCharapter(charap, maxTeamID);
            map[x][y] = ID;
            
        }
        for (Arrow arrow:arrows)
        {
            int direction = arrow.getDirection();
            int X = arrow.getCoordinate().getX();
            int Y = arrow.getCoordinate().getY();
            if (X < 0 || X >= this.width || Y < 0 || Y >= this.height)
                break;
            int ground = world[X][Y];
            int ID = ground + (100*direction);
            map[X][Y] = ID;
        
        }
         // codice del blocco di pietra, cosi non si passa
        
        return map;
        
    }
    
    public void addMoves(ArrayList<Coordinate> list, int owner)
    {   //se è grande solo 1 significa che quel personaggio deve bloccarsi
        if (list.size() == 1)
        {
            //scorro tutte le mosse per vedere quale ha quella posizione come attuale
            for (int i=0; i<moves.size(); i++)
            {   //se non è il proprietario della mossa, non può annullarla
                    //se la prima coordinata della mossa (il giocatore) è uguale all'unica componente della lista
                    if(moves.get(i).getSteps().get(0).equals(list.get(0))){
                        moves.remove(i);
                        int id = getIDFromCoordinate(list.get(0))[0];
                        for (Charapter charap:charapters)
                            if (charap.getId() == id)
                                charap.setShooting(false);
                    }
            }
        }
        else
        {
            //la prima coordinata della lista rappresenta il giocatore, ogni mossa ha l'ID del giocatore
            int[] data = null;
            data = getIDFromCoordinate(list.get(0));
            moves.add(new Move(owner, data[0], data[1],  list));
            for (Charapter charap:charapters)
                if (charap.getId() == data[0])
                    charap.setShooting(false);
        }          
    }
    public void addAction(ArrayList<Coordinate> list)
    {

        
            
            Coordinate coordinate= list.get(0);
            Coordinate direzione = list.get(1);
            int x = coordinate.getX();
            int y = coordinate.getY();
            int X = direzione.getX();
            int Y = direzione.getY();
            int direction = 0;
            
            if (x == X && Y < y)
                direction = Utils.parseInt(Utils.UP);
            else if (X > x && Y < y)
                direction = Utils.parseInt(Utils.UP_RIGHT); 
            else if (X > x && Y == y)
                direction = Utils.parseInt(Utils.RIGHT); 
            else if (X > x && Y > y)
                direction = Utils.parseInt(Utils.DOWN_RIGHT); 
            else if (X == x && Y > y)
                direction = Utils.parseInt(Utils.DOWN); 
            else if (X < x && Y > y)
                direction = Utils.parseInt(Utils.DOWN_LEFT); 
            else if (X < x && Y == y)
                direction = Utils.parseInt(Utils.LEFT); 
            else if (X < x && Y < y)
                direction = Utils.parseInt(Utils.UP_LEFT); 
            for(Charapter charap:charapters)
                if (charap.getCoordinate().equals(coordinate)){
                    charap.setDirection(direction);
                    charap.setShooting(true);
                }
        
    }
    //ritorna anche il tipo
    private int[] getIDFromCoordinate(Coordinate coord)
    {
        int[] data = new int[2];
        for(Charapter charap:charapters)
            if(charap.getCoordinate().equals(coord))
            {
                data[0]=charap.getId();
                data[1]=Utils.parseInt(charap.getType());
            }
        return data;
    } 
    
    private void moveCharapter(int[] type)
    {
        Move move;
        if (moves.size() > 0)
        {
            for (int i = 0; i< moves.size(); i++){
                move = moves.get(i);
                if (move.getType() == type[0] || move.getType() == type[1] || move.getType() == type[2])
                    if (move.getSteps().size()==1){ // mossa terminata
                        moves.remove(i);
                        i--;
                    }
                    else
                    {
                        for (Charapter charapter:charapters)
                            if (charapter.getId() == move.getID())
                            {
                                Coordinate next = move.getSteps().get(1);
                                if (getIDFromCoordinate(next)[0] != 0){
                                    moves.remove(i);
                                    i--;
                                }
                                else{
                                    charapter.setCoordinate(next);
                                    move.getSteps().remove(0);
                                }
                            }
                    }
            }
        }
 
    }
    
    private void checkArrows()
    {
        int x=0,y=0;
        Arrow arrow;
        for (int i = 0; i < arrows.size(); i++)
        {
            arrow = arrows.get(i);
            x = arrow.getCoordinate().getX();
            y = arrow.getCoordinate().getY();
            if (x < 0 || x > width-1 || y < 0 || y > height-1 || world[x][y] == 12){
                arrows.remove(i);
                i--;
            }
            else
                for (int j = 0; j<charapters.size(); j++)
                {
                    Charapter charap = charapters.get(j);
                    if (charap.getCoordinate().equals(arrow.getCoordinate()))
                    {
                        arrows.remove(i);
                        i--;
                        if (charap.hit(arrow)){
                            charapters.remove(j);
                    }
                    }
                }
        }
    }
    private void shoot()
    {
        for(Charapter charap:charapters)
            if (Utils.parseInt(charap.getType())==2 && charap.isShooting())
                arrows.add(charap.throwArrow(charap.getDirection()));
    }
    
    public void addAttack(ArrayList<Coordinate> list, int owner)
    {
        this.addMoves(list, owner);
        int ID = getIDFromCoordinate(list.get(0))[0];
        for (Charapter charap:charapters)
            if (charap.getId() == ID){
                charap.setShooting(true);
            }
    }
    private void checkAttacks()
    {
        boolean isAttacking = false;
        for (int i = 0; i< charapters.size(); i++)
        {
            isAttacking = false;
            Charapter attacker = charapters.get(i);
            for (int j = 0; j < charapters.size() && attacker.isShooting() && attacker.getType() == Utils.SOLDIER_ID && !isAttacking; j++)
                // metto il secondo controllo per impedire di controllare anche lo stesso charapter
            {
                Charapter defender = charapters.get(j);
                if (attacker.isNear(defender)){   
                    isAttacking = true;
                    if (attacker.attack(defender)){
                        charapters.remove(j);
                        attacker.setShooting(false);
                    }
                }
                    
            }
        }
    }
    public int checkVictory()
    {
        boolean flag1 = false, flag2 = false;
        for (Charapter charap:charapters)
        {
            if (charap.getId() == king1ID) //non c'è il king1, ha vinto il team 2
                flag1 = true;
            if (charap.getId() == king2ID)
                flag2 = true;
        }
        if (!flag1)
            return 1;
        if (!flag2)
            return 2;
        return 0;
    }
    
    public Charapter getCharapterFromCoordinate(Coordinate coord)
    {
        for (Charapter charap:charapters)
            if (charap.getCoordinate().equals(coord))
                return charap;
        return null;
    }
}
