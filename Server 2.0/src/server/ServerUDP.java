package server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void sendSingle(String data, int index)
    {
        try {
            InetAddress ia;
            if (index == 1)
                ia = ia1;
            else
                ia = ia2;
            byte[] dataBytes = data.getBytes();
            DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, ia, UDPORT);
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public boolean isPlay() { return play; }

    public void setPlay(boolean play) { this.play = play; }
    
}
