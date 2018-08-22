package client;

import java.io.*;
import java.net.*;
import server.Server;

public class Client {
    
    public static void main(String[] args) {
        BufferedReader in;
        PrintStream out;
        Socket socket;
        String serverAddress = "localhost";
        
        try {
            socket = new Socket(serverAddress, Server.PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);
            String message = in.readLine();
            System.out.println("Messaggio ricevuto: " + message);
            out.close();
            in.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
