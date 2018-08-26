package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server extends Thread{
    private ServerSocket server;
    private boolean running, firstTime;
    private int width, height;
    private Core core;
    
    private Connect connect;
    
    private ObjectOutputStream out;
//    private BufferedReader in;
    private ObjectInputStream in;
    
    private static InetAddress ia;
    
    public static final int PORT = 7777;
    
    /**
     * Creazione di una server socket in ascolto sulla porta PORT
     * Successivamente istanza classe ServerUDPP
     */
    public Server(int width, int height, Core core) {
        this.width = width;
        this.height = height;
        firstTime = true;
        try { server = new ServerSocket(PORT); } 
        catch (IOException ex) { System.out.println(ex.getMessage()); }
    }
    
    public void startServer() {
        this.start();
        running = true;
    }
    
    /**
     * Funzione che crea un canale di comunicazione per ogni richiesta dai 
     * client
     */
    @Override
    public void run() {
        while(running) {
            try {
                System.out.println("In attesa di connessione ...");
                Socket client = server.accept();
                ia = client.getInetAddress();
                core.initUDP(ia);
                System.out.println("Connessione accettata da: " + ia);
                out = new ObjectOutputStream(client.getOutputStream());
                // prima connessione, si passano gli upper e lower bound 
                if (firstTime) 
                {
                    out.writeUTF(""+ 29);
                    out.writeUTF("" + 36);
                    firstTime = !firstTime;
                }
                else
                {
                    out.writeUTF(""+ 29);
                    out.writeUTF("" + 36);
                    firstTime = !firstTime;
                }
                out.writeUTF(""+ width);
                out.writeUTF("" + height);
                out.flush();
                System.out.println("Risposta: ho mandato le informazion iniziali al client di indirizzo" + client.getInetAddress());
                
                
                
                // passo al client le informazioni necessarie per la creazione del gioco
                
                connect = new Connect(client);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void setRunning(boolean running) {
        this.running = running;
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
