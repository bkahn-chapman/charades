import java.io.*;
import java.util.*;
import java.util.Scanner;

public class InputChecker{
  String userInput;
  String word;
  Scanner read = new Scanner(System.in);

  public InputChecker(){
    userInput = "";
    word = "";
  }

  public InputChecker(String u, String w){
    userInput = u;
    word = w;
  }

  public void getInput(String input){
    userInput = input.toLowerCase();
  }

  public void getWord(String charades){
    word = charades.toLowerCase();
  }

  public boolean isEqual(){
    if(userInput.equals(word)){
      return true;
    } else{
      return false;
    }
  }
}
