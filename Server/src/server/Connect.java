package server;

import java.io.*;
import java.net.*;

/**
 * Classe che implementa il canale di comunicazone tra Server e Client
 */
public class Connect extends Thread {
    private Socket client;
    private BufferedReader in;
    private PrintStream out;
    
    public Connect(Socket clientSocket) {
        client = clientSocket;
        try {
            // Canale di comunicazione in input
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // Canale di comunicazione in output
            out = new PrintStream(client.getOutputStream(), true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Tentativo chiusura connessione ...");
            try { client.close(); } 
            catch (IOException e) { System.out.println(e.getMessage()); }
            return;
        }
        this.start();
    }
    
    /**
     * Nel metodo run ha luogo la comunicazione client server
     */
    @Override
    public void run() {
        try {
            out.println("Messaggio di prova");
            out.flush();
            
            out.close();
            in.close();
            client.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
