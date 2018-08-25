package server;

import PackageDiProva.ClasseDiProva;
import PackageDiProva.Coordinate;
import java.io.*;
import java.net.*;

public class ServerUDP extends Thread{
    boolean play;
    private DatagramSocket socket;
    
    // Costanti
    public static int UDPORT = 8888;
    
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
                invia();
                update = 0;
                timer = 0;
            }
        }
        
    }
    
    public void invia() { 
        ClasseDiProva classeDiProva = new ClasseDiProva(new Coordinate(0, 0));
        try {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(classeDiProva);
            oo.close();
            
            byte[] serializedMessage = bStream.toByteArray();
            
            InetAddress ia = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, ia, UDPORT + 1);
            socket.send(packet);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isPlay() { return play; }

    public void setPlay(boolean play) { this.play = play; }
    
}
