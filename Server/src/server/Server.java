package server;

import java.io.IOException;
import java.net.*;

public class Server extends Thread{
    private ServerSocket server;
    private boolean running, firstTime;
    
    private static InetAddress ia;
    
    public static final int PORT = 7777;
    
    /**
     * Creazione di una server socket in ascolto sulla porta PORT
     * Successivamente istanza classe ServerUDPP
     */
    public Server() {
        try { server = new ServerSocket(PORT); } 
        catch (IOException ex) { System.out.println(ex.getMessage()); }
    }
    
    public void startServer() {
        this.start();
        running = true;
    }
    
    /**
     * Funzione che crea un canale di comunicazione per ogni richiesta dai 
     * client
     */
    @Override
    public void run() {
        while(running) {
            try {
                System.out.println("In attesa di connessione ...");
                Socket client = server.accept();
                System.out.println("Connessione accettata da: " + client.getInetAddress());
                ia = client.getInetAddress();
                Connect connect = new Connect(client, firstTime);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
    
    public static void main (String args[])
    {
        Server server = new Server();
        server.start();
    }
    
}
