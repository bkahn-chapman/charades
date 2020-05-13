import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * MTServer.java
 *
 * <p>This program implements a simple multithreaded chat server.  Every client that
 * connects to the server can broadcast data to all other clients.
 * The server stores an ArrayList of sockets to perform the broadcast.
 *
 * <p>The MTServer uses a ClientHandler whose code is in a separate file.
 * When a client connects, the MTServer starts a ClientHandler in a separate thread
 * to receive messages from the client.
 *
 * <p>To test, start the server first, then start multiple clients and type messages
 * in the client windows.
 *
 */

public class MtServer {
  // Maintain list of all client sockets for broadcast
  private ArrayList<Socket> socketList;

  public MtServer() {
    socketList = new ArrayList<Socket>();
  }

  private void getConnection() {
    int count = 1;
    // Wait for a connection from the client
    try {
      System.out.println("Waiting for client connections on port 7654.");
      ServerSocket serverSock = new ServerSocket(7654);
      // This is an infinite loop, the user will have to shut it down
      // using control-c
      while (true) {
        Socket connectionSock = serverSock.accept();
        // Add this socket to the list
        socketList.add(connectionSock);
        // Send to ClientHandler the socket and arraylist of all sockets
        ClientHandler handler = new ClientHandler(connectionSock, this.socketList);
        Thread theThread = new Thread(handler);
        theThread.start();
        count++;
        if(count == 8){ //limits the number of players to 8
          break;
        }
      }
      // Will never get here, but if the above loop is given
      // an exit condition then we'll go ahead and close the socket
      //serverSock.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    Random rand = new Random();
    Scanner keyboard = new Scanner(System.in);
    MtServer server = new MtServer();

    String username = "";
    String difficulty = "";
    String content = "";
    String word = "";
    int wordIndex = rand.nextInt(5); //chooses a random word from the list
    ArrayList<String> contentArrList = new ArrayList<String>(100); //list of words

    System.out.println("What is your username?");
    username = keyboard.nextLine(); //username to know who is saying what
    System.out.println("What difficulty would you like to play on? Easy/Hard");
    difficulty = keyboard.nextLine(); //gets the user's difficulty choice
    difficulty.toLowerCase();

    switch(difficulty){
      //multiple cases depending on what they type
      case "Easy":
      case "EASY":
      case "easy":
      try{
        BufferedReader er = null;
        File easy = new File("EasyCharades.txt"); //opens the list of easy words
        er = new BufferedReader(new java.io.FileReader(easy)); //gets the easy words
        while((content = er.readLine()) != null){
          contentArrList.add(content); //adds the easy words to the list
        }
        er.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
      //multiple cases depending on what they type
      case "Hard":
      case "HARD":
      case "hard":
      try{
        BufferedReader hr = null;
        File hard = new File("HardCharades.txt"); //opens the list of hard words
        hr = new BufferedReader(new java.io.FileReader(hard)); //gets the hard words
        while((content = hr.readLine()) != null){
          contentArrList.add(content); //adds the hard words to the list
        }
        hr.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }
    word = contentArrList.get(wordIndex); //gives the describer the word they are to describe
    System.out.println("You word is " + word + ".");

    server.getConnection(); //gets connections from the guesser players
  }
} // MtServer
