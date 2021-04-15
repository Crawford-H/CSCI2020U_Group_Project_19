package server;

import javafx.fxml.FXML;
import net.Connection;
import net.Server;
import net.ServerData;

public class Controller {
    /*-----------------------------------------------------------------
                         ***DESCRIPTION***
       This class holds the server for Rock Paper Scissors, it also
       handles what happens with the game by sending messages to clients
       based on what was sent in by the clients
     -----------------------------------------------------------------*/


    // --------------------- Private Variables ------------------------//

    // container for data in the server
    private static ServerData data;
    // container for info about the current RPS game
    private Game game;



    // --------------------- Initializer -------------------------------//

    @FXML // initialized server and game data
    public void initialize() {
        // instantiate variables for server data and game data
        data = new ServerData();
        game = new Game();

        // create server then start it
        Server server = new Server(8080, data, this::onMessage);
        new Thread(server).start();
    }



    // --------------------- Public Methods ----------------------------//

    // called when a message is received from the client
    public void onMessage(Connection client) {
        try {
            // reads and handles messages until deque is empty
            while (!data.getMessagesIn().isEmpty()) {
                String message = data.getMessagesIn().takeFirst();
                handleMessages(client, message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    // --------------------- Private Methods ----------------------------//

    // handles what happens to messages received from clients then responds accordingly
    private void handleMessages(Connection client, String message) {
        //switch for message
        String[] args = message.split(" ");
        switch (args[0]) {
            case "CONNECTED":       // received when a client connects
                setPlayers(client);
                break;
            case "ROCK":            // received when the rock button is pressed by a player
                selectPlayerChoice(client, "Rock");
                checkPlayerChoices();
                break;
            case "PAPER":           // received when the paper button is pressed by a player
                selectPlayerChoice(client, "Paper");
                checkPlayerChoices();
                break;
            case "SCISSORS":        // received when the scissors button is pressed by a player
                selectPlayerChoice(client, "Scissors");
                checkPlayerChoices();
                break;
            case "Disconnected":    // received when a client disconnects
                // remove the client from the game
                if (client == game.getPlayer1()) {
                    game.setPlayer1(null);
                    game.setState(Game.State.stopped);
                }
                else if (client == game.getPlayer2()) {
                    game.setPlayer2(null);
                    game.setState(Game.State.stopped);
                }
            default:                // called if an invalid message is received
                System.out.println("[SERVER] Invalid message");
        }
    }

    // checks if both players have made a choice, then send the clients a message whether they won, lost, or tied
    private void checkPlayerChoices() {
        // check if both players made a choice
        if (game.getPlayer1Choice() != null && game.getPlayer2Choice() != null) {
            // check who is the winner
            int winner = GameLogic.checkWin(game.getPlayer1Choice(), game.getPlayer2Choice());
            // send clients outcome of the game
            switch (winner) {
                case 0: // game was tied
                    game.getPlayer1().send("TIE " + game.getPlayer2Choice());
                    game.getPlayer2().send("TIE " + game.getPlayer2Choice());
                    break;
                case 1: // player 1 won
                    game.getPlayer1().send("WON " + game.getPlayer2Choice() + " " + game.getPlayer1Choice());
                    game.getPlayer2().send("LOST " + game.getPlayer1Choice() + " " + game.getPlayer2Choice());
                    break;
                case 2: // player 2 won
                    game.getPlayer1().send("LOST " + game.getPlayer2Choice() + " " + game.getPlayer1Choice());
                    game.getPlayer2().send("WON " + game.getPlayer1Choice() + " " + game.getPlayer2Choice());
                    break;
            }
            // reset game after round is finished
            game.resetPlayerChoices();
        }
    }

    // sets which clients are in the game then starts the game if there are two clients
    private void setPlayers(Connection client) {
        // put client in player slots if a slot is empty
        if (game.getPlayer1() == null) {
            game.setPlayer1(client);
        } else if (game.getPlayer2() == null) {
            game.setPlayer2(client);
        }

        // send clients STARTGAME if both player slots are taken
        if (game.getPlayer1() != null && game.getPlayer2() != null) {
            game.setState(Game.State.started);
            game.getPlayer1().send("STARTGAME");
            game.getPlayer2().send("STARTGAME");
        }
    }

    // sets a clients choice in the game
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
