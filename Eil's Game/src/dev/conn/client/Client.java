package dev.conn.client;

import dev.training.Game;
import dev.training.Handeler;
import dev.training.Utils;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Thread {
    
    /**
     * Valori per il settaggio iniziale del gioco:
     * lower and upper bound servono per capire che omini visualizzare come "propri"
     * width e height sono rappresentano le dimensioni della matrice dei tile
     */
    private int lowerBound, upperBound, width, height;
    
    // Elementi della connessione
    private PrintStream printOut;
    private ObjectOutputStream out;
    private BufferedReader printIn;
    private ObjectInputStream in;
    private Socket socket;
    private String serverAddress;
    
    private Handeler handeler;
    
    private final int PORT = 7777;

    public Client(String serverAddress) {
        this.serverAddress = serverAddress;
        try {
            socket = new Socket(serverAddress, PORT);
        } catch (IOException ex) { 
            System.out.println(ex.getMessage() + "\n"); 
        }
        
    }
    
    public Client reTry() {
        Client c;
        System.out.println("Tentativo di riconnessione");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        c = new Client(serverAddress);
        return c;
    }
    
    public void init() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            primaApertura();
        } catch (IOException|ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void primaApertura() throws IOException, ClassNotFoundException {
        
        lowerBound = Utils.parseInt(in.readUTF());
        upperBound = Utils.parseInt(in.readUTF());
        width = Utils.parseInt(in.readUTF());
        height = Utils.parseInt(in.readUTF());
        System.out.println("Range di omini: " + lowerBound + " - " + upperBound);
        System.out.println("Dimensioni griglia mondo: " + width + " - " + height);
        
    }
    
    public void inviaPath(ArrayList pathSteps) {
        try {
            out.writeUTF(pathSteps.toString());
            out.flush();
            System.out.println("Inviato");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void setHandeler(Handeler handeler) { this.handeler = handeler; }
    
    public int getLowerBound() { return lowerBound; }

    public int getUpperBound() { return upperBound; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
    
    public Socket getSocket() { return socket; }    
    
//    public static void main(String args[])
//    {
//        Client client = new Client("192.168.1.119");
//    }

}
