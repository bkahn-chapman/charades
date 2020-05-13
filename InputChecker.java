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

  //puts the guess and the word in the same place
  public InputChecker(String u, String w){
    userInput = u;
    word = w;
  }

  //gets the user's input
  public void getInput(String input){
    userInput = input.toLowerCase();
  }

  //gets the word to be guessed
  public void getWord(String charades){
    word = charades.toLowerCase();
  }

  //checks if the word the user entered is the same as the word
  public boolean isEqual(){
    if(userInput.equals(word)){
      return true;
    } else{
      return false;
    }
  }
}
