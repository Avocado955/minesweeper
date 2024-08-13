import java.util.Scanner;

// Get the players input and check if its valid, otherwise loop till it is

public class PlayerActions {
  private Scanner scanner;

  public PlayerActions() {

  }

  public String getPlayerInput() {
    this.scanner = new Scanner(System.in);
    String userInput = this.scanner.next();
    return userInput;
  }

  public boolean checkValidInput(int xySize, String input) {
    // Currently anything higher then 9 as a second parameter will just be cut down
    // to the first digit so A10 will just check A1
    if (input.length() < 2) {
      return false;
    }
    int stringCharNum = input.substring(0, 1).toUpperCase().codePointAt(0);
    int stringNumber = Integer.parseInt(input.substring(1, 2));
    if (stringCharNum - 65 <= xySize - 1 && stringCharNum - 65 >= 0 && stringNumber <= xySize - 1) {
      return true;
    }
    return false;
  }

  public void closeScanner() {
    this.scanner.close();
  }

  // move the loop for getting the player input in here with the checking

  // make an input interface which has the functions inside
  // have the input interface coming in through app to the game loop, decoupling
  // it

  // make a tile class that has the boolean for revealed or not, number and isbomb
}
