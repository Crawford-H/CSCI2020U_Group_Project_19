package server;


import net.Connection;

public class Game {
    private Connection player1;
    private Connection player2;
    private String player1Choice;
    private String player2Choice;
    private State state;
    public enum State {
        stopped,
        started
    }

    public Game() {
        state = State.stopped;
    }

    // getters
    public Connection getPlayer1() { return player1; }
    public Connection getPlayer2() { return player2; }
    public String getPlayer1Choice() { return player1Choice; }
    public String getPlayer2Choice() { return player2Choice; }
    public State getState() { return state; }


    // setters
    public void setPlayer1(Connection player1) { this.player1 = player1; }
    public void setPlayer2(Connection player2) { this.player2 = player2; }
    public void setPlayer1Choice(String player1Choice) { this.player1Choice = player1Choice; }
    public void setPlayer2Choice(String player2Choice) { this.player2Choice = player2Choice; }
    public void resetPlayerChoices() { player1Choice = null; player2Choice = null; }
    public void setState(State state) { this.state = state; }
}
