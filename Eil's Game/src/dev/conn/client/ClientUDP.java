package dev.conn.client;

import dev.conn.PackageDiProva.ClasseDiProva;
import dev.conn.server.ServerUDP;
import java.io.*;
import java.net.*;

public class ClientUDP extends Thread{
    private DatagramSocket socket;
    private boolean run;
    
    // Costanti
    private final int BUFF_LENGHT = 2048;
    
    public ClientUDP() {
        try { socket = new DatagramSocket(ServerUDP.UDPORT + 1); } 
        catch (SocketException ex) { System.out.println(ex.getMessage()); }
        this.start();
    }
    
    @Override
    public void run() {
        ClasseDiProva classeDiProva = new ClasseDiProva("");
        byte[] buffer = new byte[BUFF_LENGHT];
        DatagramPacket packet = new DatagramPacket(buffer, BUFF_LENGHT);
        while(true) {
            try { 
            socket.receive(packet);
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            classeDiProva = (ClasseDiProva) iStream.readObject();
            iStream.close();
            } catch (IOException|ClassNotFoundException ex) { System.out.println(ex.getMessage()); }
            System.out.println("Messaggio ricevuto: " + classeDiProva.getMessaggio());
        }
    }
    
    public boolean isRun() { return run; }
    
    public void setRun(boolean run) { this.run = run; }
    
}
