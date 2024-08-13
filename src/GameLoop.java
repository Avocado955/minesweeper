import java.util.ArrayList;
import java.util.List;

public class GameLoop {
  // For debugging mode, enter -1 in Board size input section
  private boolean debugging = false;
  private boolean keepPlaying = true;
  public boolean hasEnded = false;
  public boolean playerWon = false;
  private int xySize = 0;

  private Board gameBoard;
  private PlayerActions player;

  public GameLoop() {
    player = new PlayerActions();
  }

  public void loop() {
    while (keepPlaying) {
      setUp();
      gamePlayLoop();
    }
  }

  public void setUp() {
    hasEnded = false;
    playerWon = false;
    // We want setup to handles getting the random spots for the mine
    // And get the players input for the board size
    boolean isValid = false;
    while (!isValid) {
      System.out.println("How big would you like the Board to be?");
      System.out.println("Note: X and Y Axis will be the same");
      System.out.println("Please input between 2 - 10: ");
      String input = player.getPlayerInput();
      int inputVal = Integer.parseInt(input);
      if (inputVal == -1) {
        this.debugging = !this.debugging;
        System.out.printf("Debugging mode: %b\n", this.debugging);
      }
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

      if (!selectedMines.contains("" + randomXNum + "," + randomYNum)) {
        selectedMines.add("" + randomXNum + "," + randomYNum);
        gameBoard.placeBomb(randomXNum, randomYNum);
        if (debugging) {
          System.out.printf("Bomb placed X: %d, Y: %d\n", randomXNum, randomYNum);
        }
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
      while (!validInput) { // check the players input is valid, and loop till it is
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
      System.out.println();
      boolean checkSpace = gameBoard.checkInput(stringCharNum, stringNumber);
      // If this is true, then they blew up a bomb
      if (checkSpace) {
        hasEnded = true;
        playerWon = false;
      } else { // If not then check if they have revealed all other squares
        boolean checkWin = gameBoard.checkWin();
        if (checkWin) { // If so, they have won and end the game
          playerWon = true;
          hasEnded = true;
        }
      }

    }

    if (playerWon) {
      System.out.println();
      gameBoard.printBoard();
      System.out.println();
      System.out.println("CONGRATULATION!");
      System.out.println("You managed to avoid exploding youself!");
    } else {
      System.out.println();
      gameBoard.printBoard();
      System.out.println();
      System.out.println("BOOM!");
      System.out.println("You hit a bomb! Better luck next time!");
    }

    System.out.println();
    System.out.println("Would you like to play again?");
    System.out.println("Y - yes, N - no");
    System.out.println("Any Invalid will be taken as No");
    String input = player.getPlayerInput();
    if (input.toUpperCase().charAt(0) != 'Y') {
      keepPlaying = false;
    }
    System.out.println();
  }

  public void closePlayerScanner() {
    player.closeScanner();
  }

}
