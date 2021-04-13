package server;

import javafx.fxml.FXML;
import net.Connection;
import net.Server;
import net.ServerData;

public class Controller {
    private static ServerData data;
    private Game game;

    @FXML
    public void initialize() {
        data = new ServerData();
        game = new Game();
        Server server = new Server(8080, data, this::onMessage);
        new Thread(server).start();
    }

    public void sendToAll(String message) {
        for (Connection client : data.getClients()) {
            client.send(message);
        }
    }

    public void onMessage(Connection client) {
        try {
            while (!data.getMessagesIn().isEmpty()) {
                String message = data.getMessagesIn().takeFirst();
                handleMessages(client, message);
                System.out.println("[" + client.getID() + "] " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void handleMessages(Connection client, String message) {
        String[] args = message.split(" ");
        switch (args[0]) {
            case "CONNECTED":
                setPlayers(client);
                break;
            case "ROCK":
                selectPlayerChoice(client, "Rock");
                checkPlayerChoices();
                break;
            case "PAPER":
                selectPlayerChoice(client, "Paper");
                checkPlayerChoices();
                break;
            case "SCISSORS":
                selectPlayerChoice(client, "Scissors");
                checkPlayerChoices();
                break;
            default:
                System.out.println("[SERVER] Invalid message");
        }
    }


    private void checkPlayerChoices() {
        if (game.getPlayer1Choice() != null && game.getPlayer2Choice() != null) {
            int winner = GameLogic.checkWin(game.getPlayer1Choice(), game.getPlayer2Choice());
            switch (winner) {
                case 0:
                    game.getPlayer1().send("TIE " + game.getPlayer2Choice());
                    game.getPlayer2().send("TIE " + game.getPlayer2Choice());
                    break;
                case 1:
                    game.getPlayer1().send("WON " + game.getPlayer2Choice());
                    game.getPlayer2().send("LOST " + game.getPlayer1Choice());
                    break;
                case 2:
                    game.getPlayer1().send("LOST " + game.getPlayer2Choice());
                    game.getPlayer2().send("WON " + game.getPlayer1Choice());
                    break;
            }
            game.resetPlayerChoices();
        }
    }

    private void setPlayers(Connection client) {
        if (game.getPlayer1() == null) {
            game.setPlayer1(client);
        } else if (game.getPlayer2() == null) {
            game.setPlayer2(client);
        }

        if (game.getPlayer1() != null && game.getPlayer2() != null) {
            game.setState(Game.State.started);
            game.getPlayer1().send("STARTGAME");
            game.getPlayer2().send("STARTGAME");
        }
    }

    private void selectPlayerChoice(Connection client, String choice) {
        if (game.getState() == Game.State.started) {
            if (client.getID() == game.getPlayer1().getID()) {
                game.setPlayer1Choice(choice);
            } else {
                game.setPlayer2Choice(choice);
            }
        }
    }
}
