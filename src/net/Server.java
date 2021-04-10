package net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private static ArrayList<Connection> clients;
    private ExecutorService pool;
    private Connection.Update update;
    private int IDCounter;
    private ServerData data;

    public Server(int port, ServerData data, Connection.Update updateMessages) {
        try {
            this.data = data;
            serverSocket = new ServerSocket(port);
            clients = new ArrayList<>();
            pool = Executors.newFixedThreadPool(10);
            IDCounter = 1000;
            this.update = updateMessages;
            System.out.println("[SERVER] Successfully started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] New connection from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                Connection client = new Connection(socket, IDCounter++, data.getMessagesIn(), update);
                data.getClients().add(client);
                pool.execute(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Connection> getClients() { return clients; }
}
