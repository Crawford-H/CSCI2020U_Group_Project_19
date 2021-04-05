package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private static ArrayList<ClientHandler> clients;
    private static ExecutorService pool;
    private final ServerSocket server;
    private static boolean running;

    public Server() throws IOException {
        pool = Executors.newFixedThreadPool(10);
        this.server = new ServerSocket(8080);
        clients = new ArrayList<>();
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                System.out.println("[SERVER] Listening for connection");
                Socket client = server.accept();
                System.out.println("[SERVER] has connected to client " + client.getInetAddress().toString());
                ClientHandler clientThread = new ClientHandler(client);
                clients.add(clientThread);
                pool.execute(clientThread);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        running = false;
        System.exit(0);
    }

    public boolean                  isRunning()  { return running; }
    public ArrayList<ClientHandler> getClients() { return clients; }
}
