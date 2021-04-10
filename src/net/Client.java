package net;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;

public class Client {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       Handles the connection to the server making it so only a method
       and deque is needed in the implementation of a client.
     -----------------------------------------------------------------*/


    // contains the connection from to the server
    private Connection connection;


    // constructor
    public Client(Connection.Update onMessage, LinkedBlockingDeque<String> messagesIn) {
        try {
            // create socket connected to server then create a connection
            Socket socket = new Socket("localhost", 8080);
            connection = new Connection(socket, 0, messagesIn, onMessage);

            //start connection
            new Thread(connection).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // send message to server
    public void send(String message) {
        if (!connection.isClosed()) {
            connection.send(message);
        }
    }

    // disconnect client from server
    public void disconnect() {
        connection.disconnect();
    }
}
