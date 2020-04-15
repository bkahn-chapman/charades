import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Random;

public class FileReader{
  public static void main(String[]args)throws Exception{
    Random rand = new Random();
    Scanner read = new Scanner(System.in);

    String difficulty = "";
    String content = "";
    int wordIndex = rand.nextInt(101);
    ArrayList<String> contentArrList = new ArrayList<String>(100);

    System.out.println("What difficulty would you like to play on? Easy (1) or Hard (2)?");
    difficulty = read.nextLine();
    difficulty.toLowerCase();

    switch(difficulty){
      case "easy":
        File file = new File("EasyCharades.txt");
        BufferedReader er = new BufferedReader(new java.io.FileReader(file));
        while((content = er.readLine()) != null);
          contentArrList.add(content);
          System.out.println(content);
      er.close();
      case "hard":
        BufferedReader hr = new BufferedReader(new java.io.FileReader("HardCharades.txt"));
        while((content = hr.readLine()) != null);
          contentArrList.add(content);
      hr.close();
    }
  }
}
