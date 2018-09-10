/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Utils.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Alessandro
 */
public class Core extends Thread{
    private ServerUDP serverUDP;
    private Game game;
    private boolean play;
    private int width, height;
    private ServerTCP player1, player2;
    
    private final String BLUE_TEAM_ID = "1";
    private final String RED_TEAM_ID = "2";
    
    
    
    public Core(Socket client1, Socket client2)
    {
        game = new Game();
        game.loadWorld("res\\World\\");
        serverUDP = new ServerUDP(client1.getInetAddress(), client2.getInetAddress());
        player1 = new ServerTCP(client1);
        player2 = new ServerTCP(client2);
        this.width = game.getWidth();
        this.height = game.getHeight();
    }
    
    private void initConnections()
    {
        //invio al giocatore 1 i suoi player bounds
        player1.sendString(BLUE_TEAM_ID);
        player2.sendString(RED_TEAM_ID);
   
        //invio ad entrambi le dimensioni della mappa
        player1.sendString(""+this.width);
        player1.sendString(""+this.height);
        player2.sendString(""+this.width);
        player2.sendString(""+this.height);
        //terminati i preliminari, faccio partire i server TCP
        player1.start();
        player2.start();
    }
    @Override
    public void run()
    {
        initConnections();
        play = true;
        this.play();
    }
    private void play()
    {
        
        int fps = 5;
        double timePerUpdate = 1000000000 / fps;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int tick = 0;
        
        
        /**
         * While per decidere la frequenza di aggiornamento
         */
        while(play) {
            
            //inizializziamo il timer per ottenere un tick di mezzo secondo
            now = System.nanoTime();
            timer += now - lastTime;
            lastTime = now;
            //esegue questa operazione ogni mezzo secondo
            if(timer >= 200000000) {
                //prendo le mosse dal server TCP
                String move1, move2;
                move1 = player1.getBuffer();
                move2 = player2.getBuffer();
                //assolutamente da rifare ahahahahaah
                ArrayList<Coordinate> tempCoords = new ArrayList<Coordinate>();
                //se non sono nulle, le mando a Game per elaborarle
                if (move1 != null)
                {
                    Object data = Utils.getDataFromString(move1);
                    if (data.getClass() == tempCoords.getClass())
                    {
                        tempCoords = (ArrayList<Coordinate>) data;
                        game.addMoves(tempCoords, 0);
                        player1.clearBuffer();
                    }
                     else
                    {
                        String action = move1;
                        game.addAction(action);
                        player1.clearBuffer();
                    }
                }
                if (move2 != null)
                {
                    Object data = Utils.getDataFromString(move2);
                    if (data.getClass() == tempCoords.getClass())
                    {
                        tempCoords = (ArrayList<Coordinate>) data;
                        game.addMoves(tempCoords, 1);
                        player2.clearBuffer();
                    }
                    else
                    {
                        String action = move2;
                        game.addAction(action);
                        player2.clearBuffer();
                    }
                }
                game.update(tick);
                String map = Utils.mapToString(game);
                serverUDP.send(map);
                timer = 0;
                tick = (tick+1)%8;
            }
            
        }
    }
}
   
