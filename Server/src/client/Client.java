package client;

import PackageDiProva.ClasseDiProva;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

public class Client {
    
    public static void main(String[] args) {
        PrintStream out;
//        BufferedReader in;
        ObjectInputStream in;
        Socket socket;
        String serverAddress = "localhost";
        
        try {
            socket = new Socket(serverAddress, Server.PORT);
            out = new PrintStream(socket.getOutputStream(), true);
            in = new ObjectInputStream(socket.getInputStream());
            ClasseDiProva classeDiProva = (ClasseDiProva) in.readObject();
            System.out.println("Messaggio ricevuto: " + classeDiProva.getMessaggio());
            out.close();
            in.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
