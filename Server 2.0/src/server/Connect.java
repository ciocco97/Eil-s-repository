package server;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che implementa il canale di comunicazone tra Server e Client
 */
public class Connect extends Thread {

//    private PrintStream out;
    private ObjectOutputStream out;
//    private BufferedReader in;
    private ObjectInputStream in;
    
    private String buffer;
    
    public Connect(Socket client) {
        try {

        this.out = new ObjectOutputStream (client.getOutputStream());
        this.in = new ObjectInputStream (client.getInputStream());
        
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
            // Canale di comunicazione in input
    }
    
    /**
     * Nel metodo run ha luogo la comunicazione client server
     */
    @Override
    public void run() {
        try {
            while(true)
            {
               System.out.println("attesa di oggetti");
               String buffer = in.readUTF();
               System.out.println("ricevuto: " + buffer);
               this.buffer = buffer;
            }
        } catch (Exception ex ) {
            System.out.println(ex.getMessage());
        }
    }

    public String getBuffer() {
        return buffer;
    }
    public void sendString(String data)
    {
        try {
            out.writeUTF(data);
            out.flush();
            return;
        } catch (IOException ex) {
            System.out.println("Errore nella funzione sendString del tipo:" + ex.getMessage());
        }
    }
    public void clearBuffer()
    {
        this.buffer = null;
    }
    
    
    
    
}
