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
  }

  // Currently max board size = 10
  public Board(int num) {
    if (num > 10) {
      num = 10;
    }
    this.xySize = num;
    this.numOfMines = num;
    this.board = new int[num][num];
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
      case 0:
        return " ";
      case -1:
        return "B";
      case 9:
        return "F";
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
        // System.out.println(getSpaceValue(x, y));
        System.out.printf("[ %s ]", getSpaceValue(x, y));
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
    this.board[x][y - 1] += 1; // Left of Bomb
    this.board[x][y + 1] += 1; // Right of Bomb

    this.board[x - 1][y - 1] += 1; // Diagonal Left Above of Bomb
    this.board[x - 1][y] += 1; // Above Bomb
    this.board[x - 1][y + 1] += 1; // Diagonal Right Above of Bomb

    this.board[x + 1][y - 1] += 1; // Diagonal Left Below of Bomb
    this.board[x + 1][y] += 1; // Below Bomb
    this.board[x + 1][y + 1] += 1; // Diagonal Right Below of Bomb
  }

  // Have a system for reveal, have it check if space is already revealed
  // Have it check if it is a bomb -- This should be done elsewhere before
  // checking reveal.
  // Then reveal this space
  // if this space is not 0, return
  // if it is 0 meaning its a blank space, call reveal on each of the 8
  // surrounding
}
