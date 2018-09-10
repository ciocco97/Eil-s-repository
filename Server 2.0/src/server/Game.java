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
    private int[][] world, ground;
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
        String fileWorld = Utils.loadFileAsStrig(path + "world");
        String fileCharapters = Utils.loadFileAsStrig(path + "charapters");
        String[] token = fileWorld.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        charapters = new LinkedList<>();
        
        //spawnX = Utils.parseInt(token[2]);
        //spawnY = Utils.parseInt(token[3]);
        startTime = System.currentTimeMillis();
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
            int king1ID = rand.nextInt(maxTeamID);
            int king2ID = rand.nextInt(maxTeamID) + maxTeamID;
             for(int y = 0; y < height; y++) 
                for(int x = 0; x < width; x++){
                    int ID = Utils.parseInt(token[x + (y * width) + 4]);
                    //un if un po strano spero si capisca, in caso è per capire di chi è quel particolare personaggio
                   
                    int owner = ID<maxTeamID?0:1;
                    if (ID != 0){
                        if (ID == king1ID || ID == king2ID)
                            charapters.add(new King (owner, ID, new Coordinate(x,y)));
                        else
                        {
                        if (rand.nextBoolean()){
                            Archer archer = new Archer(owner, ID, new Coordinate(x,y));
                            archer.setDirection(4);
                            archer.setShooting(true);
                            charapters.add(archer);
                        }
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
        
        long deltaTime = (System.currentTimeMillis() - startTime) / 1000;
        if (deltaTime > TIME_OF_GAME_SETUP && !gameTrigger){ // tempo in secondi
            for (int i = 0; i<height; i++)
                world[(width/2)][i] = ground[(width/2)][i];
                gameTrigger = true;
                System.out.println("cambiato il mondo");
        }
        if (tick==1)
            {moveCharapter(new int[]{2,0,0});
                     }
        else if (tick==3)
            {moveCharapter(new int[]{2,1,0}); shoot();}
        else if (tick==5)
            {moveCharapter(new int[]{2,0,0}); }
        else if (tick==7)
            {moveCharapter(new int[]{2,1,3}); shoot();}
        for (Arrow arrow:arrows)
            arrow.tick();
        checkArrows();
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
                    if(moves.get(i).getSteps().get(0).equals(list.get(0)))
                        moves.remove(i);
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
    public void addAction(String data)
    {
        if (data.startsWith("f")) // comando per lanciare la freccia
        {
            int direction = Integer.parseInt(data.charAt(1)+"");
            int x = Integer.parseInt(data.charAt(3)+"");
            int y = Integer.parseInt(data.charAt(5)+"");
            Coordinate coord = new Coordinate(x,y);
            int ID = getIDFromCoordinate(coord)[0];
            for (Charapter charap:charapters)
                if (charap.getId() == ID && Utils.parseInt(charap.getType()) == 2)
                {
                    charap.setShooting(true);
                    charap.setDirection(direction);
                }
        }
    }
    
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
                    if (move.getSteps().size()==1) // mossa terminata
                        moves.remove(i);
                    else
                    {
                        for (Charapter charapter:charapters)
                            if (charapter.getId() == move.getID())
                            {
                                Coordinate next = move.getSteps().get(1);
                                if (getIDFromCoordinate(next)[0] != 0){
                                    moves.remove(i);
                                }
                                else{
                                    charapter.setShooting(false);
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
        boolean remuved = false;
        for (int i = 0; i < arrows.size(); i++)
        {
            remuved = false;
            for (int j = 0; j<charapters.size() && !remuved; j++)
            {
                Charapter charap = charapters.get(j);
                Arrow arrow = arrows.get(i);
                int x = arrow.getCoordinate().getX();
                int y = arrow.getCoordinate().getY();
                if (x < 0 || x >= this.width || y < 0 || y >= this.height){
                    arrows.remove(i);
                    remuved = true;
                    break;
                }
                int ground = world[x][y];
                if (ground == 12){
                    arrows.remove(i);
                    remuved = true;
                    break;
                }
                if (charap.getCoordinate().equals(arrow.getCoordinate()))
                {
                    arrows.remove(i);
                    remuved = true;
                    if (charap.hit(arrow)){
                        charapters.remove(j);
                        System.out.println("sommuort");
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
}
