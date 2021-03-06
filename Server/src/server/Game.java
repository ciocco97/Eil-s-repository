package server;

import Utils.Utils;




public class Game {
    
    private int width, height;
    private int[][] world, charapters;
    
    
    
    public void loadWorld(String path) {
        String file = Utils.loadFileAsStrig(path);
        String[] token = file.split("\\s+");
        width = Utils.parseInt(token[0]);
        height = Utils.parseInt(token[1]);
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
        charapters = new int[width][height];
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void update()
    {
        
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
    
}
