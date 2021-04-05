package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Server server;
    public static Thread serverThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("RPS Server");
        primaryStage.setScene(new Scene(root, 300, 275));
        server = new Server();
        primaryStage.show();
        serverThread = new Thread(server);
        serverThread.start();
    }

    @Override
    public void stop() throws IOException {
        server.stop();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
