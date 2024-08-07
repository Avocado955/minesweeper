public class GridPos {
  private int x;
  private int y;

  public GridPos(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Getters
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  // Setters
  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "GridPos [x=" + this.x + ", y=" + this.y + "]";
  }
}
