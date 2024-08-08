public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Minesweeper Terminal Edition");
        System.out.println("Please follow the instructions to play");
        System.out.println();
        GameLoop gameLoop = new GameLoop();
        gameLoop.loop();
    }
}
