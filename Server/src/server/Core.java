/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Alessandro
 */
public class Core {
    private ServerUDP serverUDP;
    private Server server;
    private Game game;
    
    public Core()
    {
        serverUDP = new ServerUDP();
        server = new Server();
        game = new Game();
        game.loadWorld("res\\worlds\\world");
    }
    public initCore()
    {
        
    }
    private update()
    {
        
    }
}
