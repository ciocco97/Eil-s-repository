package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.*;

public class Server extends Thread{
    private ServerSocket server;
    private boolean running, firstTime;
    private int width, height;
    private Game game;
    
    
    private PrintStream out1;
    private PrintStream out2;
    
    private BufferedReader in1;
    private BufferedReader in2;
    
    private static InetAddress ia1;
    private static InetAddress ia2;
    
    public static final int PORT = 7777;
    
    /**
     * Server TCP di inizializzazione del programma, una volta stabilita la connessione e conosciuti
     * gli indirizzi IP dei client, viene aperto il Thread di core che gestirà la partita
     * 
     */
    public Server() {
        try { server = new ServerSocket(PORT); 
            System.out.println("---EIL'S GAME SERVER, VERSION 2.0--- ");
        
        } 
        catch (IOException ex) { System.out.println(ex.getMessage()); }
    }
    
    public void startServer() {
        this.start();
        running = true;
        System.out.println("Server avviato sulla porta: " + PORT);
    }
    
    /**
     * Funzione che crea un canale di comunicazione per ogni richiesta dai 
     * client
     */
    @Override
    public void run() {
        while(running) {
            try {
                //accetto i due giocatori e creo la partita
                System.out.println("Attesa giocatore 1");
                Socket client = server.accept();
                System.out.println("Giocatore 1 connesso con indirizzo IP:" + client.getInetAddress());
                System.out.println("Attesa giocatore 2");
                Socket client2 = server.accept();
                System.out.println("Giocatore 2 connesso con indirizzo IP:" + client.getInetAddress());
                Core core = new Core(client, client2);
                core.start();
                System.out.println("Partita creata, ritorno in attesa di giocatori");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void stop(boolean running) {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }
    
//    public static void main (String args[])
//    {
//        Server server = new Server(1400, 800);
//        server.startServer();
//    }
    
}
