package dev.conn.server;

import dev.conn.PackageDiProva.ClasseDiProva;
import java.io.*;
import java.net.*;

/**
 * Classe che implementa il canale di comunicazone tra Server e Client
 */
public class Connect extends Thread {
    private Socket client;
//    private PrintStream out;
    private ObjectOutputStream out;
//    private BufferedReader in;
    private ObjectInputStream in;
    
    public Connect(Socket clientSocket) {
        client = clientSocket;
        try {
            // Canale di comunicazione in output
//            out = new PrintStream(client.getOutputStream(), true);
            out = new ObjectOutputStream(client.getOutputStream());
            // Canale di comunicazione in input
            in = new ObjectInputStream(client.getInputStream());
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
        ClasseDiProva classeDiProva = new ClasseDiProva("Questo è un altro messaggio");
        try {
            out.writeObject(classeDiProva);
            out.flush();
            classeDiProva = (ClasseDiProva) in.readObject();
            System.out.println("Risposta: " + classeDiProva.getMessaggio());
            
            out.close();
            in.close();
            client.close();
        } catch (IOException|ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
