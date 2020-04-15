import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Random;

public class FileReader{
  public static void main(String[]args){
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
        FileReader fr = new FileReader("EasyCharades.txt");
        int i;
        while((i=fr.read()) != -1)
          System.out.print((char) i);
      case "hard":
        BufferedReader br = new BufferedReader(new FileReader("HardCharades.txt"));
        while((content = br.readLine()) != null);
          contentArrList.add(content);
        br.close();
    }
  }
}
