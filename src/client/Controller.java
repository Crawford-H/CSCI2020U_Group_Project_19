package client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;


public class Controller {
    @FXML
    public TextField newGameCode;
    @FXML
    public GridPane mainPane;


    public void newGame(ActionEvent actionEvent) throws IOException {
        Main.primaryStage.setScene(Main.newGame);
        Main.client.send("NEWGAME");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.primaryStage.setScene(Main.mainMenu);
    }

    public void joinGame(ActionEvent actionEvent) throws IOException {
        Main.primaryStage.setScene(Main.joinGame);
    }

    public void join(ActionEvent actionEvent) {
        Main.client.send("JOIN " + newGameCode.getText());
    }

    public void exit(ActionEvent actionEvent) {
        Main.client.disconnect();
        System.exit(0);
    }
}
