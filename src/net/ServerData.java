package net;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerData {
    public static LinkedBlockingDeque<String> messagesIn;
    public static ArrayList<Connection> clients;

    public ServerData() {
        messagesIn = new LinkedBlockingDeque<>();
        clients = new ArrayList<>();
    }

    public ArrayList<Connection> getClients() { return clients; }
    public LinkedBlockingDeque<String> getMessagesIn() { return messagesIn; }
}
