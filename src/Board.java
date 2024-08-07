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
  private int numOfMines;

  public Board() {
    this.xySize = 10;
    this.numOfMines = 10;
    this.board = new int[10][10];
  }

  public Board(int num) {
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
      case -1:
        return "B";
      case 9:
        return "F";
      default:
        return "" + this.board[x][y];
    }
  }

  public void printBoard() {
    for (int x = 0; x < xySize; x++) {
      for (int y = 0; y < xySize; y++) {
        // System.out.println(getSpaceValue(x, y));
        System.out.printf("[ %s ]", getSpaceValue(x, y));
      }
      System.out.println();
    }
  }

  // Have a system for reveal, have it check if space is already revealed
  // Have it check if it is a bomb -- This should be done elsewhere before
  // checking reveal.
  // Then reveal this space
  // if this space is not 0, return
  // if it is 0 meaning its a blank space, call reveal on each of the 8
  // surrounding
}
