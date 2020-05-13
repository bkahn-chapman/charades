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
    MtServer s = new MtServer();
    String word = MtServer.getWord().word;
    try {
      String hostname = "192.168.50.55"; //THE IP ADDRESS OF THE DESCRIBER (HOST) COMPUTER **CHANGE THIS**
      int port = 7654;

      System.out.println("What is the IP Address of the describer's computer?");
      //gets the IP address of the host computer so it is more easily portable
      Scanner keyboard = new Scanner(System.in);
      hostname = keyboard.nextLine();

      //connects to the server on the given IP address
      System.out.println("Connecting to server on port " + port);
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());

      System.out.println("Connection made.");

      //gets the guesser's username
      System.out.println("What is your username?");
      String username = keyboard.nextLine();

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      // Read input from the keyboard and send it to everyone else.
      // The only way to quit is to hit control-c, but a quit command
      // could easily be added.
      while (true) {
        String data = keyboard.nextLine();
        if(data == word){
          System.out.println("You got the word!");
        } else{
          serverOutput.writeBytes(username + ": " + data + "\n");
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
} // MtClient
