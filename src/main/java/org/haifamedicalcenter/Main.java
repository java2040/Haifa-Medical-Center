package org.haifamedicalcenter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        final int PORT = 80;  // Common HTTP port, later should be changed to 443, an HTTPS port.

        // To work with IPv4, use the following line.
        //try ( ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("0.0.0.0")) ) {

        // To work with IPv6 Interfaces use this "try with resources" line.
        // (::) <== this means that the server socket binds to all IPv6 available interfaces.
        try ( ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("::")) ) {
            System.out.println("Server started on port " + PORT);
            while (true) {

                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread1 = new Thread(clientHandler);
                thread1.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
