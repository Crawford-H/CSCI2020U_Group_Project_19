package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;


public class Connection implements Runnable {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       Handles the input and output for a server or client by looking
       for connections while the socket is open, then invoking the
       given method if an message has been inputted and adding the
       message to the given deque.
     -----------------------------------------------------------------*/


    // connection to other endpoint
    private Socket socket;
    // PrintWriter to send messages to other endpoint
    private PrintWriter out;
    // BufferedReader to get messages in from other endpoint
    private BufferedReader in;
    // ID of connection
    private int ID;
    // Thread safe deque to add the incoming messages to
    private static LinkedBlockingDeque<String> messagesIn;
    // Method to handle incoming messages
    private Update onMessage;

    // interface so the connection can call a method every time a message is received
    public interface Update {
        void Message(Connection connection);
    }

    // constructor
    public Connection(Socket socket, int ID, LinkedBlockingDeque<String> messagesIn, Update update) {
        try {
            Connection.messagesIn = messagesIn;
            this.socket = socket;
            this.ID = ID;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.onMessage = update;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // check for messages while the connection is valid
    @Override
    public void run() {
        try {
            // loop while socket is open
            while (!isClosed()) {
                // handle incoming messages
                addMessages();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // disconnect client from server
    public void disconnect() {
        try {
            socket.close();
            in.close();
            out.close();
            System.out.println("[SERVER] " + ID + " has disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // send message to client
    public void send(String message) {
        out.println(message);
    }


    // getters
    public int getID() { return ID; }
    public boolean isClosed() { return socket.isClosed(); }

    // reads in messages from client then invokes method given from implementation
    private void addMessages()  {
        try {
            // read in lines while they are not null
            String line;
            while ((line = in.readLine()) != null) {
                // add message to deque
                messagesIn.putLast(line);

                // invoke method to handle message
                onMessage.Message(this);
            }
        } catch (IOException | InterruptedException e) {
            disconnect();
        }
    }
}
