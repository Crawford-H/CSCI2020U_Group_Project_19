
package net;

import server.Game;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerData {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       Simple class that just holds data for a server.
     -----------------------------------------------------------------*/


    // Thread safe deque to allow multiple thread to take in all messages from clients
    private static LinkedBlockingDeque<String> messagesIn;
    // ArrayList of clients
    private static ArrayList<Connection> clients;
    // ArrayList of active games
    private static ArrayList<Game> games;

    // Constructor
    public ServerData() {
        messagesIn = new LinkedBlockingDeque<>();
        clients = new ArrayList<>();
        games = new ArrayList<>();
    }

    // Getters
    public ArrayList<Connection> getClients() { return clients; }
    public LinkedBlockingDeque<String> getMessagesIn() { return messagesIn; }
    public ArrayList<Game> getGames() { return games; }
}
