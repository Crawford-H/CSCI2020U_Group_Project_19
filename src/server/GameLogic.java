package server;

import java.util.Random;

public class GameLogic {
    // THE OPTIONS HERE ARE JUST USED FOR TESTING, IN IMPLEMENTATION, LINK THIS WITH BUTTON IN GAME, TO PASS EITHER "ROCK", "PAPER" or "SCISSORS"
    String player1Choice = "Rock";
    String player2Choice = "Rock";
    //int result; // 0 = tie, 1, = P1 win, 2 = P2 win.

    public static String cpuChoice(){
        String compChoice = "";
        Random gen = new Random();
        int compPlayer = gen.nextInt(3)+1;
        if(compPlayer == 1){
            compChoice = "Rock";
        }
        else if(compPlayer == 2){
            compChoice = "Paper";
        }
        else {
            compChoice = "Scissors";
        }
        return compChoice;
    }

    public static int checkWin(String p1Choice, String p2Choice){
        // P1 = Rock
        if(p1Choice.equals("Rock")){
            if(p2Choice.equals("Scissors")){
                return 1;
            }
            if(p2Choice.equals("Paper")){
                return 2;
            }
        }
        // P1 = Paper
        else if(p1Choice.equals("Paper")){
            if(p2Choice.equals("Scissors")){
                return 2;
            }
            if(p2Choice.equals("Rock")){
                return 1;
            }
        }
        // P1 = Scissors
        else if(p1Choice.equals("Scissors")){
            if(p2Choice.equals("Rock")){
                return 2;
            }
            if(p2Choice.equals("Paper")){
                return 1;
            }
        }
        // P1 would equal P2
        return 0;
    }

}