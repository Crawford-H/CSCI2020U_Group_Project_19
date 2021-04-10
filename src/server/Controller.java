package server;

import javafx.fxml.FXML;
import net.Connection;
import net.Server;
import net.ServerData;

public class Controller {
    private static ServerData data;

    @FXML
    public void initialize() {
        data = new ServerData();
        Server server = new Server(8080, data, this::onMessage);
        new Thread(server).start();
    }

    private void onMessage(Connection client) {
        try {
            while (!ServerData.messagesIn.isEmpty()) {
                System.out.println(client.getID() + " " + data.getMessagesIn().takeFirst());
                client.send("Hello from server");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void sendToAll() {
        for (Connection client : data.getClients()) {
            if (client.isClosed()) {
                data.getClients().remove(client);
            } else {
                client.send("Hello from server");
            }
        }
    }
}
