package dev.conn.client;

import java.io.IOException;
import java.net.*;

public class ClientUDP extends Thread{
    private DatagramSocket socket;
    private boolean run;
    
    /**
     * Praticamente variabile più importandte della classe
     */
    public static String map;
    
    // Costanti
    private final int BUFF_LENGHT = 2048;
    private final int PORT = 4444;
    
    public ClientUDP() {
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException ex) {
            System.out.println(ex.getMessage());
        }
        run = true;
    }
    
    @Override
    public void run() {
        byte[] buffer = new byte[BUFF_LENGHT];
        DatagramPacket packet = new DatagramPacket(buffer, BUFF_LENGHT);
        while(run) {
            try { 
                socket.receive(packet);
                map = new String(packet.getData());
                System.out.println("FROM SERVER:" + map);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } 
        }
    }
    
    public boolean isRun() { return run; }
    
    public void setRun(boolean run) { this.run = run; }
    
}
