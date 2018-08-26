package server;

import java.io.*;
import java.net.*;

public class ServerUDP extends Thread{
    boolean play;
    private DatagramSocket socket;
    private InetAddress ia;
    
    // Costanti
    public static int UDPORT = 4444;
    
    public ServerUDP() { 
        play = true; 
        try {
            socket = new DatagramSocket(UDPORT);
        } catch (SocketException ex) { 
            System.out.println("Errore creazione serverUDP\n" + ex.getMessage()); 
        }
        this.start(); 
    }
    
    @Override
    public void run() {
//        int fps = 5;
//        double timePerUpdate = 1000000000 / fps;
//        double delta = 0;
//        long now;
//        long lastTime = System.nanoTime();
//        long timer = 0;
//        int update = 0;
//        
//        /**
//         * While per decidere la frequenza di aggiornamento
//         */
//        while(play) {
//            now = System.nanoTime();
//            delta += (now - lastTime) / timePerUpdate;
//            timer += now - lastTime;
//            lastTime = now;
//            if(delta >= 1) {
//                update++;
//                delta--;
//            }
//            if(timer >= 1000000000) {
//                // Funzione del ServerUDP
//                invia();
//                update = 0;
//                timer = 0;
//            }
//        }
        
    }
    
    public void send(String data) { 
        try {
            
            byte[] dataBytes = data.getBytes();
            DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, InetAddress.getByName("25.82.137.22"), UDPORT);
            socket.send(packet);
            System.out.println("invio UDP effettuato");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isPlay() { return play; }

    public void setPlay(boolean play) { this.play = play; }
    
}
