package net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Connection implements Runnable {
    private int ID;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;


    public Connection(Socket socket, int ID) {
        try {
            this.socket = socket;
            this.ID = ID;
            this.out = new PrintWriter(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isClosed()) {
            try {
                String line;
                if ((line = in.readLine()) != null)  {
                    onMessage(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isClosed() { return socket.isClosed(); }

    private void onMessage(String line) {
        System.out.println(ID + " " + line);
    }
}
