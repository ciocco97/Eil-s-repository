package dev.conn.client;

import dev.conn.PackageDiProva.ClasseDiProva;
import dev.training.Game;
import dev.training.Handeler;
import java.io.*;
import java.net.*;

public class Client {
    
//   PrintStream out;
    private ObjectOutputStream out;
//   BufferedReader in;
    private ObjectInputStream in;
    private Socket socket;
    private ClientUDP clientUDP;
    private Handeler handeler;

    public Client(int PORT, String serverAddress) {
        try {
            socket = new Socket(serverAddress, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            whatToDo();
            out.close();
            in.close();
        } catch (IOException|ClassNotFoundException ex) { 
            System.out.println(ex.getMessage()); 
        }
        
        clientUDP = new ClientUDP();
        
    }
    
    /**
     * Secondo Eil è fatta male
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private void whatToDo() throws IOException, ClassNotFoundException {
        int primo = in.readInt();
        int secondo = in.readInt();
        System.out.println(primo + " - " + secondo);
        
    }
    
    public void setHandeler(Handeler handeler) { this.handeler = handeler; }
    
    
    public static void main(String args[])
    {
        Client client = new Client(7777, "localhost");
    }
}
