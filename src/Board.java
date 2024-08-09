import java.util.ArrayList;
import java.util.List;

public class Board {
  // Use a int[][] for the board data
  // -1 = bomb, 0 = empty
  // 1, 2, 3, 4, 5, 6, 7, 8 = number of bombs in adjoining tiles
  // flag should be 9
  // Need a function to check surrounding board items

  // 4x x 4y
  // y
  // x 0 1 2 3
  // 4 5 6 7
  // 8 9 10 11
  // 12 13 14 15

  // x * num + y
  // 2 * 4 + 1 = 9
  // 0x,0y 0x,1y 0x,2y 0x,3y
  // 1x,0y 1x,1y 1x,2y 1x,3y
  // 2x,0y 2x,1y 2x,2y 2x,3y
  // 3x,0y 3x,1y 3x,2y 3x,3y

  // Want the board to be customisable
  private int xySize;
  private int[][] board;
  private boolean[][] revealed; // Using this to check if a board square has been revealed
  private int numOfMines;

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
    // Should move these 2 checks out to the setup
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

  private List<GridPos> getAllSafeGridPosAroundGridPos(int x, int y) {
    List<GridPos> allGridPositions = new ArrayList<>();
    int a = 0;
    int b = -1;
    for (int z = 0; z < 8; z++) {
      // System.out.printf("X: %d, A: %d, Y: %d, B: %d\n", x, a, y, b);
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
    // System.out.println("All values");
    // System.out.println(allGridPositions.toString());
    // System.out.println();

    List<GridPos> validGridPositionsToCheck = new ArrayList<>();
    for (GridPos gridPos : allGridPositions) {
      if (gridPos.getX() < 0 || gridPos.getX() >= this.xySize || gridPos.getY() < 0 || gridPos.getY() >= this.xySize) {
        continue;
      }
      validGridPositionsToCheck.add(gridPos);
    }

    // System.out.println("After remove out of range values");
    // System.out.println(validGridPositionsToCheck.toString());
    // System.out.println();
    return validGridPositionsToCheck;
  }

  private void updateAllSurroundingBomb(int x, int y) {
    // Refactor using GridPos list
    // need to check if its in range of the board
    // need to check that the cell is not a bomb
    List<GridPos> validGridPositionsToCheck = getAllSafeGridPosAroundGridPos(x, y);

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
    List<GridPos> validGridPositionsToReveal = getAllSafeGridPosAroundGridPos(x, y);
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

  // Getters
  public int getXySize() {
    return this.xySize;
  }

  public int getNumOfMines() {
    return this.numOfMines;
  }
}
