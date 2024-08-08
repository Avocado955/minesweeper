public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Board board = new Board(10);
        board.printBoard();
        board.placeBomb(3, 3);
        board.placeBomb(4, 2);
        board.placeBomb(1, 0);
        board.placeBomb(2, 2);
        board.printBoard();
        PlayerActions player = new PlayerActions();
        player.getPlayerInput(10);
    }
}
