package server;

import PackageDiProva.ClasseDiProva;
import PackageDiProva.Coordinate;
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
//  per decidere che client ha quale range di personaggi  
    private boolean firstTime;
    
    public Connect(Socket clientSocket, boolean firstTime) {
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
        try {
            if (firstTime) 
            {
                out.writeInt(29);
                out.writeInt(100);
            }
            else
            {
                out.writeInt(101);
                out.writeInt(200);
            }
            out.flush();
            System.out.println("Risposta: " + classeDiProva.getMessaggio());
            
            out.close();
            in.close();
            client.close();
        } catch (IOException|ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
