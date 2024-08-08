import java.util.Scanner;

// Get the players input and check if its valid, otherwise loop till it is

public class PlayerActions {
  private Scanner scanner;

  public PlayerActions() {

  }

  public void getPlayerInput(int xySize) {
    System.out.println(checkValidInput(xySize, "A11"));
    checkValidInput(xySize, "A4");
  }

  private boolean checkValidInput(int xySize, String input) {
    // Currently anything higher then 9 as a second parameter will just be cut down
    // to the first digit so A10 will just check A1
    if (input.length() < 2) {
      return false;
    }
    int stringCharNum = input.substring(0, 1).toUpperCase().codePointAt(0);
    int stringNumber = Integer.parseInt(input.substring(1, 2));
    if (stringCharNum - 65 <= xySize - 1 && stringNumber <= xySize - 1) {
      return true;
    }
    return false;
  }
}
