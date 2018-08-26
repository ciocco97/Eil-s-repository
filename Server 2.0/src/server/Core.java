/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Utils.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Alessandro
 */
public class Core extends Thread{
    private ServerUDP serverUDP;
    private Game game;
    private boolean play;
    private int width, height;
    private Connect player1, player2;
    
    public Core(Socket client1, Socket client2)
    {
        game = new Game();
        game.loadWorld("res\\World\\");
        serverUDP = new ServerUDP(client1.getInetAddress(), client2.getInetAddress());
        player1 = new Connect(client1);
        player2 = new Connect(client2);
        this.width = game.getWidth();
        this.height = game.getHeight();
    }
    
    private void initConnections()
    {
        //invio al giocatore 1 i suoi player bounds
        player1.sendString("29");
        player1.sendString("36");
        // lo stesso per il giocatore 2
        player2.sendString("29");
        player2.sendString("36");
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
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int update = 0;
        
        
        /**
         * While per decidere la frequenza di aggiornamento
         */
        while(play) {
            
            //inizializziamo il timer per ottenere un tick di mezzo secondo
            now = System.nanoTime();
            delta += (now - lastTime) / timePerUpdate;
            timer += now - lastTime;
            lastTime = now;
            if(delta >= 1) {
                update++;
                delta--;
            }
            //esegue questa operazione ogni mezzo secondo
            if(timer >= 1000000000) {
                // Funzione del ServerUDP
                game.update();
                String map = Utils.mapToString(game);
                serverUDP.send(map);
                update = 0;
                timer = 0;
                System.out.println(map);
            }
            
        }
    }
}
   
