/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author aless
 */
public class Launcher {
    
    public static void main(String args[])
    {
        Core core = new Core();
        core.initConnection();
        core.play();
        
    }
}
