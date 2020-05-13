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
  public String word = "";
  private ArrayList<Socket> socketList;

  private static MtServer instance = null;

  public static MtServer getWord(){
    if(instance == null){
      instance = new MtServer();
    }
    return instance;
  }

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
    difficulty.toLowerCase(); //sets the input to lowercase for easier switch statement

    switch(difficulty){
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


    try{
      String hostname = "192.168.50.55"; //THE IP ADDRESS OF THE DESCRIBER (HOST) COMPUTER **CHANGE THIS**
      int port = 7654;

      System.out.println("What is the IP Address of the describer's computer?");
      //gets the IP address of the host computer so it is more easily portable
      hostname = keyboard.nextLine();

      //connects to the server on the given IP address
      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);
      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());
      System.out.println("Connection made.");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.
      while (true){
        String data = keyboard.nextLine();
        if(data.contains(word)){
          System.out.println("Your descriptions cannot contain the word!");
        } else{
          serverOutput.writeBytes(username + ": " + data + "\n");
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtServer
