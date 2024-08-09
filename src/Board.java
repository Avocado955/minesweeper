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

  // _____________
  // [ ] | | |
  // [ ] | |
  // [ ][ ][ ][ ]
  // [ ][ ][ ][ ]
  // ￣ ￣ ￣ ￣ ￣ ￣ ￣
  // ---------- (U+FFE3)

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

  private void updateAllSurroundingBomb(int x, int y) {
    // Refactor using GridPos list
    List<GridPos> allGridPositions = new ArrayList<>();
    int a = 0;
    int b = -1;
    for (int z = 0; z < 8; z++) {
      System.out.printf("X: %d, A: %d, Y: %d, B: %d\n", x, a, y, b);
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
    System.out.println("All values");
    System.out.println(allGridPositions.toString());
    System.out.println();
    // Creates an error
    // Need to either use the iterator method
    // or make a new list and add them to it
    List<GridPos> validGridPositionsToCheck = new ArrayList<>();
    for (GridPos gridPos : allGridPositions) {
      if (gridPos.getX() < 0 || gridPos.getX() >= this.xySize || gridPos.getY() < 0 || gridPos.getY() >= this.xySize) {
        continue;
      }
      validGridPositionsToCheck.add(gridPos);
    }

    System.out.println("After remove out of range values");
    System.out.println(validGridPositionsToCheck.toString());
    System.out.println();

    for (GridPos gridPos : validGridPositionsToCheck) {
      if (checkSpaceForBomb(gridPos.getX(), gridPos.getY())) {
        continue;
      }
      this.board[gridPos.getX()][gridPos.getY()] += 1;
    }

    // need to check that the cell is not a bomb
    // need to check if its in range of the board

    // For now write an if statement for each, and think about a way to streamline
    // it
    // if (y - 1 >= 0) {
    // if (!checkSpaceForBomb(x, y - 1)) {
    // this.board[x][y - 1] += 1; // Left of Bomb
    // }
    // if (x - 1 >= 0) {
    // if (!checkSpaceForBomb(x - 1, y - 1)) {
    // this.board[x - 1][y - 1] += 1; // Diagonal Left Above of Bomb
    // }
    // }
    // if (x + 1 < this.xySize) {
    // if (!checkSpaceForBomb(x + 1, y - 1)) {
    // this.board[x + 1][y - 1] += 1; // Diagonal Left Below of Bomb
    // }
    // }
    // }

    // if (y + 1 < this.xySize) {
    // if (!checkSpaceForBomb(x, y + 1)) {
    // this.board[x][y + 1] += 1; // Right of Bomb
    // }
    // if (x - 1 >= 0) {
    // if (!checkSpaceForBomb(x - 1, y + 1)) {
    // this.board[x - 1][y + 1] += 1; // Diagonal Right Above of Bomb
    // }

    // }
    // if (x + 1 < this.xySize) {
    // if (!checkSpaceForBomb(x + 1, y + 1)) {
    // this.board[x + 1][y + 1] += 1; // Diagonal Right Below of Bomb
    // }
    // }
    // }

    // if (x - 1 >= 0) {
    // if (!checkSpaceForBomb(x - 1, y)) {
    // this.board[x - 1][y] += 1; // Above Bomb
    // }
    // }

    // if (x + 1 < this.xySize) {
    // if (!checkSpaceForBomb(x + 1, y)) {
    // this.board[x + 1][y] += 1; // Below Bomb
    // }
    // }

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

  // Have a system for reveal, have it check if space is already revealed
  // Have it check if it is a bomb -- This should be done elsewhere before
  // checking reveal.
  // Then reveal this space
  // if this space is not 0, return
  // if it is 0 meaning its a blank space, call reveal on each of the 8
  // surrounding

  public void reveal(int x, int y) {
    if (this.revealed[x][y]) {
      return;
    }
    revealed[x][y] = true;
    if (this.board[x][y] != 0) {
      return;
    }
    if (y - 1 >= 0) {
      reveal(x, y - 1); // Left of Bomb
      if (x - 1 >= 0) {
        reveal(x - 1, y - 1); // Diagonal Left Above of Bomb
      }
      if (x + 1 < this.xySize) {
        reveal(x + 1, y - 1); // Diagonal Left Below of Bomb
      }
    }

    if (y + 1 < this.xySize) {
      reveal(x, y + 1); // Right of Bomb
      if (x - 1 >= 0) {
        reveal(x - 1, y + 1); // Diagonal Right Above of Bomb
      }
      if (x + 1 < this.xySize) {
        reveal(x + 1, y + 1); // Diagonal Right Below of Bomb
      }
    }

    if (x - 1 >= 0) {
      reveal(x - 1, y); // Above Bomb
    }

    if (x + 1 < this.xySize) {
      reveal(x + 1, y); // Below Bomb
    }
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
