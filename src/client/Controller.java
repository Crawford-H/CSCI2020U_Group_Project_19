package client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.Client;
import net.Connection;
import java.util.concurrent.LinkedBlockingDeque;


public class Controller {
    private Client client;
    private static LinkedBlockingDeque<String> messagesIn;

    @FXML
    public void initialize() {
        messagesIn = new LinkedBlockingDeque<>();
        client = new Client(this::onMessage, messagesIn);
        client.send("CONNECTED");
    }

    public void onMessage(Connection connection) {
        try {
            while (!messagesIn.isEmpty()) {
                String message = messagesIn.takeFirst();
                handleMessages(client, message);
                System.out.println("[SERVER] " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleMessages(Client client, String message) {
        String[] args = message.split(" ");
        switch (args[0]) {

        }
    }

    public void rockButton(ActionEvent actionEvent) {
        client.send("ROCK");
    }

    public void paperButton(ActionEvent actionEvent) {
        client.send("PAPER");
    }

    public void scissorsButton(ActionEvent actionEvent) {
        client.send("SCISSORS");
    }
}
