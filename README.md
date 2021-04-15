# csci2020u_Assignment_Group_19
Group Assignment of Crawford Heidinger, Alessandro Prataviera, Jackson Chen and Mathew Kasbarian


Overview
---------------------------------------------------
Our project is a 2 player multithreaded game of Rock Paper Scissors where the 2 clients face off against each other as many times as they would like. It is a simple game using the one of Java's features known as JavaFX to create UIs.



How to Run
-----------------------------------------------------------------------------------------------
In order to run our program you will need to download a java that is greater than java 8 and less than java 13.
You must also get a java that can compile javafx code as well since the regular java versions won't do this.
There is a website that you can download a combined version of java and javafx called azul, and I would
recommend you go there to download it. The recommended IDE that we use is Intellij as it is meant for the
java coding language, and it is easy to set up, but Intellij is not needed. Once all the setup is complete, and you
have everything downloaded, you can clone our repository in your terminal which is done with a simple git
clone, and the repository website link. This will import all the code onto your machine for use. Then you
can open our code in the IDE and run our program however there is a certain order that it must be done in. Firstly, go 
to the server package and into the main class; from there you can run it to start up a server. For clients, you can then 
run the main class from the client package so that you which will connect to the server if it is running. 
From there you will have access to the game!

Steps to play Rock Paper Scissors OPTION 1:
1. Compile the code in an IDE and run in the specific order
2. Then wait for another player to join
3. Once the player has joined and there are 2 players in the game you can select 1 out of the 3 buttons
4. Once both players have selected their options the server will notify you who has won

Steps to play Rock Paper Scissors OPTION 2:
1. navigate to out/production/CSCI2020U_Group_Project_19 in the terminal
2. run server with command "java server.Main"
3. run as many clients with separate terminals with command "java client.Main"


Resources
-----------------------------------------------------------------------------------------------
https://www.azul.com/downloads/zulu-community/?package=jdk

https://www.jetbrains.com/idea/
