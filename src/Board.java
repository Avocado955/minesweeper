import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
  // Want the board to be customisable
  private int xySize;
  private int[][] board;
  private boolean[][] revealed; // Using this to check if a board square has been revealed
  private int numOfMines;

  // NOT YET IMPLEMENTED
  // -----Colours-----
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  // -----Constructors-----
  public Board() {
    this.xySize = 10;
    this.numOfMines = 10;
    this.board = new int[10][10];
    this.revealed = new boolean[10][10];
  }

  // Currently min board size = 2
  // Currently max board size = 10
  public Board(int num) {
    // Leaving this in just in case an edge case slips through
    if (num < 2) {
      num = 2;
    }
    if (num > 10) {
      num = 10;
    }
    this.xySize = num;
    this.numOfMines = num;
    this.board = new int[num][num];
    this.revealed = new boolean[num][num];
  }

  // -----Private Methods-----

  private String getSpaceValue(int x, int y) {
    switch (this.board[x][y]) {
      case -1:
        return "B";
      // case 9:
      // return "F";
      default:
        return "" + this.board[x][y];
    }
  }

  private String getBoardLetter(int x) {
    String letter = Character.toString(65 + x);
    return letter;
  }

  private List<GridPos> getAllValidGridPosAroundGridPos(int x, int y) {
    List<GridPos> allGridPositions = new ArrayList<>();
    int a = 0;
    int b = -1;
    for (int z = 0; z < 8; z++) {
      if (z == 1) {
        b += 1;
      }
      if (z == 2) {
        a = -1;
        b = -1;
      }
      if (z == 5) {
        a = 1;
        b = -1;
      }
      GridPos newPos = new GridPos(x + a, y + b);
      allGridPositions.add(newPos);
      b += 1;
    }

    List<GridPos> validGridPos = allGridPositions.stream().filter((pos) -> {
      if (pos.getX() < 0 || pos.getX() >= this.xySize) {
        return false;
      }
      if (pos.getY() < 0 || pos.getY() >= this.xySize) {
        return false;
      }
      return true;
    }).collect(Collectors.toList());

    return validGridPos;
  }

  private void updateAllSurroundingBomb(int x, int y) {
    // Refactor using GridPos list
    // need to check if its in range of the board
    // need to check that the cell is not a bomb
    List<GridPos> validGridPositionsToCheck = getAllValidGridPosAroundGridPos(x, y);

    for (GridPos gridPos : validGridPositionsToCheck) {
      if (checkSpaceForBomb(gridPos.getX(), gridPos.getY())) {
        continue;
      }
      this.board[gridPos.getX()][gridPos.getY()] += 1;
    }
  }

  private void reveal(int x, int y) {
    if (this.revealed[x][y]) {
      return;
    }
    revealed[x][y] = true;
    if (this.board[x][y] != 0) {
      return;
    }
    List<GridPos> validGridPositionsToReveal = getAllValidGridPosAroundGridPos(x, y);
    for (GridPos gridPos : validGridPositionsToReveal) {
      reveal(gridPos.getX(), gridPos.getY());
    }
  }

  // -----Public Methods-----

  public void printBoard() {
    System.out.printf(" X/Y");
    for (int a = 0; a < xySize; a++) {
      System.out.printf("  %d  ", a);
    }
    System.out.println();
    for (int x = 0; x < xySize; x++) {
      System.out.printf(" %s: ", getBoardLetter(x));
      for (int y = 0; y < xySize; y++) {
        if (!revealed[x][y]) {
          System.out.printf("[   ]");
        } else {
          System.out.printf("[ %s ]", getSpaceValue(x, y));
        }
      }
      System.out.println();
    }
  }

  public void placeBomb(int x, int y) {
    this.board[x][y] = -1;
    updateAllSurroundingBomb(x, y);
  }

  public boolean checkSpaceForBomb(int x, int y) {
    if (this.board[x][y] == -1) {
      return true;
    }
    return false;
  }

  public boolean checkInput(int x, int y) {
    boolean bombCheck = checkSpaceForBomb(x, y);
    if (bombCheck) {
      this.revealed[x][y] = true;
      return true;
    }
    reveal(x, y);
    return false;
  }

  public boolean checkWin() {
    for (int x = 0; x < xySize; x++) {
      for (int y = 0; y < xySize; y++) {
        if (checkSpaceForBomb(x, y)) {
          continue;
        }
        if (!revealed[x][y]) {
          return false;
        }
      }
    }
    return true;
  }

  // -----Getters-----
  public int getXySize() {
    return this.xySize;
  }

  public int getNumOfMines() {
    return this.numOfMines;
  }
}
