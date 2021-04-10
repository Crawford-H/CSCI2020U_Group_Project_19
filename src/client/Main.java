package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import net.Client;
import net.Connection;

import java.util.concurrent.LinkedBlockingDeque;

public class Main extends Application {
    public static Stage primaryStage;
    public static Scene mainMenu;
    public static Scene newGame;
    public static Scene joinGame;

    public static Client client;
    public static LinkedBlockingDeque<String> messagesIn;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        messagesIn = new LinkedBlockingDeque<>();
        client = new Client(this::onMessage, messagesIn);
        client.send("Connected to server");

        Main.primaryStage = primaryStage;
        GridPane mainMenuPane = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        mainMenu = new Scene(mainMenuPane, 800, 600);
        GridPane newGamePane = FXMLLoader.load(getClass().getResource("NewGame.fxml"));
        newGame = new Scene(newGamePane, 800, 600);
        GridPane joinGamePane = FXMLLoader.load(getClass().getResource("JoinGame.fxml"));
        joinGame = new Scene(joinGamePane, 800, 600);
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


    public static void main(String[] args) {
        launch(args);
    }
}
