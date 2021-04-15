package client;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import net.Client;
import net.Connection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.LinkedBlockingDeque;


public class Controller {
    /*---------------------------------------------------------------//
                         ***DESCRIPTION***
       Controller for Rock Paper Scissors client.
       This class controls:
        * the display and UI for the game
        * handles messages from the server
        * sends messages to the server
        * handles what happens when a button is pressed
     //---------------------------------------------------------------*/


    // ---------------------Public Variables -------------------------//

    @FXML // The display for the game
    public Canvas canvas;
    @FXML // GraphicsContext of the canvas to draw the game to
    public GraphicsContext graphicsContext;



    // --------------------- Private Variables ------------------------//

    // Connection to the server to get info about the game
    private Client client;
    // Thread safe deque for the messages that have come in
    private static LinkedBlockingDeque<String> messagesIn;
    // Image of the sprites used in the display
    private Image sprites;
    // Keeps track of whether it is the players turn or not
    private boolean playerTurn;



    // --------------------- Initializer -------------------------------//

    @FXML // Initializes all the variables and what is on the display
    public void initialize() {
        // networking stuff
        messagesIn = new LinkedBlockingDeque<>();
        client = new Client(this::onMessage, messagesIn);
        // send to the server that the client has connected to add client to the game
        client.send("CONNECTED");

        // display and button stuff
        playerTurn = false;
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.fillText("Waiting for other player", 10, 10);
        // get images for display
        try {
            FileInputStream image = new FileInputStream("resources/rps.png");
            sprites = new Image(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    // --------------------- Public Methods ----------------------------//

    // called when the Rock button is pressed
    public void rockButton() {
        // button only works when it is the players turn
        if (playerTurn) {
            // clear the screen
            clear();
            // send to server the players choice
            client.send("ROCK");
            // draw to screen then end players turn
            drawImages("Rock", false);
            graphicsContext.fillText("Waiting...", 500, 240);
            playerTurn = false;
        }
    }

    // called when the Paper button is pressed
    public void paperButton() {
        if (playerTurn) {
            clear();
            client.send("PAPER");
            drawImages("Paper", false);
            graphicsContext.fillText("Waiting...", 500, 240);
            playerTurn = false;
        }
    }

    // called when the Scissors button is pressed
    public void scissorsButton() {
        if (playerTurn) {
            clear();
            client.send("SCISSORS");
            drawImages("Scissors", false);
            graphicsContext.fillText("Waiting...", 500, 240);
            playerTurn = false;
        }
    }

    // method passed into the client, called whenever a message is received
    public void onMessage(Connection connection) {
        try {
            // read in all messages until the deque is empty
            while (!messagesIn.isEmpty()) {
                //take the first message in the deque than handle what happens
                String message = messagesIn.takeFirst();
                handleMessages(client, message);
                System.out.println("[SERVER] " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    // --------------------- Private Methods ----------------------------//

    // handle what happens with the messages sent in from the server
    private void handleMessages(Client client, String message) {
        // switch for all possible commands from the server
        String[] args = message.split(" ");
        switch (args[0]) {
            case "STARTGAME":    // received when the game is ready
                onStartGame();
                break;
            case "TIE":         // received when a tie happened
                onTie(args[1]);
                break;
            case "WON":         // received when the player won
                onWin(args);
                break;
            case "LOST":        // received when the player lost
                onLoss(args);
                break;
            default:            // and unknown message
                System.out.println("Invalid message");
                break;
        }
    }

    // called when message received that the player lost
    private void onLoss(String[] args) {
        // clear canvas then draw outcome
        clear();
        drawImages(args[1], true);
        drawImages(args[2], false);

        // display text that player lost then allow a turn
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillText("You Lost :(((\nSelect another option to play again.", 250, 200);
        playerTurn = true;
    }

    // called when message received that the player won
    private void onWin(String[] args) {
        // clear canvas then draw outcome
        clear();
        drawImages(args[1], true);
        drawImages(args[2], false);

        // display text that player won then allow a turn
        graphicsContext.setFill(Color.LIGHTGREEN);
        graphicsContext.fillText("You Won!!!\nSelect another option to play again.", 250, 200);
        playerTurn = true;
    }

    // called when message received that the player tied
    private void onTie(String arg) {
        // clear canvas then draw outcome
        clear();
        drawImages(arg, true);
        drawImages(arg, false);

        // display text that player tied then allow a turn
        graphicsContext.setFill(Color.GREY);
        graphicsContext.fillText("It was a tie.\nSelect another option to play again.", 250, 200);
        playerTurn = true;
    }

    // called when message received that game can start
    private void onStartGame() {
        try {
            Thread.sleep(100);  // for some reason there was a null pointer exception unless there is a delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //clear the screen then set players turn to true
        clear();
        graphicsContext.fillText("Other player joined. Choose an option to play", 10, 10);
        playerTurn = true;
    }

    // draws the choice of a player
    private void drawImages(String choice, boolean opponent) {
        // check which image to draw
        int sxBuffer = (choice.equals("Rock")) ? 0 : ((choice.equals("Paper")) ? 1 : 2);

        // draw in different locations depending on whether it is the player or opponent
        if (!opponent) {
            graphicsContext.drawImage(sprites, 217.66666667 * sxBuffer, 0, 217.66666667, 225, 50, 125, 200, 200);
        } else {
            graphicsContext.drawImage(sprites, 217.66666667 * sxBuffer, 0, 217.66666667, 225, 640, 125, -200, 200);
        }
    }

    // clears the screen
    private void clear() {
        graphicsContext.setFill(Color.web("#455A64"));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Color.BLACK);
    }
}
