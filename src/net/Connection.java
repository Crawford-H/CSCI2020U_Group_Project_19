package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;


public class Connection implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int ID;
    private static LinkedBlockingDeque<String> messagesIn;
    private Update onMessage;

    public interface Update {
        void Message(Connection connection);
    }


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


    @Override
    public void run() {
        try {
            while (!isClosed()) {
                addMessages();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void send(String message) {
        out.println(message);
    }


    //getters
    public int getID() { return ID; }
    public boolean isClosed() { return socket.isClosed(); }


    private void addMessages()  {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                messagesIn.putLast(line);
                onMessage.Message(this);
            }
        } catch (IOException | InterruptedException e) {
            disconnect();
        }
    }
}
