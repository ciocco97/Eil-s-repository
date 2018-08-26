/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Alessandro
 */
public class UDP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket server = new DatagramSocket();
        byte[] dataBytes = "tua nonna con le mutandine viola".getBytes();
        System.out.println(dataBytes.length);
        DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, InetAddress.getByName("25.82.137.22"), 8888);
        server.send(packet);
        String string = new String(packet.getData());
        System.out.println(string);
        server.close();
        System.out.println("fatto");
    }
    
}
