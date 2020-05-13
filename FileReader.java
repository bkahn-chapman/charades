import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Random;

public class FileReader{
  public static void main(String[]args)throws Exception{
    Random rand = new Random(); //RNG
    Scanner read = new Scanner(System.in); //file reader

    String difficulty = "";
    String content = "";
    int wordIndex = rand.nextInt(101); //chooses a random word from the list
    ArrayList<String> contentArrList = new ArrayList<String>(100); //akes the list of words that can be given

    System.out.println("What difficulty would you like to play on? Easy (1) or Hard (2)?");
    difficulty = read.nextLine(); //takes in the user's difficulty choice
    difficulty.toLowerCase();

    switch(difficulty){
      case "easy": //handles the easy choice
        File file = new File("EasyCharades.txt"); //uses the easy word set
        BufferedReader er = new BufferedReader(new java.io.FileReader(file));
        //creates the easy word list
        while((content = er.readLine()) != null);
          contentArrList.add(content);
          System.out.println(content);
      er.close();
      case "hard": //handles the hard choice
        BufferedReader hr = new BufferedReader(new java.io.FileReader("HardCharades.txt")); //uses the hard word set
        //creates the hard word list
        while((content = hr.readLine()) != null);
          contentArrList.add(content);
      hr.close();
    }
  }
}
