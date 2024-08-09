import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameLoop {
  public boolean hasEnded = false;
  public boolean playerWon = false;
  private int xySize = 0;

  private Board gameBoard;
  private PlayerActions player;

  public GameLoop() {
    player = new PlayerActions();
  }

  public void loop() {
    setUp();
    gamePlayLoop();
  }

  // Setup doesnt need to be its own class as then would need to pass in the Board
  // and Player from Gameloop
  // Needs to be a function inside the gameLoop
  public void setUp() {
    // Might want this static? depending on how we make this
    // We want setup to handles getting the random spots for the mine
    // And get the players input for the board size
    boolean isValid = false;
    while (!isValid) {
      System.out.println("How big would you like the Board to be?");
      System.out.println("Note: X and Y Axis will be the same");
      System.out.println("Please input between 2 - 10: ");
      String input = player.getPlayerInput();
      int inputVal = Integer.parseInt(input);
      if (inputVal >= 2 && inputVal <= 10) {
        isValid = true;
        this.xySize = inputVal;
      } else {
        System.out.println("Invalid Input!");
        System.out.println();
      }
    }
    gameBoard = new Board(this.xySize);
    boolean createdMines = false;
    List<String> selectedMines = new ArrayList<>();
    while (!createdMines) {
      int randomXNum = (int) (Math.random() * this.xySize);
      int randomYNum = (int) (Math.random() * this.xySize);
      System.out.printf("X: %d, Y: %d\n", randomXNum, randomYNum);
      // Need a different method of storing the values, as 0, 3 is the same as 1, 0
      // As 0 * 3 = 0, and 1 * 0 = 0
      // make a basic class which is a storage for the x, y to be more storable
      if (!selectedMines.contains("" + randomXNum + "," + randomYNum)) {
        selectedMines.add("" + randomXNum + "," + randomYNum);
        gameBoard.placeBomb(randomXNum, randomYNum);
      }
      if (selectedMines.size() >= this.xySize) {
        createdMines = true;
      }
    }
  }

  public void gamePlayLoop() {
    while (!hasEnded) {

      gameBoard.printBoard();
      boolean validInput = false;
      String input = "";
      while (!validInput) {
        System.out.println("Choose A Space to reveal!");
        System.out.println("Enter the letter then number (Eg. A4)");
        input = player.getPlayerInput();
        validInput = player.checkValidInput(xySize, input);
        if (!validInput) {
          System.out.println();
          System.out.println("Invalid Input");
        }
      }

      // Pull apart the input and store the first 2 characters
      int stringCharNum = input.substring(0, 1).toUpperCase().codePointAt(0) - 65;
      int stringNumber = Integer.parseInt(input.substring(1, 2));
      System.out.printf("X: %d, Y: %d\n", stringCharNum, stringNumber);

      boolean checkSpace = gameBoard.checkInput(stringCharNum, stringNumber);
      // If this is true, then they blew up a bomb
      if (checkSpace) {
        hasEnded = true;
        playerWon = false;
        System.out.println();
        gameBoard.printBoard();
        System.out.println();
        System.out.println("BOOM!");
        System.out.println("You hit a bomb! Better luck next time!");
      }
    }
  }

}
