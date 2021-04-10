package net;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       Accepts incoming connections and creates new threads for each
       connection. Takes in the class ServerData which contains a
       the deque and list of clients so the constructor isn't super
       long. The server implementation would require a method to
       handle incoming messages and an instance of ServerData to
       get all the clients and messages from.
     -----------------------------------------------------------------*/


    // ServerSocket to allow multiple connections
    private ServerSocket serverSocket;
    // ExecutorService to limit the amount of active threads
    private ExecutorService pool;
    // Update method to instantiate with Connection
    private Connection.Update update;
    // int to give each client a unique ID
    private int IDCounter;
    // data from the server
    private ServerData data;


    // constructor
    public Server(int port, ServerData data, Connection.Update updateMessages) {
        try {
            this.data = data;
            this.serverSocket = new ServerSocket(port);
            this.pool = Executors.newFixedThreadPool(10);
            this.IDCounter = 1000;
            this.update = updateMessages;
            System.out.println("[SERVER] Successfully started");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loop and accept connections
    @Override
    public void run() {
        // infinite loop
        while (true) {
            try {
                // accept incoming connections
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] New connection from " + socket.getInetAddress() + ":" + socket.getLocalPort());

                // create new connection to client
                Connection client = new Connection(socket, IDCounter++, data.getMessagesIn(), update);

                // add client to list of clients
                data.getClients().add(client);

                // start thread for new client
                pool.execute(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
