public class App {
    // Look at adding colours using ANSI color code
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Minesweeper Terminal Edition");
        System.out.println("Please follow the instructions to play");
        System.out.println();
        GameLoop gameLoop = new GameLoop();
        gameLoop.loop();
    }
}
