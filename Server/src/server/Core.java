/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Utils.Coordinate;
import java.util.ArrayList;

/**
 *
 * @author Alessandro
 */
public class Core {
    private ServerUDP serverUDP;
    private Server server;
    private Game game;
    private boolean play;
    private int width, height;
    
    public Core()
    {
        //serverUDP = new ServerUDP();
        game = new Game();
        game.loadWorld("res\\World\\world");
        this.width = game.getWidth();
        this.height = game.getHeight();
        System.out.println(this.mapToString());
        //server = new Server(game.getWidth(), game.getHeight());
    }
    public void initConnection()
    {
        server.startServer();
        play=true;
        
    }
    private void update()
    {
        int fps = 5;
        double timePerUpdate = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int update = 0;
        
        /**
         * While per decidere la frequenza di aggiornamento
         */
        while(play) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                update++;
                delta--;
            }
            if(timer >= 1000000000) {
                // Funzione del ServerUDP
                game.update();
                
                update = 0;
                timer = 0;
            }
        }
    }
    /**
     * metodo che trasforma un dato codificato in stringa in un oggetto utile al gioco
     * @param buffer stringa che rappresenta il dato che deve essere elaborato
     * @return oggetto utile, o lista (movimento di un personaggio) o coordinata (deselezione personaggio)
     */
    private Object getDataFromString(String buffer)
    {   //se è una lista 
        if (buffer.startsWith("["))
        {
            ArrayList list = new ArrayList<Coordinate>();
            //tolgo le parentesi quadre dai buffer
            buffer = buffer.substring(1, buffer.length()-1);
            buffer = " " + buffer;
            //spezzo nei vari componenti che sono divisi da virgole
            String[] splits = buffer.split(",");
            for (String couple : splits)
            {   //spezzo le coordinate divise da spazi
                String[] integers = couple.split(" ");
                int x = Integer.parseInt(integers[1]);
                int y = Integer.parseInt(integers[2]);
                list.add(new Coordinate(x,y));    
            }
            return list;
        
        }
        else
        {
            String core = buffer.substring(1, buffer.length());
            String integers[] = core.split(" ");
            int x = Integer.parseInt(integers[0]);
            int y = Integer.parseInt(integers[1]);
            return new Coordinate(x,y);
        }
    }
    
    private String mapToString()
    {
        int [][] map = game.getMap();
        String buffer = new String();
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++)
            {
                buffer += map[i][j];
                buffer +=" ";
            }
            buffer +="-";
        }
        return buffer;
    }
    
    public static void main (String args[])
    {
        Core core = new Core();
    }
}
