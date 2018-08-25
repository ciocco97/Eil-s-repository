package dev.conn.client;

import dev.conn.PackageDiProva.ClasseDiProva;
import dev.training.worlds.Coordinate;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientUDP extends Thread{
    private DatagramSocket socket;
    private boolean run;
    
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
        this.start();
    }
    
    @Override
    public void run() {
        ClasseDiProva classeDiProva = new ClasseDiProva(new Coordinate(0, 0));
        byte[] buffer = new byte[BUFF_LENGHT];
        DatagramPacket packet = new DatagramPacket(buffer, BUFF_LENGHT);
        while(run) {
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
