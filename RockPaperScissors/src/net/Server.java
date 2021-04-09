package net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ExecutorService pool;
    private ServerSocket server;
    private int IDCount;


    public Server() {
        try {
            server = new ServerSocket(8080);
            pool = Executors.newFixedThreadPool(10);
            IDCount = 1000;
            System.out.println("[SERVER] Started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            acceptConnections();
        }
    }


    private void acceptConnections() {
        try {
            Socket socket = server.accept();
            System.out.println("[SERVER] New connection from " + socket.getInetAddress() + ":" + socket.getLocalPort());
            Connection clientThread = new Connection(socket, IDCount++);
            pool.execute(clientThread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
