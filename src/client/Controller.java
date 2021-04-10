package client;


import javafx.fxml.FXML;
import net.Client;
import net.Connection;
import java.util.concurrent.LinkedBlockingDeque;

public class Controller {
    Client client;
    private static LinkedBlockingDeque<String> messagesIn;

    @FXML
    public void initialize() {
        messagesIn = new LinkedBlockingDeque<>();
        client = new Client(this::onMessage, messagesIn);
        client.send("Connected to server");
    }


    public void onMessage(Connection connection) {
        try {
            while (!messagesIn.isEmpty()) {
                System.out.println(messagesIn.takeFirst());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
