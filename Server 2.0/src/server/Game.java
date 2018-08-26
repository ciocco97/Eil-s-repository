package server;

import PackageDiProva.Charapter;
import Utils.Utils;
import java.util.LinkedList;




public class Game {
    
    private int width, height;
    private int[][] world, charapters;
    private LinkedList<Charapter> charaptersList;
    
    
    
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
        
//        token = fileCharapters.split("\\s+");
//        width = Utils.parseInt(token[0]);
//        height = Utils.parseInt(token[1]);
//        
//         for(int y = 0; y < height; y++) 
//            for(int x = 0; x < width; x++){
//                charapters[x][y] = Utils.parseInt(token[x + (y * width) + 4]);
//                charaptersList.add(new Charapter(x,y,100));
//            }
 
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void update()
    {
//        for (int i=0; i<width-1; i++)
//            for (int j=0; j<height-1; j++)
//            {
//                if (charapters[i][j]!=0)
//                {
//                    charapters[(i+1)][j]=charapters[i][j];
//                    charapters[i][j]=0;
//                }
//            }
    }
    public int[][] getMap()
    {
        int[][] map = new int[width][height];
        for (int i=0; i<width; i++)
        {
            for (int j=0; j<height; j++)
            {
//                if (charapters[i][j]!=0)
//                    map[i][j]=charapters[i][j];
//                else
                    map[i][j]=world[i][j];
            }
        }
        return map;
        
    }
    
}
