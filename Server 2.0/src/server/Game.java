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
    
    private int width, height;
    private int[][] world;
    private LinkedList<Charapter> charapters;
    private ArrayList<Move> moves;
    private LinkedList<Arrow> arrows;
    private int maxTeamID;
    
    
    public Game()
    {
        charapters = new LinkedList();
        moves = new ArrayList();
    }
    public void loadWorld(String path) {
        String fileWorld = Utils.loadFileAsStrig(path + "world");
        String fileCharapters = Utils.loadFileAsStrig(path + "charapters");
        String[] token = fileWorld.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        charapters = new LinkedList<>();
        //spawnX = Utils.parseInt(token[2]);
        //spawnY = Utils.parseInt(token[3]);
        
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
        
            token = fileCharapters.split("\\s+");
            width = Utils.parseInt(token[0]);
            height = Utils.parseInt(token[1]);
            maxTeamID = Utils.parseInt(token[2])/2;
             for(int y = 0; y < height; y++) 
                for(int x = 0; x < width; x++){
                    int ID = Utils.parseInt(token[x + (y * width) + 4]);
                    //un if un po strano spero si capisca, in caso è per capire di chi è quel particolare personaggio
                    
                    Random rand = new Random();
                    int owner = ID<maxTeamID?0:1;
                    int king1ID = rand.nextInt(maxTeamID);
                    int king2ID = rand.nextInt(maxTeamID) + maxTeamID;
                    if (ID != 0){
                        if (ID == king1ID)
                        charapters.add(new King (owner, ID, new Coordinate(x,y)));
                        else    
                        {
                        if (rand.nextBoolean())
                            charapters.add(new Archer(owner, ID, new Coordinate(x,y)));
                        else
                            charapters.add(new Soldier(owner, ID, new Coordinate(x,y)));
                        }
                    }  
                }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void update(int tick)
    {
        boolean toDo = true;
        //entra l'update delle mosse, ogni 2 tick   
        if (moves.size() > 0 && tick%2 == 0)
        {
            //scorro tutte le mosse ancora da eseguire
            for (int i=0; i<moves.size(); i++)
            {
                toDo=true;
                Move mossa = moves.get(i);
                Charapter charapter = null;
                int ID = mossa.getID();
                for(Charapter charap:charapters){
                        if (charap.getId()==ID)
                            charapter = charap;
                }
                if (charapter.getSpeed()==1 && tick%4!=1)
                    toDo=false;
                Coordinate nextCoordinate = null;
                //prendo le future coordinate e tolgo quelle in cui andrà il giocatore
                if (mossa.getSteps().size()==1){
                    moves.remove(i);
                    break;
                }
                else
                {
                    nextCoordinate = mossa.getSteps().get(1);
                    //scorro tutti i giocatori per vedere se non c'è nessuno in quelle coordinate
                    for (Charapter charap:charapters){
                        if (charap.getCoordinate().equals(nextCoordinate))
                        {
                            //significa che c'è qualcuno lungo il tragitto, mi fermo
                            moves.remove(i);
                            toDo = false;
                        }           
                    }
                }  
                if(toDo){
                    charapter.setCoordinate(nextCoordinate);
                    mossa.getSteps().remove(0);
                }
            }
        }
    }
    public int[][] getMap()
    {
        int[][] map = new int[width][height];
        //riempio con il mondo
        for (int i=0; i<width; i++)
            for (int j=0; j<height; j++)
                map[i][j] = world[i][j];
        
        //inserisco gli ID dei vari personaggi
        for (Charapter charap:charapters)
        {
            int x=charap.getCoordinate().getX();
            int y=charap.getCoordinate().getY();
            int ID = Utils.getIdFromCharapter(charap, maxTeamID);
            map[x][y] = ID;
        }
            
        return map;
        
    }
    
    public void addMoves(ArrayList<Coordinate> list, int owner)
    {   //se è grande solo 1 significa che quel personaggio deve bloccarsi
        if (list.size() == 1)
        {
            //scorro tutte le mosse per vedere quale ha quella posizione come attuale
            for (int i=0; i<moves.size(); i++)
            {   //se non è il proprietario della mossa, non può annullarla
                if (moves.get(i).getOwner() == owner)
                {
                    //se la prima coordinata della mossa (il giocatore) è uguale all'unica componente della lista
                    if(moves.get(i).getSteps().get(0).equals(list.get(0)))
                        moves.remove(i);
                }
            }
        }
        else
        {
            //la prima coordinata della lista rappresenta il giocatore, ogni mossa ha l'ID del giocatore
            int[] data = null;
            data = getIDFromCoordinate(list.get(0));
            moves.add(new Move(owner, data[0], data[1],  list));
        }
        
                
    }
    
    private int[] getIDFromCoordinate(Coordinate coord)
    {
        int[] data = new int[2];
        for(Charapter charap:charapters)
            if(charap.getCoordinate().equals(coord))
            {
                data[0]=charap.getId();
                data[1]=charap.getType();
            }
        return data;
    } 
    
    
}
