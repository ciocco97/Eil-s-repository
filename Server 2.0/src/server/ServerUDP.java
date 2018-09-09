package server;

import java.io.*;
import java.net.*;

public class ServerUDP extends Thread{
    boolean play;
    private DatagramSocket socket;
    private InetAddress ia1;
    private InetAddress ia2;
    
    // Costanti
    public static int UDPORT = 4444;
    
    public ServerUDP(InetAddress ia1, InetAddress ia2) { 
        play = true; 
        this.ia1 = ia1;
        this.ia2 = ia2;
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) { 
            System.out.println("Errore creazione serverUDP\n" + ex.getMessage()); 
        }
        this.start(); 
    }
    
    @Override
    public void run() {

    }
    
    public void send(String data) { 
        try {
            
            byte[] dataBytes = data.getBytes();
            DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, ia1, UDPORT);
            DatagramPacket packet2 = new DatagramPacket(dataBytes, dataBytes.length, ia2, UDPORT);
            socket.send(packet);
            socket.send(packet2);
            System.out.println("mando la mappa");
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isPlay() { return play; }

    public void setPlay(boolean play) { this.play = play; }
    
}
