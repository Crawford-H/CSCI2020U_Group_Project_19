package net;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;

public class Client {
    private Connection connection;
    private Socket socket;
    private static Client client;

    public Client(Connection.Update onMessage, LinkedBlockingDeque<String> messagesIn) {
        try {
            socket = new Socket("localhost", 8080);
            connection = new Connection(socket, 0, messagesIn, onMessage);
            new Thread(connection).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        if (!socket.isClosed()) {
            connection.send(message);
        }
    }

    public void disconnect() {
        connection.disconnect();
    }

}
