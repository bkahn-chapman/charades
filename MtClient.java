import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;

/**
 * MTClient.java
 *
 * <p>This program implements a simple multithreaded chat client.  It connects to the
 * server (assumed to be localhost on port 7654) and starts two threads:
 * one for listening for data sent from the server, and another that waits
 * for the user to type something in that will be sent to the server.
 * Anything sent to the server is broadcast to all clients.
 *
 * <p>The MTClient uses a ClientListener whose code is in a separate file.
 * The ClientListener runs in a separate thread, recieves messages form the server,
 * and displays them on the screen.
 *
 * <p>Data received is sent to the output screen, so it is possible that as
 * a user is typing in information a message from the server will be
 * inserted.
 */
public class MtClient {
  /**
   * main method.
   * @params not used.
   */
  public static void main(String[] args) {
    try {
      String hostname = "192.168.50.55";
      int port = 7654;

      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made.");

      System.out.println("What is your username?");
      Scanner keyboard = new Scanner(System.in);
      String username = keyboard.nextLine();

      Random rand = new Random();
      Scanner read = new Scanner(System.in);

      String difficulty = "";
      String content = "";
      String word = "";
      int wordIndex = rand.nextInt(101);
      ArrayList<String> contentArrList = new ArrayList<String>(100);

      System.out.println("What difficulty would you like to play on? Easy or Hard?");
      difficulty = read.nextLine();
      difficulty.toLowerCase();

      switch(difficulty){
        case "easy":
          word = "Phone";
          System.out.println("Your word is: Phone");
        case "hard":
          word = "President";
          System.out.println("Your word is: President");
      }

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.
      while (true) {
        String data = keyboard.nextLine();
        if(data.contains(word)){
          System.out.println("You cannot use the word in your descriptions!");
        } else{
          serverOutput.writeBytes(username + ": " + data + "\n");
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtClient
