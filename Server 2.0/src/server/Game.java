package server;

import Models.Arcere;
import Models.Charapter;
import Models.Gueriero;
import Utils.Coordinate;
import Utils.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;




public class Game {
    
    private int width, height;
    private int[][] world, charapters;
    private LinkedList<Charapter> charaptersList;
    private LinkedList<ArrayList<Coordinate>> moves;
    
    
    public Game()
    {
        charaptersList = new LinkedList();
        moves = new LinkedList();
        
    }
    public void loadWorld(String path) {
        String fileWorld = Utils.loadFileAsStrig(path + "world");
        String fileCharapters = Utils.loadFileAsStrig(path + "charapters");
        String[] token = fileWorld.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
        charaptersList = new LinkedList<>();
        //spawnX = Utils.parseInt(token[2]);
        //spawnY = Utils.parseInt(token[3]);
        
        world = new int[width][height];
        charapters = new int[width][height];
        
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

             for(int y = 0; y < height; y++) 
                for(int x = 0; x < width; x++){
                    int ID = Utils.parseInt(token[x + (y * width) + 4]);
                    charapters[x][y] = ID;
                    if (new Random().nextBoolean())
                        charaptersList.add(new Arcere(ID));
                    else
                        charaptersList.add(new Gueriero(ID));
                    
                }
 
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void update()
    {
        //se c'è qualcosa da fare
        if (moves.size() > 0)
        {
            System.out.println("c'è qualcosa");
            //scorro tutte le mosse ancora da eseguire
            for (int i = 0; i<moves.size(); i++)
            {
                Coordinate current, next;
                if (moves.get(i).size() != 1)
                {
                    System.out.println("muovo");
                    current = moves.get(i).get(0);
                    next = moves.get(i).get(1);
                    charapters[next.getX()][next.getY()] = charapters[current.getX()][current.getY()];
                    charapters[current.getX()][current.getY()]=0;
                    moves.get(i).remove(0);
                }
                else
                    moves.remove(i);
            }
        }
    }
    public int[][] getMap()
    {
        int[][] map = new int[width][height];
        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
                if (charapters[i][j]!=0)
                    map[i][j]=charapters[i][j];
                else
                    map[i][j]=world[i][j];
            }
        }
        return map;
        
    }
    
    public void addMoves(ArrayList<Coordinate> list)
    {
        moves.add(list);
    }
    
}
