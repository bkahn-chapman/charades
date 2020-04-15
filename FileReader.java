import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
public class FileReader{
  public static void main(String[]args){
    String difficulty = "";
    String content = "";
    ArrayList<String> contentArrList = new ArrayList<String>(100);
    Scanner read = new Scanner(System.in);
    BufferedReader br = null;

    System.out.println("What difficulty would you like to play on? Easy (1) or Hard (2)?");
    difficulty = read.nextLine();
    difficulty.toLowerCase();

    switch(difficulty){
      case "easy":
        br = new BufferedReader(new FileReader("EasyCharades.txt"));
        while((content = br.readLine()) != null){
          contentArrList.add(content);
        br.close();
        }
      case "hard":
        br = new BufferedReader(new FileReader("HardCharades.txt"));
        while((content = br.readLine()) != null);
          contentArrList.add(content);
        br.close();
    }
  }
}
