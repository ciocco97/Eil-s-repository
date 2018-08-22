package dev.conn.client;

import dev.conn.PackageDiProva.ClasseDiProva;
import dev.conn.server.Server;
import java.io.*;
import java.net.*;

public class Client {
    
//   PrintStream out;
    private ObjectOutputStream out;
//   BufferedReader in;
    private ObjectInputStream in;
    private Socket socket;
    private ClientUDP clientUDP;
    
    // Costanti
    private final String serverAddress = "localhost";

    public Client() {
        try {
            socket = new Socket(serverAddress, Server.PORT);
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
    
    private void whatToDo() throws IOException, ClassNotFoundException {
        ClasseDiProva classeDiProva = (ClasseDiProva) in.readObject();
        System.out.println("Messaggio ricevuto: " + classeDiProva.getMessaggio());
        classeDiProva = new ClasseDiProva("Questa è una risposta");
        out.writeObject(classeDiProva);
        out.flush();
    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }
    
}
