package server;

import java.io.IOException;
import java.net.*;

public class Server extends Thread{
    private ServerSocket server;
    private final ServerUDP serverUDP;
    
    public static final int PORT = 7777;
    
    /**
     * Creazione di una server socket in ascolto sulla porta PORT
     * Successivamente istanza classe ServerUDPP
     */
    public Server() {
        try { server = new ServerSocket(PORT); } 
        catch (IOException ex) { System.out.println(ex.getMessage()); }
        System.out.println("Il server è in attesa sulla porta " + PORT);
        this.start();
        
        serverUDP = new ServerUDP();
    }
    
    /**
     * Funzione che crea un canale di comunicazione per ogni richiesta dai 
     * client
     */
    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("In attesa di connessione ...");
                Socket client = server.accept();
                System.out.println("Connessione accettata da: " + client.getInetAddress());
                Connect connect = new Connect(client);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        Server server = new Server();
    }
    
}
