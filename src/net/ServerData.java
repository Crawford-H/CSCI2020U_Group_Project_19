package net;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerData {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       Simple class that just holds data for a server.
     -----------------------------------------------------------------*/


    // Thread safe deque to allow multiple thread to take in all messages from clients
    public static LinkedBlockingDeque<String> messagesIn;
    // ArrayList of clients
    public static ArrayList<Connection> clients;


    // Constructor
    public ServerData() {
        messagesIn = new LinkedBlockingDeque<>();
        clients = new ArrayList<>();
    }


    // Getters
    public ArrayList<Connection> getClients() { return clients; }
    public LinkedBlockingDeque<String> getMessagesIn() { return messagesIn; }
}
